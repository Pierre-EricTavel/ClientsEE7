/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientejbperrin;

import itta.cours.*;
import java.rmi.RemoteException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Administrator
 */
public class ClientEJBPerrin {


    public static void main(String[] args) {
       

            
            try {
                Properties props = new Properties();
                props.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.enterprise.naming.SerialInitContextFactory");//com.sun.enterprise.naming.SerialInitContextFactorycom.sun.jndi.fscontext.RefFSContextFactory");
                props.put(Context.URL_PKG_PREFIXES,"com.sun.enterprise.naming");
                props.put(Context.STATE_FACTORIES,"com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
               
                InitialContext context = new InitialContext(props);
                PerrinSessionBeanLocal perrin = (PerrinSessionBeanLocal)context.lookup("java:global/EJBPerrin/PerrinSessionBean");
                System.out.println( perrin.helloWorld());
                
            } catch (NamingException ex) {
                Logger.getLogger(ClientEJBPerrin.class.getName()).log(Level.SEVERE, null, ex);
            }

        try {
            Context initialContext = new InitialContext();
            PerrinSessionBeanLocal perrin = (PerrinSessionBeanLocal)javax.rmi.PortableRemoteObject.narrow(
                                initialContext.lookup("java:global/EJBPerrin/PerrinSessionBean"), itta.cours.PerrinSessionBeanLocal.class); 
            initialContext.getEnvironment().forEach((k,v)->{
                System.out.println(k+" "+v);
            });
            System.out.println( perrin.helloWorld());
        } catch (NamingException ex) {
            Logger.getLogger(ClientEJBPerrin.class.getName()).log(Level.SEVERE, null, ex);
        }


        
        
    }
    
}
