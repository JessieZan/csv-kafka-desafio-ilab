package com.example.demo.dao;

import com.example.demo.model.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteDao extends CrudRepository<Cliente, Integer> {
    public Cliente findByNome(String nome);
}
