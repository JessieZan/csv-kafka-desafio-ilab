package com.example.demo.services;

import com.example.demo.dao.ClienteDao;
import com.example.demo.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.Console;
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
        String tamanhoArray = redis.read("Ultimo_ID");
        var jedis = new Jedis("http://localhost:6379");
        ArrayList<Cliente> clientesRedis = new ArrayList<>();
        
        for(int k=1; k <= Integer.parseInt(tamanhoArray) ;k++) {
            // List<Cliente> clientesBuscarRedis = jedis.hgetAll("cliente"+ k);
            Map<String, String> fields = jedis.hgetAll("cliente"+k);
            System.out.println(fields);
            // clientesRedis.add(fields);
        }

        Integer id = 1;

        List<Cliente> clientes = (List<Cliente>) dao.findAll();

        var redis = new RedisService();
        for (Cliente cliente : clientes) {
            jedis.hset("cliente"+ id, "id", cliente.getId().toString());
            jedis.hset("cliente"+ id, "nome", cliente.getNome());
            String key = id.toString();
            redis.write("Ultimo_ID", key, 30);
            id++;
        }
       
        return clientes;
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