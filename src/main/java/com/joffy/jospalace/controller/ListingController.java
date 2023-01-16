package com.joffy.jospalace.controller;

import com.joffy.jospalace.dto.UserDto;
import com.joffy.jospalace.entity.ListingEntity;
import com.joffy.jospalace.entity.UserEntity;
import com.joffy.jospalace.model.ListingAddRequestModel;
import com.joffy.jospalace.service.implementation.ListingServiceImpl;
import com.joffy.jospalace.service.implementation.UserServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("listing")
public class ListingController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    ListingServiceImpl listingService;


    @PostMapping
    @ApiImplicitParams({
            @ApiImplicitParam(
                    required = true,
                    name = "authorization",
                    value = "bearer-token",
                    dataType = "java.lang.String",
                    paramType = "header"
            )
    })
    public void CreateListing(@RequestBody ListingAddRequestModel listingAddItem){
        UserEntity userEntity = new UserEntity();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        BeanUtils.copyProperties(user,userEntity);
        System.out.println("haI "+user.getUserId());
        listingService.addToListing(listingAddItem,userEntity);

    }
    @ApiImplicitParams({
            @ApiImplicitParam(
                    required = true,
                    name = "authorization",
                    value = "bearer-token",
                    dataType = "java.lang.String",
                    paramType = "header"
            )
    })
    @GetMapping("/get")
    public List<ListingEntity> getAllListings(){
        return listingService.getAllListings();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    required = true,
                    name = "authorization",
                    value = "bearer-token",
                    dataType = "java.lang.String",
                    paramType = "header"
            )
    })
    @PostMapping("/image/upload/{pid}")
    public void uploadListingImage(@RequestParam("file") MultipartFile file, @PathVariable(value = "pid") Long listingId ) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        listingService.uploadImage(file,listingId,user.getUserId());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    required = true,
                    name = "authorization",
                    value = "bearer-token",
                    dataType = "java.lang.String",
                    paramType = "header"
            )
    })
    @DeleteMapping("/image/delete/{pid}")
    public void deleteImage(@PathVariable(value = "pid") Long listingId ) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        listingService.deleteImage(listingId,user.getUserId());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    required = true,
                    name = "authorization",
                    value = "bearer-token",
                    dataType = "java.lang.String",
                    paramType = "header"
            )
    })
    @DeleteMapping("/delete/{pid}")
    public void deleteListing(@PathVariable(value = "pid") Long listingId) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        listingService.deleteListing(listingId,user.getUserId());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    required = true,
                    name = "authorization",
                    value = "bearer-token",
                    dataType = "java.lang.String",
                    paramType = "header"
            )
    })
    @PostMapping("/send/mail/{pid}")
    public void sendMail(@PathVariable(value = "pid") Long listingId) throws MessagingException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        listingService.sendMail(listingId,user.getUserId());
    }
}
