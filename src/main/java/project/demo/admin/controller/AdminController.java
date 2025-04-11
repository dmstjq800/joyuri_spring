package project.demo.admin.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.demo.admin.dto.RoleDTO;
import project.demo.admin.service.AdminService;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    public final AdminService adminService;
    /// 유저 리스트
    @GetMapping("/userlist")
    public ResponseEntity<?> getUserList() {
        return adminService.getUserList();
    }

    /// 권한부여
    @PostMapping("/addrole")
    public ResponseEntity<?> grantRole(@RequestBody RoleDTO roleDTO) {
        return adminService.addRole(roleDTO);
    }
}
