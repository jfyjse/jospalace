package com.joffy.jospalace.entity;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "listing")
public class ListingEntity implements Serializable {
    private static final long serialVersionUID = -448814212475655244L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id",nullable = false)//nullable=false
    private UserEntity userEntity;

    @Column(nullable = false)
    private String listingName;

    private String description;

    @Column(nullable = false)
    private Integer price;

    public String getListingName() {
        return listingName;
    }

    public void setListingName(String listingName) {
        this.listingName = listingName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
