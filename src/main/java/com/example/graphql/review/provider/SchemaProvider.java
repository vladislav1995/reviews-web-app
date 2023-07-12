package com.example.graphql.review.provider;

import com.google.common.collect.ImmutableMap;
import com.example.graphql.dto.Film;
import com.example.graphql.dto.User;
import com.example.graphql.provider.AbstractSchemaProvider;
import com.example.graphql.review.resolver.FilmResolver;
import com.example.graphql.review.resolver.MutationResolver;
import com.example.graphql.review.resolver.QueryResolver;
import com.example.graphql.review.resolver.UserResolver;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

/**
 * @author Uladik
 */
@Component
public class SchemaProvider extends AbstractSchemaProvider {

    private static final Map<String, Function<Map<String, Object>, ?>> FETCHERS = ImmutableMap.of(
            "User", values -> new User(Long.parseLong((String) values.get("id")), null, null),
            "Film", values -> new Film(Long.parseLong((String) values.get("id")), null)
    );

    private static final Map<String, Class<?>> TYPES = ImmutableMap.of(
            "User", User.class,
            "Film", Film.class
    );

    private final QueryResolver queryResolver;

    private final MutationResolver mutationResolver;

    private final UserResolver userResolver;

    private final FilmResolver filmResolver;

    @Autowired
    public SchemaProvider(@Qualifier("schemaFileName") String schemaFileName,
            QueryResolver queryResolver,
            MutationResolver mutationResolver,
            UserResolver userResolver,
            FilmResolver filmResolver
    ) {
        super(schemaFileName, FETCHERS, TYPES);
        this.queryResolver = queryResolver;
        this.mutationResolver = mutationResolver;
        this.userResolver = userResolver;
        this.filmResolver = filmResolver;
    }

    @Override
    protected RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query").dataFetcher("getReviewById", queryResolver.getReview()))
                .type(newTypeWiring("Mutation").dataFetcher("putReview", mutationResolver.putReview()))
                .type(newTypeWiring("User").dataFetcher("reviews", userResolver.getReviewsByUserId()))
                .type(newTypeWiring("Film").dataFetcher("reviews", filmResolver.getReviewsByFilmId())).build();
    }

}
