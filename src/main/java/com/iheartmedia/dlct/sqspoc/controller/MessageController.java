package com.iheartmedia.dlct.sqspoc.controller;

import com.iheartmedia.dlct.sqspoc.service.SQSMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sqs")
public class MessageController {

    @Autowired
    SQSMessageSender sqsMessageSender;

    @PostMapping(value = "/send")
    public void sendMessageToSQS(@RequestParam("message") String message){
        sqsMessageSender.sendMessage(message);
    }

    @PostMapping(value = "/sendQueueManagingTemplate")
    public void sendMessageToSQSWithQueueManagingTemplate(@RequestParam("message") String message){
        sqsMessageSender.sendMessageWithQueueManagingTemplate(message);
    }

}
