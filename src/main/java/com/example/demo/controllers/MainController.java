package com.example.demo.controllers;


import com.example.demo.model.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.utilitarios.S3Util;
 
@Controller
public class MainController {
 
    @GetMapping("")
    public String viewHomePage() {
        return "upload";
    }
    @PostMapping("/upload")
    public String handleUploadForm(Model model, String description,
            @RequestParam("file") MultipartFile multipart) {
        String fileName = multipart.getOriginalFilename();
         
        System.out.println("Description: " + description);
        System.out.println("filename: " + fileName);
         
        String message = "";
         
        try {
            S3Util.uploadFile(fileName, multipart.getInputStream());
            message = "Your file has been uploaded successfully!";
        } catch (Exception ex) {
            message = "Error uploading file: " + ex.getMessage();
        }
         
        model.addAttribute("message", message);
         
        return "message";              
    }

    @Controller
    @RequestMapping(value = "/kafka")
    public class KafkaController {

        private final Producer producer;

        @Autowired
        KafkaController(Producer producer) {
            this.producer = producer;
        }

        @PostMapping(value = "/publish")
        public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
            this.producer.sendMessage(message);
        }
    }

}