package com.example.demo.services;

import com.example.demo.dao.ClienteDao;
import com.example.demo.model.Cliente;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aj.org.objectweb.asm.Type;
import redis.clients.jedis.Jedis;

import java.io.Console;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private ClienteDao dao;

    RedisService redis = new RedisService();

    @Override
    public List<Cliente> listarTodos() {
        List<Cliente> clientes = (List<Cliente>) dao.findAll();
        
        Integer id = 1;
        ArrayList<Cliente> lista = new ArrayList<Cliente>();

        for (Cliente cliente : clientes) {
            var key = "cliente"+id;
            var redisService = new RedisService();
            redisService.write(key, cliente.getId().toString() + "-"+ cliente.getNome(), 30);
            var valor = redisService.read(key);
            var valorredis = valor.split("-");
            Cliente clienteRedis = new Cliente(Integer.parseInt(valorredis[0]), valorredis[1]);
            lista.add(clienteRedis);
            id++;
        }
        // var listaa = new Gson();
        // System.out.println(listaa.toJson(lista));
        return lista;
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