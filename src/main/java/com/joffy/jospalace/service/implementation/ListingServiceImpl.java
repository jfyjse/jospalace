package com.joffy.jospalace.service.implementation;

import com.joffy.jospalace.dto.UserDto;
import com.joffy.jospalace.entity.ListingEntity;
import com.joffy.jospalace.entity.UserEntity;
import com.joffy.jospalace.model.ListingAddRequestModel;
import com.joffy.jospalace.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListingServiceImpl {

    @Autowired
    ListingRepository listingRepository;

    public void addToListing(ListingAddRequestModel listingAddItem, UserEntity userId) {
        ListingEntity listing = new ListingEntity();
        listing.setListingName(listingAddItem.getListingName());
        listing.setDescription(listingAddItem.getDescription());
        listing.setPrice(listingAddItem.getPrice());
        listing.setUserEntity(userId);
        System.out.println("userid   "+userId.getUserId());
        listingRepository.save(listing);
    }
}
