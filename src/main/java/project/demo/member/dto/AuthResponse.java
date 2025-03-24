package project.demo.member.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String accessToken;

    public AuthResponse(String jwt) {
        accessToken = jwt;
    }
}
