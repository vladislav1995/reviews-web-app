package com.example.graphql.review.resolver;

import com.example.graphql.dto.Film;
import com.example.graphql.dto.Review;
import com.example.graphql.review.service.ReviewService;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Uladik
 */
@Component
public class FilmResolver {

    private ReviewService reviewService;

    @Autowired
    public FilmResolver(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public DataFetcher<List<Review>> getReviewsByFilmId() {
        return dataFetchingEnvironment -> {
            Film film = dataFetchingEnvironment.getSource();
            return reviewService.getReviewsByFilmId(film.getId());
        };
    }

}
