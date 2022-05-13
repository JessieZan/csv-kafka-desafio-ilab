package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Pedido;
@Repository
public interface PedidoDao extends CrudRepository<Pedido, Integer>{

}
