package com.joffy.jospalace.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequestModel {
    private String name;
    private String location;
    private Integer phone;

}
