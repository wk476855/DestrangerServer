package com.demo.destranger.server.push;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class SendHandleTask implements Runnable {

	private String username;
	private MySocket mSocket;
	private LinkedBlockingQueue<ProtocolPair> queue;
	
	
	public SendHandleTask(MySocket mSocket, String username) throws IOException {
		super();
		this.mSocket = mSocket;
		this.username = username;
		queue = SendQueue.getQueue(username);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			while(true) {
				if(queue != null && !queue.isEmpty()) {
					ProtocolPair pair = queue.take();
					mSocket.send(pair);
					System.out.println("send to " + mSocket.getClient() + " : " + pair.protocol + " : " + pair.content);
				}
			}
		}catch(InterruptedException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
