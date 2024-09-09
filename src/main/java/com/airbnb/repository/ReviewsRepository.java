package com.airbnb.repository;

import com.airbnb.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
    @Query("SELECT r FROM Reviews r WHERE " +
            "(:propertyId IS NULL OR r.property.id = :propertyId)" +
            "AND" +
            "(:userId IS NULL OR r.appUser.id = :userId)")
    List<Reviews> findByPropertyAndUser(@Param("propertyId") Long propertyId, @Param("userId") Long userId);
}