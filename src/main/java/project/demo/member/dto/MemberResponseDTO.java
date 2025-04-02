package project.demo.member.dto;

import lombok.Getter;
import lombok.Setter;
import project.demo.member.entity.Member;

import java.util.List;

@Getter
@Setter
public class MemberResponseDTO {
    String username;
    String nickname;
    List<String> roles;
    public MemberResponseDTO(Member member) {
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.roles = member.getRoles();
    }
}
