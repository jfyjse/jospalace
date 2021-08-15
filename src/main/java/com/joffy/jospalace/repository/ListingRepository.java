package com.joffy.jospalace.repository;

import com.joffy.jospalace.entity.ListingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ListingRepository extends JpaRepository<ListingEntity,Long> {
    @Query(value = "select * from listing",nativeQuery = true)
    List<ListingEntity> getAllListing();

    @Transactional
    @Modifying
    @Query(value = "delete from listing l where l.id=?1 ", nativeQuery = true)
    void deleteByListingId(Long listingId);

    @Query(value = "select user_id from listing l where l.id = ?1",nativeQuery = true)
    Long findUserByListingId(Long listingId);

    @Query(value = "select listing_name  from listing l where l.id = ?1",nativeQuery = true)
    String findProductName(Long listingId);
}
