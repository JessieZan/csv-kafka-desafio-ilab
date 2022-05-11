package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Cliente;
import com.example.demo.services.IClienteService;

@Controller
public class ClienteController {
    @Autowired
    private IClienteService service;

    @GetMapping("/clientes")
    public String listarTodos(ModelMap model) {
        List<Cliente> clientes = service.listarTodos();

        model.addAttribute("clientes", clientes);

        return "mostraInfos";
    }

    @PostMapping("/clientes")
    public String postClient(Cliente cliente, Model model) {
        try {
            service.CriarCliente(cliente);
            model.addAttribute("message", "Cliente cadastrado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Erro ao salvar cliente: " + e.getMessage());
        }
        return "cliente";
    }
}
