package com.iheartmedia.dlct.sqspoc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class SQSMessageReceiver {

    private static final Logger logger = LoggerFactory.getLogger(SQSMessageReceiver.class);

    static private final String QUEUE_NAME = "dlct_poc.fifo";

    @SqsListener(QUEUE_NAME)
    public void receiveMessage(String message, @Header("SenderId") String senderId) {
        logger.info("Received message: {}, having SenderId: {}", message, senderId);
    }

}
