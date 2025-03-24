package project.demo.security.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.demo.member.dto.AuthResponse;
import project.demo.member.dto.MemberDTO;
import project.demo.member.service.MemberService;
import project.demo.security.jwt.JWTutil;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final MemberService MemberService;
    private final JWTutil jwtUtil;
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody MemberDTO memberDTO) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(memberDTO.getUsername(), memberDTO.getPassword())
            );
        }catch (UsernameNotFoundException | BadCredentialsException e){
            throw new Exception("사용자 정보 불일치", e);
        } catch (Exception authException) {
            throw new Exception("로그인 실패", authException);
        }


        final String jwt = jwtUtil.generateToken(memberDTO);
        ///
        final String refreshToken = jwtUtil.generateRefreshToken(jwt);
        memberService.saveTocken(memberDTO.getUsername(), refreshToken);
        ///
        return ResponseEntity.ok(new AuthResponse(jwt, memberDTO.getUsername()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> createRefreshToken(@RequestBody MemberDTO memberDTO) throws Exception {

        String refreshToken = memberService.getRefreshToken(memberDTO.getUsername());

        if(refreshToken == null || jwtUtil.isTokenExpired(refreshToken)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Refresh Token Expired");
        }


        String jwt = jwtUtil.generateToken(memberDTO);
        return ResponseEntity.ok(new AuthResponse(jwt, memberDTO.getUsername()));
    }
//    BadCredentialsException: 가장 흔한 예외 중 하나로, 제공된 비밀번호가 저장된 비밀번호와 일치하지 않을 때 발생합니다.
//    DisabledException: 사용자가 비활성화된 상태일 때 발생합니다. 예를 들어, 계정이 잠겼거나 관리자에 의해 비활성화된 경우입니다.
//    LockedException: 사용자의 계정이 잠겨 있을 때 발생합니다.
//    AccountExpiredException: 사용자의 계정 유효 기간이 만료되었을 때 발생합니다.
//    CredentialsExpiredException: 사용자의 비밀번호 유효 기간이 만료되었을 때 발생합니다.
//    UsernameNotFoundException: 제공된 사용자 이름에 해당하는 사용자를 찾을 수 없을 때 발생합니다.

}
