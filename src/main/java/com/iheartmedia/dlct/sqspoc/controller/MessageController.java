package com.iheartmedia.dlct.sqspoc.controller;

import com.iheartmedia.dlct.sqspoc.models.SimpleModel;
import com.iheartmedia.dlct.sqspoc.service.SQSMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sqs")
public class MessageController {

    @Autowired
    SQSMessageSender sqsMessageSender;

    @PostMapping(value = "/send")
    public void sendMessageToSQS(@RequestBody SimpleModel message){
        sqsMessageSender.sendMessage(message);
    }

    @PostMapping(value = "/sendQueueManagingTemplate")
    public void sendMessageToSQSWithQueueManagingTemplate(@RequestBody SimpleModel message){
        sqsMessageSender.sendMessageWithQueueManagingTemplate(message);
    }

}
