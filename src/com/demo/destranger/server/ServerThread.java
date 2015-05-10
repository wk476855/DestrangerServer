package com.demo.destranger.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {

	ServerSocket server = null;
	
	@Override
	public void run() {
		int port = 12345;
		try {
			server = new ServerSocket(port);
			if(server == null) {
				System.out.println("����ʧ�ܣ�,��鿴�˿�"+port+"�Ƿ�ռ");
				return;
			}
			System.out.println("�����ɹ�!");
			while(!Thread.interrupted()) {
				Socket socket = server.accept();
				if(socket != null)
					new HandleThread(socket).start();
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
