/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientejbperrin;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Administrator
 */
public class ClientJMSPerrin {

    //private static MessageListener listener;
    
    public static void main(String[] args) {
        
        Context context=null;
        ConnectionFactory factory;
        Connection connection=null;
        Destination destination;
        Session session;
        MessageProducer sender;
        MessageConsumer receiver = null;
        
        
          Properties props = new Properties();
                props.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.enterprise.naming.SerialInitContextFactory");//com.sun.enterprise.naming.SerialInitContextFactorycom.sun.jndi.fscontext.RefFSContextFactory");
                props.put(Context.URL_PKG_PREFIXES,"com.sun.enterprise.naming");
                props.put(Context.STATE_FACTORIES,"com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
               
        try {
            context = new InitialContext(props);
            factory = (ConnectionFactory)context.lookup("jms/MyConnectionFactory");
            destination = (Destination)context.lookup("jms/MyQueue");
            connection = factory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            sender = session.createProducer(destination);
            
            //listener = new Listener();
            receiver= session.createConsumer(destination);
            receiver.setMessageListener((message)->{
                try {
                    System.out.println("recu "+((TextMessage)message).getText());
                } catch (JMSException ex) {
                    System.out.println(ex);
                }
            });
        
            
            connection.start();
            TextMessage message = session.createTextMessage("salut monde jms");
            sender.send(message);
            System.out.println("Message envoyé "+message.getText());
            //Message m =receiver.receive(1000);
            
        } catch (NamingException | JMSException ex) {
            System.out.println(ex);
        }
        finally{
            try {
                context.close();
                connection.close();
            } catch (NamingException | JMSException ex) {
               System.out.println(ex);
            }
        }
        
        
        
    }
    
}
