package com.example.demo.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Producer {

    @Value("${topic.name.producer}")
    private String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String message){
        log.info("Payload enviado: {}",message);
        kafkaTemplate.send(topicName, message);
    }

}
   /**@Service
    public class Producer {

        private static final Logger logger = LoggerFactory.getLogger(Producer.class);
        private static final String TOPIC = "users";

        @Autowired
        private KafkaTemplate<String, String> kafkaTemplate;

        public void sendMessage(String message) {
            logger.info(String.format("#### -> Producing message -> %s", message));
            this.kafkaTemplate.send(TOPIC, message);
        }
    }**/

