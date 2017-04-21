package com.hc360.mobile.webservice.service;

import javax.servlet.http.HttpServlet;

import org.apache.activemq.command.ActiveMQDestination;

import com.hc360.jms.JMSConsumer;
import com.hc360.jms.activemq.ActiveMQ;

public class JMSService extends HttpServlet{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2609453949565932209L;
	
	public void init(){
		try {
			JMSConsumer jconsumer = new JMSConsumer("my1", "HC.WX",ActiveMQDestination.QUEUE_TYPE, new OrderMessageListener());
			ActiveMQ.initConsumer(jconsumer);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		remenssage();
	}
	public void remenssage(){
		try {
			JMSConsumer jconsumer = new JMSConsumer("cgt", "hc.note",ActiveMQDestination.QUEUE_TYPE, new LyMessageListener());
			ActiveMQ.initConsumer(jconsumer);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
