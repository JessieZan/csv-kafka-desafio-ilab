package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Produto;

public interface ProdutoDao extends CrudRepository<Produto, Integer> {

}
