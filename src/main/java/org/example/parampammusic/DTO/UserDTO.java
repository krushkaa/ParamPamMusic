package org.example.parampammusic.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private String login;
    private String password;
    private String email;
    private String telNumber;
    private String bonusPoint;
    private String roleId;

}
