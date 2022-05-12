package com.example.demo.dao;

import com.example.demo.model.Produto;
import org.springframework.data.repository.CrudRepository;

public interface ProdutoDao extends CrudRepository<Produto, Integer> {

}
