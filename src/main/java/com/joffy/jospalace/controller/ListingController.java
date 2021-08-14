package com.joffy.jospalace.controller;

import com.joffy.jospalace.dto.UserDto;
import com.joffy.jospalace.entity.ListingEntity;
import com.joffy.jospalace.entity.UserEntity;
import com.joffy.jospalace.model.ListingAddRequestModel;
import com.joffy.jospalace.model.ListingAllModel;
import com.joffy.jospalace.service.UserService;
import com.joffy.jospalace.service.implementation.ListingServiceImpl;
import com.joffy.jospalace.service.implementation.UserServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public void CreateListing(@RequestBody ListingAddRequestModel listingAddItem){
        UserEntity userEntity = new UserEntity();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        BeanUtils.copyProperties(user,userEntity);
        System.out.println("haI "+user.getUserId());
        listingService.addToListing(listingAddItem,userEntity);

    }
    @GetMapping("/get")
    public List<ListingEntity> getAllListings(){
        List<ListingEntity> listingEntities= listingService.getAllListings();
        return listingEntities;
    }

    @PostMapping("/image/upload/{pid}")
    public void uploadListingImage(@RequestParam("file") MultipartFile file, @PathVariable(value = "pid") Long listingId ) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        listingService.uploadImage(file,listingId,user.getUserId());
    }

    @DeleteMapping("/image/delete/{pid}")
    public void deleteImage(@PathVariable(value = "pid") Long listingId ) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        listingService.deleteImage(listingId,user.getUserId());
    }

    @DeleteMapping("/delete/{pid}")
    public void deleteListing(@PathVariable(value = "pid") Long listingId) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        listingService.deleteListing(listingId,user.getUserId());
    }
}
