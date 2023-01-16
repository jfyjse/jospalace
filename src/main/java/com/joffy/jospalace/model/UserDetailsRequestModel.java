package com.joffy.jospalace.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsRequestModel {
    private String name;
    private String email;
    private String password;
    private String location;
    private Integer phone;


}
