package com.capgemini.jstk.carrental.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao <T, K extends Serializable> {

    T save(T entity);

    T findOne(K id);

    List<T> findAll();

    T update(T entity);

    void delete(T entity);

    void delete(K id);

    void deleteAll();

    void flush();

    void detach(T entity);

    long count();

    boolean exists(K id);
}
