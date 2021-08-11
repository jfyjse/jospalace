package com.joffy.jospalace.controller;

import com.joffy.jospalace.dto.UserDto;
import com.joffy.jospalace.model.UserDetailsRequestModel;
import com.joffy.jospalace.model.UserRest;
import com.joffy.jospalace.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails){
        UserRest returnValue = new UserRest();
        UserDto userDto = new  UserDto();
        BeanUtils.copyProperties(userDetails,userDto);
        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser,returnValue);
        return returnValue;
    }
    @GetMapping
    public String test(){
        return "here";
    }

}
