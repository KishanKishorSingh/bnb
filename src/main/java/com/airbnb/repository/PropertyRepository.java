package com.airbnb.repository;

import com.airbnb.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query("SELECT p FROM Property p WHERE (:cityId IS NULL OR p.city.id = :cityId) AND " +
            "(:countryId IS NULL OR p.country.id = :countryId)")
    List<Property> findByCityIdAndCountryId(@Param("cityId") Long cityId, @Param("countryId") Long countryId);
}
