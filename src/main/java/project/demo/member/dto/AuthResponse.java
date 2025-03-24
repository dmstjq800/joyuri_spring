package project.demo.member.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String accessToken;
    private String username;
    public AuthResponse(String jwt, String username) {
        accessToken = jwt;
        this.username = username;
    }
}
