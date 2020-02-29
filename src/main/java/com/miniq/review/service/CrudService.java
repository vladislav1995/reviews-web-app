package com.miniq.review.service;

/**
 * @author Uladik
 */
public interface CrudService<T, ID> {

    T get(Long id);
    ID put(T data);
    ID update (T data);
    boolean delete(ID id);
}
