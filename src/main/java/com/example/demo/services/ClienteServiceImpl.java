package com.example.demo.services;

import com.example.demo.dao.ClienteDao;
import com.example.demo.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClienteServiceImpl implements IClienteService {

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
    public boolean CriarCliente(Cliente cli) {
        if (cli != null) {
            dao.save(cli);
            return true;
        }
        return false;
    }


    @Override
    public boolean deletarCliente(Integer id) {
        Cliente cli = this.BuscarPorId(id);

        if (cli == null) return false;

        dao.deleteById(id);
        return true;
    }

}