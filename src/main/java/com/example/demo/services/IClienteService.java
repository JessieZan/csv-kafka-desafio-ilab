package com.example.demo.services;

import com.example.demo.model.Cliente;

import java.util.List;

public interface IClienteService {
    public List<Cliente> listarTodos();

    public Cliente BuscarPorId(Integer id);

    public boolean CriarCliente(Cliente cli);

    public boolean deletarCliente(Integer id);
}