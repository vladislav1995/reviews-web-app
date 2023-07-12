package com.example.graphql.review.resource;

import com.example.graphql.provider.AbstractSchemaProvider;
import com.example.graphql.request.GraphQLRequestBody;
import com.example.graphql.review.dataloader.UserReviewsDataLoader;
import com.example.graphql.review.provider.SchemaProvider;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.example.graphql.util.ResponseUtils.patchExecutionResult;
import static org.dataloader.DataLoaderFactory.newMappedDataLoader;

/**
 * @author Uladik
 */
@Path("/graphql/review")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Component
public class GraphQLResource {

    @Autowired
    private AbstractSchemaProvider provider;

    @Autowired
    private UserReviewsDataLoader userReviewsDataLoader;

    @POST
    public Response graphql(GraphQLRequestBody graphQLRequestBody) {
        return Response.ok(executeGraphQLQuery(graphQLRequestBody)).build();
    }

    @GET
    public Response graphqlGet(@BeanParam GraphQLRequestBody graphQLRequestBody) {
        return Response.ok(executeGraphQLQuery(graphQLRequestBody)).build();
    }

    public void setProvider(SchemaProvider provider) {
        this.provider = provider;
    }

    private Object executeGraphQLQuery(GraphQLRequestBody body) {
        ExecutionResult result = provider.getGraphQL().execute(convertRequestBody(body));
        return patchExecutionResult(result);
    }

    private ExecutionInput convertRequestBody(GraphQLRequestBody body) {
        return new ExecutionInput.Builder()
                .operationName(body.getOperationName())
                .query(body.getQuery())
                .variables(body.getVariables())
                .dataLoaderRegistry(buildDLRegistry())
                .build();
    }

    private DataLoaderRegistry buildDLRegistry() {
        return DataLoaderRegistry.newRegistry()
                .register(userReviewsDataLoader.getClass().getSimpleName(), newMappedDataLoader(userReviewsDataLoader))
                .build();
    }
}
