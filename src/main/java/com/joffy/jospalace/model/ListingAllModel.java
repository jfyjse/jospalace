package com.joffy.jospalace.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListingAllModel {

    private Long id;
    private String listingName;
    private String description;
    private Integer price;
}
