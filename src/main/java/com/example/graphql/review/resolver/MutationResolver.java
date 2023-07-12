package com.example.graphql.review.resolver;

import com.example.graphql.dto.Film;
import com.example.graphql.dto.Review;
import com.example.graphql.dto.User;
import com.example.graphql.review.service.ReviewService;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Uladik
 */
@Component
public class MutationResolver {

    private ReviewService reviewService;

    @Autowired
    public MutationResolver(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public DataFetcher<Long> putReview() {
        return dataFetchingEnvironment -> {
            Review data = convertInputData(dataFetchingEnvironment.getArgument("review"));
            return reviewService.put(data);
        };
    }

    private Review convertInputData(Map<String, Object> inputData) {
        Review dto = new Review();
        dto.setText((String) inputData.get("text"));
        dto.setUser(new User(Long.parseLong((String) inputData.get("userId")), (String) inputData.get("userName"), null));
        dto.setFilm(new Film(Long.parseLong((String) inputData.get("filmId")), null));
        return dto;
    }
}
