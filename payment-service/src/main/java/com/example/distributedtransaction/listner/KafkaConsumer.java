package com.example.distributedtransaction.listner;

import com.example.distributedtransaction.model.DeductAmountInput;
import com.example.distributedtransaction.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @Autowired
    AccountService accountService;

    @KafkaListener(topics = "order", groupId = "group_1")
    public void consume(String value) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        DeductAmountInput input = objectMapper.readValue(value, DeductAmountInput.class);
        Integer remainingBalance = accountService.deductAmount(input.getId(), input.getAmount());
        if (remainingBalance != null) {
            System.out.println("Remaining balance is: " + remainingBalance);
        } else {
            System.out.println("Acount with id: " + input.getId() + " not found");
        }
    }
}
