package com.demo.destranger.server.push;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {

	ServerSocket server = null;
	
	@Override
	public void run() {
		int port = 12345;
		try {
			server = new ServerSocket(port, 10);
			if(server == null) {
				System.out.println("start sever error£¡, check the port " + port);
				return;
			}
			System.out.println("start server successfully !");
			while(!Thread.interrupted()) {
				Socket socket = server.accept();
				if(socket != null) {
					System.out.println(socket.getInetAddress().toString() + " connected !");
					new HandleThread(socket).start();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(server != null)
				try {
					server.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
