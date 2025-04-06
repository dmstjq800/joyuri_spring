package project.demo.member.controller;


import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.demo.member.dto.MemberDTO;
import project.demo.member.dto.UpdatePasswordDTO;
import project.demo.member.entity.Member;
import project.demo.member.service.MemberService;



@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /// 회원가입
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody MemberDTO memberDTO) throws MessagingException {
        Member member = memberService.createMember(memberDTO);
        return ResponseEntity.ok("Member joined successfully : " + member.getUsername());
    }
    /// 이메일인증
    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(String token) {
        Member member = memberService.verifyEmail(token);
        return ResponseEntity.ok("Email verified successfully : " + member.getUsername());
    }
    /// 닉네임 수정
    @PostMapping("/updateNickname")
    public ResponseEntity<?> updateNickname(@RequestBody MemberDTO memberDTO) {
        Member member = memberService.updateNickname(memberDTO);
        return ResponseEntity.ok("Nickname updated successfully : " + member.getNickname());
    }
    /// 비밀번호 변경
    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        Member member = memberService.updatePassword(updatePasswordDTO);
        return ResponseEntity.ok("Password updated successfully : " + member.getUsername());
    }
}
