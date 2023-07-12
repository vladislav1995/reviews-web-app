package com.example.graphql.review.service;

import com.example.graphql.dto.Review;
import com.example.graphql.dto.User;
import com.example.graphql.review.repository.ReviewRepository;
import com.example.graphql.service.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Uladik
 */
@Service
public class ReviewService implements CrudService<Review, Long> {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review get(Long id) {
        return reviewRepository.get(id);
    }

    public Map<Long, List<Review>> getReviewsByUserIds(Set<Long> userIds) {
        Map<User, List<Review>> reviewsByUserIds = reviewRepository.getReviewsByUserIds(userIds);
        return reviewsByUserIds.entrySet().stream().collect(Collectors.toMap(
                entry -> entry.getKey().getId(), Map.Entry::getValue
        ));
    }

    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.getReviewsByUserId(userId);
    }

    public List<Review> getReviewsByFilmId(Long filmId) {
        return reviewRepository.getReviewsByFilmId(filmId);
    }

    @Override
    public Long put(Review data) {
        return reviewRepository.put(data);
    }

    @Override
    public Long update(Review data) {
        return reviewRepository.update(data);
    }

    @Override
    public boolean delete(Long id) {
        return reviewRepository.delete(id);
    }
}
