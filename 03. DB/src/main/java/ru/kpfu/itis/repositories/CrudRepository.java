package ru.kpfu.itis.repositories;

import java.util.List;

public interface CrudRepository<T> {
    List<T> findAll();
    T findById(Long id);
    T save(T t);
    void deleteById(Long id);
}
