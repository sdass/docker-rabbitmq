package com.subra;

import java.io.IOException;
import java.time.temporal.Temporal;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel; 
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

public class App 
{
	static Logger log = LoggerFactory.getLogger(App.class);
	
    public static void main( String[] args ) throws IOException, TimeoutException
    {
        log.info( "Hello World!" );
       // IntStream.range(1, 3).forEach(App::send);
        consume();
    }
    
    public static void send(int x){ //can be asynchronous sending. meaning for loop will wrap the connection
    	log.info( "in send()" );
    	String quename = "test-queue";
    	String host_docker = "dockerdev";
    	
    	ConnectionFactory factory = new ConnectionFactory();
    	factory.setHost(host_docker);
    	
    	for(int i=0; i<2; i++){
    	try(Connection con = factory.newConnection(); 
    			Channel channel = con.createChannel() ){
    				channel.queueDeclare(quename, false, false, false, null);
    				//for begins for(int i=0; i<5; i++){
    				String message = "Hello with new connection Message" + (int)(Math.random() *100)/2;
    				channel.basicPublish("", quename, null, message.getBytes());
    				log.info(" [x] message sent: - - - - " + message );
    				Thread.sleep(2000);
    				//}//for ends
    			} catch(Exception e){
    				e.printStackTrace();
    			}  
    	}
    }//send
    
    
    public static void consume() throws IOException, TimeoutException{ //Always synchronous. Keep the connection open to get messages.
    	log.info( "in consume()" );
    	String quename = "test-queue";
    	String host_docker = "dockerdev";
    	
    	ConnectionFactory factory = new ConnectionFactory();
    	factory.setHost(host_docker);
    	
    	Connection con = factory.newConnection(); 
    	Channel channel = con.createChannel();
    	//good practice keep quename decalre pices. why? 
    	//incase consumer starts before sender and quename is not create.. some error debug, etc.			
    	channel.queueDeclare(quename, false, false, false, null);
    	
    	String whatisit = channel.basicConsume(quename, true, (consumerTag, message) ->{    		
    		log.info("srvConsumerTag=" + consumerTag + " :srvDeliverdMsg=" + message); 
    		log.info("message extracted=" + new String(message.getBody() ));
    		},
    			
    		/* new DeliverCallback() {	@Override public void handle(String consumerTag, Delivery develverMessage) throws IOException {	} }, */
		
		new CancelCallback() {			
			@Override public void handle(String consumerTagfromserver) throws IOException { 
				log.info("Server sent cancelled: " + consumerTagfromserver + "!?");			
			}
		}  	);
		log.info(" [x] message or whatisit: - - - - " + whatisit );


	    	
    }//consume    
}
