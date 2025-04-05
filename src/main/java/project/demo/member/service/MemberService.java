package project.demo.member.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
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
import project.demo.member.dto.UpdatePasswordDTO;
import project.demo.member.entity.Member;
import project.demo.member.repository.MemberRepository;

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
    public ResponseEntity<String> createMember(String username, String password, String nickname) throws MessagingException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(memberRepository.findByUsername(username).isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("exist");

        password = passwordEncoder.encode(password);
        Member member = Member.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .roles(List.of("ROLE_USER"))
                .emailToken(UUID.randomUUID().toString())
                .emailTokenExpiry(LocalDateTime.now().plusMinutes(10))
                .enabled(false)
                .build();
        memberRepository.save(member);
        sendEmail(member);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
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
        public ResponseEntity<?> verifyEmail(String token){
            Member member = memberRepository.findByEmailToken(token).orElse(null);
            if(member == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not exist");
            if(member.getEmailTokenExpiry().isBefore(LocalDateTime.now())) {
                memberRepository.delete(member);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("email token expired");
            }
            if(member.isEnabled()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("already enabled");
            member.setEnabled(true);
            member.setEmailToken(null);
            member.setEmailTokenExpiry(null);
            memberRepository.save(member);
            return ResponseEntity.status(HttpStatus.OK).body("success");
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
    public ResponseEntity<?> updateNickname(MemberDTO memberDTO) {
        Member member = memberRepository.findByUsername(getCurrentUsername()).orElse(null);
        if(member == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not exist");
        member.setNickname(memberDTO.getNickname());
        memberRepository.save(member);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
    /// 비밀번호 변경
    public ResponseEntity<?> updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Member member = memberRepository.findByUsername(getCurrentUsername()).orElse(null);
        if(member == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not exist");
        if(!passwordEncoder.matches(updatePasswordDTO.getOldPassword(), member.getPassword())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("not match");
        }
        member.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
        memberRepository.save(member);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
    /// admin 생성 ///
    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Member member = Member.builder().username("admin@naver.com")
                .password(passwordEncoder.encode("admin"))
                .nickname("administrator")
                .enabled(true)
                .roles(List.of("ROLE_ADMIN"))
                .emailToken("JJoYul")
                .RefreshToken(null).build();
        memberRepository.save(member);
        member = Member.builder().username("admin2@naver.com")
                .password(passwordEncoder.encode("admin"))
                .nickname("user")
                .enabled(true)
                .roles(List.of("ROLE_USER"))
                .emailToken("JJoYul")
                .RefreshToken(null).build();
        memberRepository.save(member);
        member = Member.builder().username("admin3@naver.com")
                .password(passwordEncoder.encode("admin"))
                .nickname("artist")
                .enabled(true)
                .roles(List.of("ROLE_ARTIST"))
                .emailToken("JJoYul")
                .RefreshToken(null).build();
        memberRepository.save(member);
    }
    /// 토큰저장
    public void saveTocken(String username, String token) {
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
    public ResponseEntity<?> addRole(RoleDTO roleDTO) {
        Member member = memberRepository.findByNickname(roleDTO.getNickname()).orElse(null);
        if(member == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not exist");
        member.setRoles(roleDTO.getRoles());
        memberRepository.save(member);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

}
