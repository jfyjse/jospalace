package com.joffy.jospalace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    private static final long serialVersionUID = 775833933884518215L;
    private long id;
    private String userId;
    private String name;
    private String email;
    private String password;
    private String encryptedPassword;
    private String location;
    private Integer phone;

}
