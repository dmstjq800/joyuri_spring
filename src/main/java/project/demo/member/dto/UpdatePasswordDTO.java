package project.demo.member.dto;

import lombok.Getter;

@Getter
public class UpdatePasswordDTO {
    private String oldPassword;
    private String newPassword;
}
