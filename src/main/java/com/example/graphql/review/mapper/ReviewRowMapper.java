package com.example.graphql.review.mapper;

import com.example.graphql.dto.Film;
import com.example.graphql.dto.Review;
import com.example.graphql.dto.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Uladik
 */
@Component
public class ReviewRowMapper implements RowMapper<Review> {

    @Override
    public Review mapRow(ResultSet resultSet, int i) throws SQLException {
        Review result = new Review();
        result.setId(resultSet.getLong("id"));
        result.setText(resultSet.getString("text"));
        result.setUser(new User(resultSet.getLong("userId"), resultSet.getString("userName"), null));
        result.setFilm(new Film(resultSet.getLong("filmId"), null));
        return result;
    }

}
