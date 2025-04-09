package project.demo.member.dto;


import lombok.Getter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter

public class MemberDTO {

    @Email(message = "올바른 이메일 형식이 아닙니다")
    private  String username;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다")
    @Size(min = 7, max = 31, message = "비밀번호는 7자 이상 32자 미만이어야 합니다")
    private  String password;

    @NotBlank(message = "닉네임은 필수 입력 항목입니다")
    @Size(min = 2, max = 12, message = "닉네임은 2자 이상 13자 미만이어야 합니다")
    private String nickname;
    private String token;

}
