package com.example;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class Producer {

	private final String queueName = "HelloWorldQueue";
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Bean
	Queue queue() {
		/*Create a durable queue*/
		boolean durable = true;    //durable - RabbitMQ will never lose the queue if a crash occurs
		//boolean exclusive = false;  //exclusive - if queue only will be used by one connection
		//boolean autoDelete = false; //autodelete - queue is deleted when last consumer unsubscribes
		return new Queue(queueName, durable);//, exclusive, autoDelete);
	}
	
	@Bean
	TopicExchange exchange() {
		return new TopicExchange(""); // use default exchange
	}
	
	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queueName);
	}
	
	public void pushToQueue(String nbRefCode) {
		rabbitTemplate.convertAndSend(queueName, nbRefCode);
	}
}