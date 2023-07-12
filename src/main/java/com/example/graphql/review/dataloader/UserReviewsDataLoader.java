package com.example.graphql.review.dataloader;

import com.example.graphql.dto.Review;
import com.example.graphql.review.service.ReviewService;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author Uladik
 */
@Component
public class UserReviewsDataLoader implements MappedBatchLoader<Long, List<Review>> {

    private final ReviewService reviewService;

    @Autowired
    public UserReviewsDataLoader(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    public CompletionStage<Map<Long, List<Review>>> load(Set<Long> userIds) {
        return CompletableFuture.supplyAsync(() -> reviewService.getReviewsByUserIds(userIds));
    }
}
