package com.joffy.jospalace.repository;

import com.joffy.jospalace.entity.ListingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ListingRepository extends JpaRepository<ListingEntity,Long> {
    @Query(value = "select * from listing",nativeQuery = true)
    List<ListingEntity> getAllListing();
}
