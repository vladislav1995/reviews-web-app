package com.example.graphql.review.repository;

import com.example.graphql.dto.User;
import com.google.common.collect.ImmutableList;
import com.example.graphql.dto.Review;
import com.example.graphql.provider.JdbcTemplateProvider;
import com.example.graphql.repository.CrudRepository;
import com.example.graphql.review.mapper.ReviewRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Uladik
 */
@Repository
public class ReviewRepository implements CrudRepository<Review, Long> {

    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM apollo_federation.review WHERE review.id = ?";
    private static final String SELECT_BY_USER_ID_QUERY = "SELECT * FROM apollo_federation.review WHERE review.userId = ?";
    private static final String SELECT_BY_USER_IDS_QUERY = "SELECT * FROM apollo_federation.review WHERE review.userId IN (:user_ids)";
    private static final String SELECT_BY_FILM_ID_QUERY = "SELECT * FROM apollo_federation.review WHERE review.filmId = ?";
    private static final String INSERT_QUERY = "INSERT INTO apollo_federation.review (text, userName, userId, filmId) VALUES (?, ?, ?, ?)";

    private JdbcTemplate template;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private ReviewRowMapper reviewRowMapper;

    @Autowired
    public ReviewRepository(JdbcTemplateProvider provider, ReviewRowMapper reviewRowMapper) {
        this.template = provider.getTemplate();
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(template.getDataSource());
        this.reviewRowMapper = reviewRowMapper;
    }

    @Override
    public Review get(Long id) {
        return template.queryForObject(SELECT_BY_ID_QUERY, reviewRowMapper, id);
    }

    public List<Review> getReviewsByUserId(Long userId) {
        return template.query(SELECT_BY_USER_ID_QUERY, reviewRowMapper, userId);
    }

    public List<Review> getReviewsByFilmId(Long filmId) {
        return template.query(SELECT_BY_FILM_ID_QUERY, reviewRowMapper, filmId);
    }

    public Map<User, List<Review>> getReviewsByUserIds(Set<Long> userIds) {
        SqlParameterSource queryParams = new MapSqlParameterSource("user_ids", userIds);
        List<Review> reviews = namedParameterJdbcTemplate.query(SELECT_BY_USER_IDS_QUERY, queryParams, reviewRowMapper);
        return reviews.stream().collect(Collectors.groupingBy(Review::getUser));
    }

    @Override
    public Long put(Review data) {
        return (long) template.update(INSERT_QUERY, convertData(data));
    }

    @Override
    public Long update(Review data) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    private Object[] convertData(Review data) {
        return ImmutableList.of(data.getText(), data.getUser().getName(), data.getUser().getId(), data.getFilm().getId()).toArray();
    }
}
