package com.miniq.review.service;

import com.miniq.example.graphql.dto.Review;
import com.miniq.review.repository.CrudRepository;
import com.miniq.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Uladik
 */
@Service
public class ReviewService implements CrudRepository<Review, Long> {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review get(Long id) {
        return reviewRepository.get(id);
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
