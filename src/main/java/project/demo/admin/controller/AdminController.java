package project.demo.admin.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.demo.admin.dto.RoleDTO;
import project.demo.member.service.MemberService;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    public final MemberService memberService;
    /// 유저 리스트
    @GetMapping("/userlist")
    public ResponseEntity<?> getUserList() {
        return memberService.getUserList();
    }

    /// 권한부여
    @PostMapping("/addrole")
    public ResponseEntity<?> grantRole(@RequestBody RoleDTO roleDTO) {
        return memberService.addRole(roleDTO);
    }
}
