package jms.jndi_variant;

import jms.OrderDTO;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class OrderConsumer {
    public static void main(String[] args) throws NamingException {
        Context jndiContext = new InitialContext();
        ConnectionFactory connectionFactory = (ConnectionFactory) jndiContext.lookup("jms/ee/ConnectionFactory");
        Destination topic = (Destination) jndiContext.lookup("jms/ee/Topic");
        try(JMSContext jmsContext = connectionFactory.createContext()) {
            while (true) {
                OrderDTO order = jmsContext.createConsumer(topic).receiveBody(OrderDTO.class);
                System.out.println("Order was received: " + order);
            }
        }
    }
}
