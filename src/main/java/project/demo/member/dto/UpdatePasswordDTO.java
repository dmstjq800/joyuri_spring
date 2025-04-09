package project.demo.member.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdatePasswordDTO {
    private String oldPassword;

    @Size(min = 7, max = 31, message = "비밀번호는 7자 이상 32자 미만이어야 합니다")
    private String newPassword;
}
