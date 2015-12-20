package com.example;

import java.nio.charset.StandardCharsets;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;

@Service
public class Receiver implements IReceiver, ChannelAwareMessageListener{
	
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		String str = new String(message.getBody(), StandardCharsets.UTF_8);
		System.out.println("Got Message >> " + str);
		System.out.println("processing Message");
		Thread.sleep(2000);
		System.out.println("processing done");
	}

}
