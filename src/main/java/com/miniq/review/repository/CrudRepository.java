package com.miniq.review.repository;

/**
 * @author Uladik
 */
public interface CrudRepository<T, ID> {

    T get(ID id);
    ID put(T data);
    ID update (T data);
    boolean delete(ID id);

}
