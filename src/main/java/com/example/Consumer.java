package com.example;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
	private final String queueName = "HelloWorldQueue";
	
	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		container.setAcknowledgeMode(AcknowledgeMode.AUTO);
		return container;
	}
	
	@Bean
	IReceiver receiver() {
        return new Receiver();
    }
	
	@Bean
	MessageListenerAdapter listenerAdapter(IReceiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
}
