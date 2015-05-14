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
	private Socket socket;
	private OutputStream out;
	private BufferedOutputStream bos;
	private LinkedBlockingQueue<ProtocolPair> queue;
	
	
	public SendHandleTask(Socket socket, String username) throws IOException {
		super();
		this.socket = socket;
		this.username = username;
		if(socket != null) {
			out = socket.getOutputStream();
			bos = new BufferedOutputStream(out);
			queue = SendQueue.getQueue(username);
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(bos == null)	return;
		try{
			while(true) {
				if(queue != null && !queue.isEmpty()) {
					ProtocolPair pair = queue.take();
					send(pair);
				}
			}
		}catch(InterruptedException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try {
				bos.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	  private byte[] generateHeader(int contentLen, int protocol){
	        int totalLen = contentLen + 6;
	        byte[] data = new byte[totalLen];
	        byte[] tmp = DataToolkit.intToByteArray(contentLen, 4);
	        System.arraycopy(tmp, 0, data, 0, 4);
	        tmp = DataToolkit.intToByteArray(protocol, 2);
	        System.arraycopy(tmp, 0, data, 4, 2);
	        return data;
	    }

	    public void send(ProtocolPair pair) throws IOException {
	        byte[] content = pair.content.getBytes();
	        byte[] data = generateHeader(content.length, pair.protocol);          //¼Ó°üÍ·
	        System.arraycopy(content, 0, data, 6, content.length);

	        if (socket != null && bos != null) {
	            bos.write(data);
	            bos.flush();
	        }
	    }

}
