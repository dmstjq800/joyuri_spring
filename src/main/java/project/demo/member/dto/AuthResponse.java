package project.demo.member.dto;


import lombok.Getter;
import lombok.Setter;
import project.demo.member.entity.Member;

import java.util.List;

@Getter
@Setter
public class AuthResponse {
    private String accessToken;
    private String username;
    private String nickname;
    private List<String> roles;
    public AuthResponse(String jwt, Member member) {
        accessToken = jwt;
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.roles = member.getRoles();
    }
}
