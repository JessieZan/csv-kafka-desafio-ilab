package com.example.demo.controllers;

import com.example.demo.model.Cliente;
import com.example.demo.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ClienteController {
    @Autowired
    private IClienteService service;


    @GetMapping("/clientes")
    public String listarTodos(ModelMap model) {

        return "clientes";
    }

    @GetMapping("/cadastrarCliente")
    public String viewCadastroClientePage(ModelMap model) {
        return "cadastrarCliente";
    }

    @GetMapping("/listaDeClientes")
    public ModelAndView viewListaDeClientesPage(ModelMap model) {
        ModelAndView andView = new ModelAndView("/listaDeClientes");
        Iterable<Cliente> clients = service.listarTodos();
        andView.addObject("clientes", clients);
        return andView;

    }

    @GetMapping("/buscarCliente")
    public ModelAndView viewClientesPage(ModelMap model) {
        ModelAndView andView = new ModelAndView("/cliente");
        Iterable<Cliente> cliente = service.listarTodos();
        andView.addObject("clientes", cliente);
        return andView;

    }


    @PostMapping("/cadastrarCliente")
    public String postClient(String nomeCliente, Model model) {
        String message = "";
        try {
            System.out.println(nomeCliente);
            Cliente cli = new Cliente(nomeCliente);

            service.CriarCliente(cli);
            message = "cliente cadastrado com sucesso:";
        } catch (Exception e) {
            e.printStackTrace();
            message = "Erro ao cadastrar cliente: " + e.getMessage();
        }
        model.addAttribute("message", message);
        return "message";
    }
}
