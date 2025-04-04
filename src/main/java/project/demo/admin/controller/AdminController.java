package project.demo.admin.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.demo.member.service.MemberService;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    public final MemberService memberService;

    @GetMapping("/userlist")
    public ResponseEntity<?> getUserList() {
        return memberService.getUserList();
    }
}
