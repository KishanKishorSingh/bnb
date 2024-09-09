package com.airbnb.repository;

import com.airbnb.entity.ImageUpload;
import com.airbnb.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageUploadRepository extends JpaRepository<ImageUpload, Long> {
    List<ImageUpload> findByProperty(Property property);
}