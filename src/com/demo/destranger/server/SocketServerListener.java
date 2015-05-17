package com.demo.destranger.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.demo.destranger.server.push.ServerThread;

/**
 * Application Lifecycle Listener implementation class SocketServerListener
 *
 */
@WebListener
public class SocketServerListener implements ServletContextListener {

	ServerThread server = new ServerThread();
	
    /**
     * Default constructor. 
     */
    public SocketServerListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	server.interrupt();
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	server.start();    	
    	System.out.println("starting communication service....");
    }
	
}
