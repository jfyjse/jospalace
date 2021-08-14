package com.joffy.jospalace.controller;

import com.joffy.jospalace.dto.UserDto;
import com.joffy.jospalace.entity.UserEntity;
import com.joffy.jospalace.model.UserDetailsRequestModel;
import com.joffy.jospalace.model.UserRest;
import com.joffy.jospalace.service.UserService;
import com.joffy.jospalace.service.implementation.UserServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserServiceImpl userServiceimpl;

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails){
        UserRest returnValue = new UserRest();
        UserDto userDto = new  UserDto();
        BeanUtils.copyProperties(userDetails,userDto);
        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser,returnValue);
        return returnValue;
    }
    @GetMapping("/view/profile")
    public UserRest getUserProfile(){
        UserRest returnValue = new UserRest();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        UserDto userDto = userService.getUserById(user.getUserId());
        BeanUtils.copyProperties(userDto, returnValue);
        return returnValue;
    }

    @GetMapping("/deactivate")
    public void deactivateAccount(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        userServiceimpl.deactivateAccount(user.getUserId());

    }

}
