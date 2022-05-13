package com.example.demo.controllers;


import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.ItemPedidoDao;
import com.example.demo.dao.PedidoDao;
import com.example.demo.model.Cliente;
import com.example.demo.model.ItemPedido;
import com.example.demo.model.KafkaService;
import com.example.demo.model.Pedido;
import com.example.demo.model.Produto;
import com.example.demo.services.IClienteService;
import com.example.demo.services.IProdutoService;
import com.example.demo.utilitarios.S3Util;

@Controller
public class MainController {
    @Autowired
	private PedidoDao daoPedido;
    
    @Autowired
	private ItemPedidoDao daoItemPedido;
    
    @Autowired
    private IClienteService serviceCliente;
    
    @Autowired
    private IProdutoService serviceProduto;
    
    @GetMapping("")
    public String viewHomePage() {
        return "home";
    }
   
    @GetMapping("/upload")
    public String viewUploadPage() {
    	System.out.println("testeee");
        return "Upload";
    }
    
    @GetMapping("/pedidos")
    public ModelAndView viewOrdersPage() {
    	ModelAndView andView = new ModelAndView("/fazerPedido");
    	Iterable <Cliente> clients =  serviceCliente.listarTodos();
    	Iterable <Produto> produts =  serviceProduto.listarTodos();
    	andView.addObject("clientes" ,clients);
    	andView.addObject("produtos" ,produts);
        return andView;

    }
    @PostMapping("/pedidos")
    public String postProduct( Integer clienteID, Integer produto1ID,Integer quantProduto1,String enderecoEntrega, Model model) {
        String message = "";
        try {
        	//modelAndView.addObject("cliente", cliente.get());
        	System.out.println(model);
        	System.out.println(quantProduto1);
        	System.out.println(enderecoEntrega);
        	System.out.println(clienteID);
        	System.out.println(produto1ID);
        	Produto produto1 = serviceProduto.BuscarPorId(produto1ID);
        	Cliente clienteAchado = serviceCliente.BuscarPorId(clienteID);
        	System.out.println(produto1.getCodigo());
        	System.out.println(produto1.getNome());
        	System.out.println(produto1.getValor());
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto1);
            itemPedido.setQuantidade(quantProduto1);
            Pedido pedido = new Pedido();
            pedido.setValorTotal(produto1.getValor()*quantProduto1);
            pedido.setEnderecoEntrega(enderecoEntrega);
           pedido.setCliente(clienteAchado);
            itemPedido.setPedido(pedido);
            itemPedido.setValor(produto1.getValor()*quantProduto1);
            daoPedido.save(pedido);
            daoItemPedido.save(itemPedido);
            //pedido.setItens(arrayItens);
       /* ArrayList<ItemPedido> arrayItens = new ArrayList();
        	for(int i = 0; i<quantProduto1;i++) {
        		arrayItens.add(Produto1);
        		
        	}*/
            message = "pedido feito com sucesso:";
        } catch (Exception e) {
            e.printStackTrace();
            message = "Erro ao fazer pedido: "+ e.getMessage() ;
        }
        model.addAttribute("message", message);
        return "message";
    }

    


    @PostMapping("/upload")
    public String handleUploadForm(Model model, String description,
            @RequestParam("file") MultipartFile multipart) throws ExecutionException, InterruptedException {


        String fileName = multipart.getOriginalFilename();
         
        System.out.println("Description: " + description);
        System.out.println("filename: " + fileName);
         
        String message = "";
        try {
            message = "Your file has been uploaded successfully!";
            KafkaService.sendMessage("mensagem",message);

        } catch (Exception ex) {
            message = "Error uploading file: " + ex.getMessage();
            System.out.println(message);
        }

        try {
            S3Util.uploadFile(fileName, multipart.getInputStream());

            message = "upload do arquivo feito com sucesso!";
        } catch (Exception ex) {
            message = "Erro ao fazer upload do arquivo: " + ex.getMessage();
        }
         
        model.addAttribute("message", message);

        //KafkaService.sendMessage("mensagem",message);

        return "message";              
    }


}