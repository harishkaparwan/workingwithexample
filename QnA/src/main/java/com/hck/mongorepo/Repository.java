package com.hck.mongorepo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface Repository<T, ID extends Serializable> {
	T save(T entity);

	T findById(ID primaryKey);

	List<T> findAll();

	Page<T> findAll(Pageable pageable);

	Long count();

	void delete(T entity);

	boolean exists(ID primaryKey);
	// ... more functionality omitted.
}
