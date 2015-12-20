package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner{
	
	@Autowired
	Producer p;
	
    public static void main(String[] args){
        SpringApplication.run(DemoApplication.class, args);
    }

	@Override
	public void run(String... arg0) throws Exception {
		
		for(int i=0; i<10; i++){
			p.pushToQueue("Hello World >>" + i);
		}
	}
}
