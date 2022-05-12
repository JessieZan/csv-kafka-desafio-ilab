package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.ProdutoDao;
import com.example.demo.model.Produto;

@Component
public class ProdutoServiceImpl implements IProdutoService{
	@Autowired
    private ProdutoDao dao;

    @Override
    public List<Produto> listarTodos() {
        return (List<Produto>) dao.findAll();
    }
    @Override
    public Produto BuscarPorId(Integer id) {
		return dao.findById(id).orElse(null);
	}



    @Override
    public boolean deletarCliente(Integer id) {
    	Produto Produt = this.BuscarPorId(id);
		
		if(Produt == null) {
			return false;
		}
		
		dao.deleteById(id);
		return true;
    }
}
