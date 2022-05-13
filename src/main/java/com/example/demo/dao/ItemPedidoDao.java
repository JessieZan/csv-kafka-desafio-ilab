package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ItemPedido;

@Repository
public interface ItemPedidoDao extends CrudRepository<ItemPedido, Integer>{

}
