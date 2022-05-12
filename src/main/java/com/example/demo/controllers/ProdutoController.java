package com.example.demo.controllers;

import com.example.demo.model.Produto;
import com.example.demo.services.IProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProdutoController {
    @Autowired
    private IProdutoService service;

    @GetMapping("/listaDeProdutos")
    public ModelAndView viewListaDeClientesPage(ModelMap model) {
        ModelAndView andView = new ModelAndView("/listaDeProdutos");
        Iterable<Produto> produts = service.listarTodos();
        andView.addObject("produtos", produts);
        return andView;

    }
}
