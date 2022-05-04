package com.example.demo.controllers;


import com.example.demo.model.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.utilitarios.S3Util;

import java.util.concurrent.ExecutionException;

@Controller
public class MainController {

    @GetMapping("")
    public String viewHomePage() {
        return "upload";
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

            message = "Your file has been uploaded successfully!";
        } catch (Exception ex) {
            message = "Error uploading file: " + ex.getMessage();
        }
         
        model.addAttribute("message", message);

        //KafkaService.sendMessage("mensagem",message);

        return "message";              
    }


}