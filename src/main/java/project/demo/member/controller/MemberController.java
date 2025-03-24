package project.demo.member.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.demo.member.dto.MemberDTO;
import project.demo.member.service.MemberService;
import project.demo.security.resultdata.RsData;


@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody MemberDTO memberDTO) {
        String username = memberDTO.getUsername();
        String password = memberDTO.getPassword();
        return memberService.createMember(username, password);
    }


}
