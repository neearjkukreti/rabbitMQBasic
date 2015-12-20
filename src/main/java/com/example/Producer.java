package com.example;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class Producer {

	private final String journalEntryQueue = "HelloWorldQueue";
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Bean
	Queue queue() {
		/*Create a durable queue*/
		boolean durable = true;    //durable - RabbitMQ will never lose the queue if a crash occurs
		//boolean exclusive = false;  //exclusive - if queue only will be used by one connection
		//boolean autoDelete = false; //autodelete - queue is deleted when last consumer unsubscribes
		return new Queue(journalEntryQueue, durable);//, exclusive, autoDelete);
	}
	
	@Bean
	TopicExchange exchange() {
		return new TopicExchange(""); // use default exchange
	}
	
	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(journalEntryQueue);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(journalEntryQueue);
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
	
	public void pushToQueue(String nbRefCode) {
		rabbitTemplate.convertAndSend(journalEntryQueue, nbRefCode);
	}
}