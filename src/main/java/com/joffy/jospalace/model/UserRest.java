package com.joffy.jospalace.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRest {
    private String userId;
    private String name;
    private String email;
    private String location;
    private Integer phone;

}
