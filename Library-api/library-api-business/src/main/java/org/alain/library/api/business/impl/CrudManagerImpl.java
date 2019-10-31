package org.alain.library.api.business.impl;

import org.alain.library.api.business.contract.CrudManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class CrudManagerImpl<T> implements CrudManager<T> {

    JpaRepository<T, Long> repository;

    public CrudManagerImpl(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    @Override
    public Optional<T> findOne(Long id){
        return repository.findById(id);
    }

    @Override
    public List<T> findAll(){
        return repository.findAll();
    }

    @Override
    public void delete(Long id){
        if(repository.findById(id).isPresent())
            repository.deleteById(id);
    }

    @Override
    public T save(T obj){
       return repository.save(obj);
    }
}
