package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.ClienteDao;
import com.example.demo.model.Cliente;

@Component
public class ClienteServiceImpl implements IClienteService{

    @Autowired
    private ClienteDao dao;

    @Override
    public List<Cliente> listarTodos() {
        return (List<Cliente>) dao.findAll();
    }
    @Override
    public Cliente BuscarPorId(Integer id) {
		return dao.findById(id).orElse(null);
	}

    @Override
    public Cliente CriarCliente(Cliente cli) {
        return dao.save(cli);
    }


    @Override
    public boolean deletarCliente(Integer id) {
        Cliente cli = this.BuscarPorId(id);
		
		if(cli == null) return false;
		
		dao.deleteById(id);
		return true;
    }

}