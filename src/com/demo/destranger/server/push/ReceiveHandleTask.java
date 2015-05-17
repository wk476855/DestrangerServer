package com.demo.destranger.server.push;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class ReceiveHandleTask implements Runnable{

	private MySocket mSocket;
	private String username;
	private LinkedBlockingQueue<ProtocolPair> queue;
	
	public ReceiveHandleTask(Socket socket) throws IOException {
		mSocket = new MySocket(socket);
	}
	
	@Override
	public void run() {
		boolean flag = true;
		try{
			while(true) {
				ProtocolPair pair = null;
				try {
					pair = mSocket.receive();
					if(pair == null)	continue;
					if(pair != null)
						System.out.println("receive from " + mSocket.getClient() + " : " +pair.protocol+" : "+pair.content);
					JSONObject json = new JSONObject(pair.content);
					if(json.has("session")) {
						String session = "";
						session = json.getString("session");
						if(!isSessionOutofDate(session)) {
							username = getUserBySession(session);
							if(flag) {
								flag = false;
								new Thread(new SendHandleTask(mSocket, username)).start();
							}
							switch(pair.protocol) {
								case Protocol.MESSAGE_SEND:
									Message msg = null;
									if(json.has("message")) {
										msg = new Gson().fromJson(json.getString("message"), Message.class);
										msg.setTime(new Date());
									}
									if(msg != null) {
										if(msg.getFrom().equals(username)) {
											String friendname = msg.getTo();
											queue = SendQueue.getQueue(friendname);
											queue.put(new ProtocolPair(Protocol.MESSAGE_RECEIVE, new Gson().toJson(msg)));
											JSONObject res = new JSONObject();;
											res.put("ret_code", ReturnCode.SUCCESS);
											queue.put(new ProtocolPair(Protocol.MESSAGE_SEND_COMFIRM, res.toString()));
										}
									}
									break;
								case Protocol.MESSAGE_RECEIVE_COMFIRM:
									
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch(InterruptedException e){
					e.printStackTrace();
				} 
			}
		}finally{
			try {
				mSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String getUserBySession(String session) {
		return session;
	}

	private boolean isSessionOutofDate(String session) {
		return false;
	}
		
	
}
