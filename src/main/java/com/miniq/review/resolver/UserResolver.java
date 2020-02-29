package com.miniq.review.resolver;

import com.miniq.example.graphql.dto.Review;
import com.miniq.example.graphql.dto.User;
import com.miniq.review.service.ReviewService;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Uladik
 */
@Component
public class UserResolver {

    private ReviewService reviewService;

    @Autowired
    public UserResolver(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public DataFetcher<List<Review>> getReviewsByUserId() {
        return dataFetchingEnvironment -> {
            User user = dataFetchingEnvironment.getSource();
            return reviewService.getReviewsByUserId(user.getId());
        };
    }

    public ReviewService getReviewService() {
        return reviewService;
    }
}
