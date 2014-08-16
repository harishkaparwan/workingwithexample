package com.hck.mongorepo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Sort;



public interface PagingAndSortingRepository<T, ID extends Serializable> extends Repository<T, ID> {
    List<T> findAll(Sort sort);
    Page<T> findAll(Pageable pageable);
}
