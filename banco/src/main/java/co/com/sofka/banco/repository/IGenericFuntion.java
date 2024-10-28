package co.com.sofka.banco.repository;

import java.util.List;

public interface IGenericFuntion<T> {

    T update(T item);
    T save(T item);
    T delete(T item);
    T findById(Long id);
    Long deleteByElementId(Long id);
    List<T> getAll();
}
