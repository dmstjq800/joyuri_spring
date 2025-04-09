package project.demo.member.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import project.demo.admin.dto.RoleDTO;
import project.demo.member.dto.MemberDTO;
import project.demo.member.dto.MemberResponseDTO;
import project.demo.member.dto.UpdateNicknameDTO;
import project.demo.member.dto.UpdatePasswordDTO;
import project.demo.member.entity.Member;
import project.demo.member.repository.MemberRepository;
import project.demo.security.exeption.customexception.BadRequestException;
import project.demo.security.exeption.customexception.ConflictException;
import project.demo.security.exeption.customexception.ForbiddenException;
import project.demo.security.exeption.customexception.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Generated
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
//    private final BCryptPasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${frontend-url}")
    private String front_url;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
    }
    public Member findById(long id){
        return memberRepository.findById(id).orElse(null);
    }
    public Member findByusername(String username){
        return memberRepository.findByUsername(username).orElse(null);
    }
    /// 유저 생성
    @Transactional
    public Member createMember(MemberDTO memberDTO) throws MessagingException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(memberRepository.findByUsername(memberDTO.getUsername()).isPresent())
            throw new ConflictException("존재하는 e-mail");
        if(memberRepository.findByNickname(memberDTO.getNickname()).isPresent())
            throw new ConflictException("존재하는 nickname");
        String password = passwordEncoder.encode(memberDTO.getPassword());
        Member member = Member.builder()
                .username(memberDTO.getUsername())
                .password(password)
                .nickname(memberDTO.getNickname())
                .roles(List.of("ROLE_USER"))
                .emailToken(UUID.randomUUID().toString())
                .emailTokenExpiry(LocalDateTime.now().plusMinutes(10))
                .enabled(false)
                .build();
        memberRepository.save(member);
        sendEmail(member);
        return member;
    }
    /// 이메일 전송
        public void sendEmail(Member member) throws MessagingException {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(member.getUsername());
            helper.setSubject("이메일 인증 요청");

            // Thymeleaf를 사용하여 HTML 내용 생성
            Context context = new Context();
            context.setVariable("nickname", member.getNickname());
            context.setVariable("verificationLink", front_url + "/verifyemail?token=" + member.getEmailToken());

            String htmlContent = templateEngine.process("mail/verifymail", context);

            helper.setText(htmlContent, true);

            mailSender.send(message);
        }
    /// 이메일 인증
        public Member verifyEmail(String token){
            Member member = memberRepository.findByEmailToken(token).orElseThrow(() -> new NotFoundException("user not found"));
            if(member.getEmailTokenExpiry().isBefore(LocalDateTime.now())) {
                memberRepository.delete(member);
                throw new BadRequestException("email token expired");
            }
            if(member.isEnabled()) throw new BadRequestException("already enabled");
            member.setEnabled(true);
            member.setEmailToken(null);
            member.setEmailTokenExpiry(null);
            memberRepository.save(member);
            return member;
        }
    /// 현재 유저 email
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        return null; // 또는 예외 처리
    }
    public boolean isLogined(){
        String username = getCurrentUsername();
        return username != null;
    }
    /// 현재 멤버
    public Member getCurrentMember() {
        return memberRepository.findByNickname(getCurrentNickname()).orElse(null);
    }
    /// 현재 유저 닉네임
    public String getCurrentNickname() {
            Member member = memberRepository.findByUsername(getCurrentUsername()).orElse(null);
            if(member == null) return null;
            return member.getNickname();
        }
    /// 닉네임 변경
    public Member updateNickname(UpdateNicknameDTO updateNicknameDTO) {

        if(memberRepository.findByNickname(updateNicknameDTO.getNickname()).isPresent()) throw new ConflictException("nickname already exists");
        Member member = memberRepository.findByUsername(getCurrentUsername()).orElseThrow(() -> new NotFoundException("user not found"));
        member.setNickname(updateNicknameDTO.getNickname());
        memberRepository.save(member);
        return member;
    }
    /// 비밀번호 변경
    public Member updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Member member = memberRepository.findByUsername(getCurrentUsername()).orElseThrow(() -> new NotFoundException("user not found"));
        if(!passwordEncoder.matches(updatePasswordDTO.getOldPassword(), member.getPassword())){
            throw new ForbiddenException("not match");
        }
        if(updatePasswordDTO.getOldPassword().equals(updatePasswordDTO.getNewPassword())){
            throw new ConflictException("same password");
        }
        member.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
        memberRepository.save(member);
        return member;
    }
    /// admin 생성 ///
    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init(){
        if(memberRepository.count() > 0){ return;}
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Member member = Member.builder().username("admin@naver.com")
                .password(passwordEncoder.encode("admin"))
                .nickname("administrator")
                .enabled(true)
                .roles(List.of("ROLE_ADMIN"))
                .emailToken("JJoYul")
                .RefreshToken(null).build();
        memberRepository.save(member);
    }
    /// 토큰저장
    public void saveToken(String username, String token) {
        Member member = memberRepository.findByUsername(username).orElseThrow();
        if(member.getRefreshToken() == null || member.getRefreshToken().isEmpty()) {
            member.setRefreshToken(token);
            memberRepository.save(member);
        }
    }
    /// 리프레시 토큰
    public String getRefreshToken(String username) {
        Member member = memberRepository.findByUsername(username).orElse(null);
        return member.getRefreshToken();
    }

    /// admin전용
    public ResponseEntity<?> getUserList() {
        List<MemberResponseDTO> memberResponseDTOList = memberRepository.findAll().stream().map(MemberResponseDTO::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDTOList);
    }
    /// admin 전용
    @Transactional
    public ResponseEntity<?> addRole(RoleDTO roleDTO) {
        Member member = memberRepository.findByNickname(roleDTO.getNickname()).orElse(null);
        if(member == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not exist");
        member.setRoles(roleDTO.getRoles());
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

}
