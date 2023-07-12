package com.example.graphql.review.resolver;

import com.example.graphql.dto.Review;
import com.example.graphql.review.service.ReviewService;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Uladik
 */
@Component
public class QueryResolver {

    private ReviewService reviewService;

    @Autowired
    public QueryResolver(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public DataFetcher<Review> getReview() {
        return dataFetchingEnvironment -> {
            String id = dataFetchingEnvironment.getArgument("id");
            return reviewService.get(Long.parseLong(id));
        };
    }

}
