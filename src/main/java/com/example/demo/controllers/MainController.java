package com.example.demo.controllers;


import com.example.demo.model.KafkaService;
import com.example.demo.utilitarios.S3Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ExecutionException;

@Controller
public class MainController {

    @GetMapping("")
    public String viewHomePage() {
        return "home";
    }

    @GetMapping("/upload")
    public String viewUploadPage() {
        return "Upload";
    }

    @GetMapping("/pedidos")
    public String viewOrdersPage() {
        return "pedidos";
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
            KafkaService.sendMessage("mensagem", message);

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

        KafkaService.sendMessage("mensagem", fileName);

        return "message";
    }


}