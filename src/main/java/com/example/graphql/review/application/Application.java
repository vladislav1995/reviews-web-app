package com.example.graphql.review.application;

import com.example.graphql.review.resource.GraphQLResource;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author Uladik
 */
public class Application extends ResourceConfig {

    public Application() {
        register(GraphQLResource.class);
    }

}
