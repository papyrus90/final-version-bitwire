package ro.sci.dao;

import java.util.Collection;

import ro.sci.domain.AbstractModel;

public interface BaseDAO<T extends AbstractModel> {
 
 Collection<T> getAll();
 
 T findById(Long id);
 
 T update(T model);
 
 boolean delete(T model);
}