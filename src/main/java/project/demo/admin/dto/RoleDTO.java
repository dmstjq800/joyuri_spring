package project.demo.admin.dto;


import lombok.Getter;

import java.util.List;

@Getter
public class RoleDTO {
    String nickname;
    List<String> roles;
}
