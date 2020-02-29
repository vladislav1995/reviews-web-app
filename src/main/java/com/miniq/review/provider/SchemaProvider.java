package com.miniq.review.provider;

import com.apollographql.federation.graphqljava._Entity;
import com.miniq.example.graphql.dto.Film;
import com.miniq.example.graphql.dto.Review;
import com.miniq.example.graphql.dto.User;
import com.miniq.example.graphql.provider.AbstractSchemaProvider;
import com.miniq.review.resolver.FilmResolver;
import com.miniq.review.resolver.MutationResolver;
import com.miniq.review.resolver.QueryResolver;
import com.miniq.review.resolver.UserResolver;
import graphql.language.InlineFragment;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.TypeResolver;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

/**
 * @author Uladik
 */
@Component
public class SchemaProvider extends AbstractSchemaProvider {

    @Autowired
    private QueryResolver queryResolver;

    @Autowired
    private MutationResolver mutationResolver;

    @Autowired
    private UserResolver userResolver;

    @Autowired
    private FilmResolver filmResolver;

    @Autowired
    public SchemaProvider(@Qualifier("schemaFileName") String schemaFileName) throws IOException {
        super(schemaFileName);
    }

    @Override
    protected DataFetcher dataFetcher() {
        return (env -> env.<List<Map<String, Object>>>getArgument(_Entity.argumentName).stream().map(values -> {
            if ("User".equals(values.get("__typename"))) {
                final Object id = values.get("id");
                if (id instanceof String) {
                    return new User(Long.parseLong((String) id), null, null);
                }
            }
            if ("Film".equals(values.get("__typename"))) {
                final Object id = values.get("id");
                if (id instanceof String) {
                    return new Film(Long.parseLong((String) id), null);
                }
            }
            return null;
        }).collect(Collectors.toList()));
    }

    @Override
    protected TypeResolver typeResolver() {
        return env -> {
            final Object src = env.getObject();
            if (src instanceof User) {
                return env.getSchema().getObjectType("User");
            }
            if (src instanceof Film) {
                return env.getSchema().getObjectType("Film");
            }
            return null;
        };
    }

    protected RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query").dataFetcher("getReviewById", queryResolver.getReview()))
                .type(newTypeWiring("Mutation").dataFetcher("putReview", mutationResolver.putReview()))
                .type(newTypeWiring("User").dataFetcher("reviews", userResolver.getReviewsByUserId()))
                .type(newTypeWiring("Film").dataFetcher("reviews", filmResolver.getReviewsByFilmId())).build();
    }

}
