package com.demo.destranger.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

public class HandleThread extends Thread {

	Socket socket;
	InputStream in = null;
	BufferedInputStream bis = null;
	
	
	public HandleThread(Socket socket) {
		super();
		this.socket = socket;
	}
	
	@Override
	public void run() {
		System.out.println("connected");
		StringBuilder sb = new StringBuilder();
		try {
			in = socket.getInputStream();
			bis = new BufferedInputStream(in);
			byte[] buffer = new byte[1024];
			int len;
			while((len = bis.read(buffer, 0, 1024)) > 0) {
				sb.append(new String(buffer, 0, len));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str = sb.toString();
		if(str != null) {
			try {
				System.out.println(str);
				JSONObject json = new JSONObject(str);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
