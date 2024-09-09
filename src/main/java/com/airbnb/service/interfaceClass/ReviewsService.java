package com.airbnb.service.interfaceClass;

import com.airbnb.entity.AppUser;
import com.airbnb.payload.ReviewsDto;

import java.util.List;

public interface ReviewsService {
    ReviewsDto createReview(Long propertyId, AppUser appUser, ReviewsDto dto);

    ReviewsDto getReviewById(Long id);

    ReviewsDto updateReview(Long id, ReviewsDto dto);

    void deleteReview(Long id);

    List<ReviewsDto> getReviewsByPropertyAndUser(Long propertyId, Long userId);
}
