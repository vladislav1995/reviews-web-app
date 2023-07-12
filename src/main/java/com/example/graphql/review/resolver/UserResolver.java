package com.example.graphql.review.resolver;

import com.example.graphql.dto.Review;
import com.example.graphql.dto.User;
import com.example.graphql.review.dataloader.UserReviewsDataLoader;
import graphql.schema.DataFetcher;
import org.dataloader.DataLoader;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Uladik
 */
@Component
public class UserResolver {
    public DataFetcher<CompletableFuture<List<Review>>> getReviewsByUserId() {
        return environment -> {
            User user = environment.getSource();
            DataLoader<Long, List<Review>> dataLoader = environment.getDataLoader(UserReviewsDataLoader.class.getSimpleName());
            return dataLoader.load(user.getId());
        };
    }

}
