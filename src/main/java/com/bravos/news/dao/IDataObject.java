package com.bravos.news.dao;

import java.util.List;

interface IDataObject<T, ID> {

    T findById(ID id);

    List<T> findAll();

    T insert(T object);

    T update(T object);

    boolean delete(T object);

    List<T> findBySql(String sql, Object... args);

}
