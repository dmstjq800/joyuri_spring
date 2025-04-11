package project.demo.admin.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import project.demo.admin.dto.RoleDTO;
import project.demo.member.dto.MemberResponseDTO;
import project.demo.member.entity.Member;
import project.demo.member.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final MemberRepository memberRepository;

    /// admin전용 유저 리스트
    public ResponseEntity<?> getUserList() {
        List<MemberResponseDTO> memberResponseDTOList = memberRepository.findAll().stream().map(MemberResponseDTO::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDTOList);
    }
    /// admin 전용 권한 부여
    @Transactional
    public ResponseEntity<?> addRole(RoleDTO roleDTO) {
        Member member = memberRepository.findByNickname(roleDTO.getNickname()).orElse(null);
        if(member == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not exist");
        member.setRoles(roleDTO.getRoles());
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
}
