package com.joffy.jospalace.repository;

import com.joffy.jospalace.entity.ListingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<ListingEntity,Long> {
}
