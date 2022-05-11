package com.example.demo.services;

import java.util.List;

import com.example.demo.model.Cliente;

import java.util.List;

import com.example.demo.model.Cliente;

public interface IClienteService {
    public List<Cliente> listarTodos();
    public Cliente BuscarPorId(Integer id);
    public Cliente CriarCliente(Cliente cli);
    public boolean deletarCliente(Integer id);
}