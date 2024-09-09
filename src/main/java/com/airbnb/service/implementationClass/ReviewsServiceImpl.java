package com.airbnb.service.implementationClass;

import com.airbnb.entity.AppUser;
import com.airbnb.entity.Reviews;
import com.airbnb.payload.ReviewsDto;
import com.airbnb.repository.AppUserRepository;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.ReviewsRepository;
import com.airbnb.service.interfaceClass.ReviewsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewsServiceImpl implements ReviewsService {

    private final ReviewsRepository reviewsRepository;
    private final PropertyRepository propertyRepository;

    public ReviewsServiceImpl(ReviewsRepository reviewsRepository, PropertyRepository propertyRepository, AppUserRepository appUserRepository) {
        this.reviewsRepository = reviewsRepository;
        this.propertyRepository = propertyRepository;
    }

    // Create a new review
    @Override
    public ReviewsDto createReview(Long propertyId, AppUser user, ReviewsDto dto) {
        Reviews review = new Reviews();
        review.setDescription(dto.getDescription());
        review.setRatings(dto.getRatings());
        review.setProperty(propertyRepository.findById(propertyId).get());
        review.setAppUser(user);
        Reviews savedReview = reviewsRepository.save(review);
        return mapToDto(savedReview);
    }

    // Get a review by ID
    @Override
    public ReviewsDto getReviewById(Long id) {
        Reviews review = reviewsRepository.findById(id).get();
        return mapToDto(review);
    }

    // Update a review
    @Override
    public ReviewsDto updateReview(Long id, ReviewsDto dto) {
        Reviews review = reviewsRepository.findById(id).get();
        review.setRatings(dto.getRatings());
        Reviews updatedReview = reviewsRepository.save(review);
        return mapToDto(updatedReview);
    }

    // Delete a review
    @Override
    public void deleteReview(Long id) {
        reviewsRepository.deleteById(id);

    }

    // Get reviews by property and/or user
    @Override
    public List<ReviewsDto> getReviewsByPropertyAndUser(Long propertyId, Long userId) {
        List<Reviews> reviews = reviewsRepository.findByPropertyAndUser(propertyId, userId);
        return reviews.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    // Convert Reviews entity to ReviewsDto

    private ReviewsDto mapToDto(Reviews review) {
        ReviewsDto dto = new ReviewsDto();
        dto.setId(review.getId());
        dto.setDescription(review.getDescription());
        dto.setRatings(review.getRatings());
        dto.setProperty(review.getProperty());
        dto.setAppUser(review.getAppUser());
        return dto;
    }
}

