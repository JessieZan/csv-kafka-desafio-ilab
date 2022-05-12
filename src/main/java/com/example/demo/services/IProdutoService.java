package com.example.demo.services;

import com.example.demo.model.Produto;

import java.util.List;

public interface IProdutoService {
    public List<Produto> listarTodos();

    public Produto BuscarPorId(Integer id);

    public boolean deletarCliente(Integer id);
}
