package com.example.demo.services;

import java.util.List;

import com.example.demo.model.Produto;

public interface IProdutoService {
    public List<Produto> listarTodos();
    public Produto BuscarPorId(Integer id);
    public boolean deletarCliente(Integer id);
}
