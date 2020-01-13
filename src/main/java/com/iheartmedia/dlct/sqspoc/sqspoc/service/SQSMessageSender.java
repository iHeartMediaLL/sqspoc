package com.iheartmedia.dlct.sqspoc.sqspoc.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class SQSMessageSender {

    private String sqsuri;

    private String queueName;

    private AmazonSQS sqs;

    private QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    public SQSMessageSender(@Value("${sqs.uri}") String sqsuri,
                            @Value("${sqs.queueName}") String queueName,
                            @Value("${cloud.aws.credentials.accessKey}") String accessKey,
                            @Value("${cloud.aws.credentials.secretKey}") String secretKey){
        AWSCredentials credentials;
        this.sqsuri = sqsuri;
        this.queueName = queueName;
        credentials = new BasicAWSCredentials(accessKey, secretKey);
        sqs = AmazonSQSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_2)
                .build();

        AmazonSQSAsync sqsAsync;

        sqsAsync = AmazonSQSAsyncClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_2)
                .build();

        queueMessagingTemplate = new QueueMessagingTemplate(sqsAsync);
    }

    public void sendMessage(String message){

        Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
        messageAttributes.put("AttributeOne", new MessageAttributeValue()
                .withStringValue("This is an attribute")
                .withDataType("String"));

        SendMessageRequest sendMessageToFifoQueue = new SendMessageRequest()
                .withQueueUrl(sqsuri)
                .withMessageBody(message)
                .withMessageGroupId(UUID.randomUUID().toString())
                .withMessageDeduplicationId(UUID.randomUUID().toString())
                .withMessageAttributes(messageAttributes);

        sqs.sendMessage(sendMessageToFifoQueue);
    }

    public void sendMessageWithQueueManagingTemplate(String message){

        Map<String, Object> headers = new HashMap<>();
        headers.put("message-group-id", UUID.randomUUID().toString());
        headers.put("message-deduplication-id", UUID.randomUUID().toString());

        this.queueMessagingTemplate.convertAndSend(queueName, message, headers);

    }


}
