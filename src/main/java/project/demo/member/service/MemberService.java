package project.demo.member.service;


import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import project.demo.member.entity.Member;
import project.demo.member.repository.MemberRepository;
import project.demo.security.resultdata.RsData;

import java.util.List;

@Service
@RequiredArgsConstructor
@Generated
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
//    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));
    }

    public ResponseEntity<String> createMember(String username, String password, String nickname) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if(memberRepository.findByUsername(username).isPresent())
            return ResponseEntity.status(HttpStatus.CONFLICT).body("exist");

        password = passwordEncoder.encode(password);
        Member member = Member.builder()
                .username(username)
                .password(password).nickname(nickname).build();
        memberRepository.save(member);

        return ResponseEntity.status(HttpStatus.CREATED).body("success");
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
    /// 현재 유저 닉네임
    public String getCurrentNickname() {
            Member member = memberRepository.findByUsername(getCurrentUsername()).orElse(null);
            if(member == null) return null;
            return member.getNickname();
        }

    /// admin 생성 ///
    @EventListener(ApplicationReadyEvent.class)
    public void init(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Member member = Member.builder().username("admin@naver.com")
                .password(passwordEncoder.encode("admin"))
                .nickname("administrator")
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

    public String getRefreshToken(String username) {
        Member member = memberRepository.findByUsername(username).orElse(null);
        return member.getRefreshToken();
    }
}
