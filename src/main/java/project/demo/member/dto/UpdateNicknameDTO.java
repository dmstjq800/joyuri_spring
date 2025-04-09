package project.demo.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateNicknameDTO {
    @NotBlank(message = "닉네임은 필수 입력 항목입니다")
    @Size(min = 2, max = 12, message = "닉네임은 2자 이상 13자 미만이어야 합니다")
    private String nickname;
}
