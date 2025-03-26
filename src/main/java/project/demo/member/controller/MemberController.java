package project.demo.member.controller;


import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.demo.member.dto.MemberDTO;
import project.demo.member.dto.UpdatePasswordDTO;
import project.demo.member.service.MemberService;
import project.demo.security.resultdata.RsData;


@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody MemberDTO memberDTO) throws MessagingException {
        String username = memberDTO.getUsername();
        String password = memberDTO.getPassword();
        String nickname = memberDTO.getNickname();
        return memberService.createMember(username, password, nickname);
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(String token) {
        return memberService.verifyEmail(token);
    }

    @PostMapping("/updateNickname")
    public ResponseEntity<?> updateNickname(@RequestBody MemberDTO memberDTO) {
        return memberService.updateNickname(memberDTO);
    }
    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        if(updatePasswordDTO.getOldPassword().equals(updatePasswordDTO.getNewPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("equal password");
        }
        return memberService.updatePassword(updatePasswordDTO);
    }

}
