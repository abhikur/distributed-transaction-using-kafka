package com.example.distributedtransaction;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

@RestController
public class OrderController {

    private final KafkaProducer<String, String> producer;

    public OrderController() {
        String bootstrapServers = "127.0.0.1:9092";

        Properties properties = new Properties();
        properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        producer = new KafkaProducer<>(properties);
    }

    @PostMapping("/order")
    public ResponseEntity<String> orderItem(@RequestParam int id, @RequestParam int amount) {
        productDeductMoneyTopic(id, amount);
        return ResponseEntity.ok("Order has been made. Amount "+ amount +" will be deducted from your account");
    }

    private void productDeductMoneyTopic(int id, int amount) {
        String jsonInput = "{\"id\": " + id + ", \"amount\": " + amount + "}";
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("order", "order_service", jsonInput);
        producer.send(producerRecord);
    }
}
