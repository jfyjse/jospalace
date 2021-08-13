package com.joffy.jospalace.controller;

import com.joffy.jospalace.dto.UserDto;
import com.joffy.jospalace.entity.ListingEntity;
import com.joffy.jospalace.entity.UserEntity;
import com.joffy.jospalace.model.ListingAddRequestModel;
import com.joffy.jospalace.service.UserService;
import com.joffy.jospalace.service.implementation.ListingServiceImpl;
import com.joffy.jospalace.service.implementation.UserServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("listing")
public class ListingController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    ListingServiceImpl listingService;


    @PostMapping
    public void CreateListing(@RequestBody ListingAddRequestModel listingAddItem){
        UserEntity userEntity = new UserEntity();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        BeanUtils.copyProperties(user,userEntity);
        System.out.println("haI "+user.getUserId());
        listingService.addToListing(listingAddItem,userEntity);

    }
}
