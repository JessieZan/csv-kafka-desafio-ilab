package com.example.demo.model;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaService {
    public static void sendMessage(String key, String value) throws InterruptedException, ExecutionException {

        var producer = new KafkaProducer<String, String>(properties());
        var record = new ProducerRecord<String, String>(System.getenv("KAFKA_TOPIC"), key, value);

        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("Mensagem enviada com sucesso para: " + data.topic() + " | partition " + data.partition() + "| offset " + data.offset() + "| tempo " + data.timestamp());
        };

        producer.send(record, callback).get();
        producer.close();
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv("KAFKA_HOST"));
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}

/**
 * @Slf4j
 * @Service
 * @RequiredArgsConstructor public class Producer {
 * @Value("${topic.name.producer}") private String topicName;
 * <p>
 * private final KafkaTemplate<String, String> kafkaTemplate;
 * <p>
 * public void send(String message){
 * log.info("Payload enviado: {}",message);
 * kafkaTemplate.send(topicName, message);
 * }
 * <p>
 * }
 * @Service public class Producer {
 * <p>
 * private static final Logger logger = LoggerFactory.getLogger(Producer.class);
 * private static final String TOPIC = "users";
 * @Autowired private KafkaTemplate<String, String> kafkaTemplate;
 * <p>
 * public void sendMessage(String message) {
 * logger.info(String.format("#### -> Producing message -> %s", message));
 * this.kafkaTemplate.send(TOPIC, message);
 * }
 * }
 * @Service public class Producer {
 * <p>
 * private static final Logger logger = LoggerFactory.getLogger(Producer.class);
 * private static final String TOPIC = "users";
 * @Autowired private KafkaTemplate<String, String> kafkaTemplate;
 * <p>
 * public void sendMessage(String message) {
 * logger.info(String.format("#### -> Producing message -> %s", message));
 * this.kafkaTemplate.send(TOPIC, message);
 * }
 * }
 **/
/**@Service public class Producer {

private static final Logger logger = LoggerFactory.getLogger(Producer.class);
private static final String TOPIC = "users";

 @Autowired private KafkaTemplate<String, String> kafkaTemplate;

 public void sendMessage(String message) {
 logger.info(String.format("#### -> Producing message -> %s", message));
 this.kafkaTemplate.send(TOPIC, message);
 }
 }**/

