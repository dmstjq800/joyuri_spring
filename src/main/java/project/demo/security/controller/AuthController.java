package project.demo.security.controller;


import io.jsonwebtoken.ExpiredJwtException;
import jdk.jfr.Enabled;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.demo.member.dto.AuthResponse;
import project.demo.member.dto.MemberDTO;
import project.demo.member.entity.Member;
import project.demo.member.service.MemberService;
import project.demo.security.exeption.customexception.BadRequestException;
import project.demo.security.exeption.customexception.ReLoginException;
import project.demo.security.exeption.customexception.UnauthorizedException;
import project.demo.security.jwt.JWTutil;

import javax.management.relation.RelationException;

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
            throw new BadRequestException("사용자 정보 불일치 ");
        } catch (DisabledException e) {
            return ResponseEntity.status(488).body("E-mail 인증 실패");
        } catch (Exception e){
            throw new Exception("로그인 실패", e);
        }
        Member member = memberService.findByusername(memberDTO.getUsername());
        final String jwt = jwtUtil.generateToken(member);
        final String refreshToken = jwtUtil.generateRefreshToken(jwt);
        memberService.saveToken(member.getUsername(), refreshToken);
        return ResponseEntity.ok(new AuthResponse(jwt, member));
    }
    /// 리프레시 토큰 검증
    @PostMapping("/refresh")
    public ResponseEntity<?> createRefreshToken(@RequestBody MemberDTO memberDTO) throws Exception {
        Member member = memberService.findByusername(memberDTO.getUsername());
        String refreshToken = memberService.getRefreshToken(member.getUsername());
        String OldToken = memberDTO.getToken();
        if(!memberDTO.getUsername().equals(jwtUtil.extractUsernameFromExpired(OldToken))) {
            throw new UnauthorizedException(memberDTO.getUsername() + "  " + jwtUtil.extractUsernameFromExpired(OldToken));
        }
        try{
            if (refreshToken != null) {
                jwtUtil.isTokenExpired(refreshToken);
            }
        }catch (ExpiredJwtException e){
            throw new ReLoginException("refresh token expired, Please login again");
        }

        String jwt = jwtUtil.generateToken(member);
        return ResponseEntity.ok(new AuthResponse(jwt, member));
    }
//    BadCredentialsException: 가장 흔한 예외 중 하나로, 제공된 비밀번호가 저장된 비밀번호와 일치하지 않을 때 발생합니다.
//    DisabledException: 사용자가 비활성화된 상태일 때 발생합니다. 예를 들어, 계정이 잠겼거나 관리자에 의해 비활성화된 경우입니다.
//    LockedException: 사용자의 계정이 잠겨 있을 때 발생합니다.
//    AccountExpiredException: 사용자의 계정 유효 기간이 만료되었을 때 발생합니다.
//    CredentialsExpiredException: 사용자의 비밀번호 유효 기간이 만료되었을 때 발생합니다.
//    UsernameNotFoundException: 제공된 사용자 이름에 해당하는 사용자를 찾을 수 없을 때 발생합니다.

}
