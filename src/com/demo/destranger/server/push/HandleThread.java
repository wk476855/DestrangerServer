package com.demo.destranger.server.push;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

public class HandleThread extends Thread {

	Socket socket;
	InputStream in = null;
	BufferedInputStream bis = null;
	OutputStream out = null;
	BufferedOutputStream bos = null;
	
	
	
	public HandleThread(Socket socket) {
		super();
		this.socket = socket;
	}
	
	@Override
	public void run() {
		StringBuilder sb = new StringBuilder();
		
		//initialize
		try {
			if(socket != null) {
				socket.setSoTimeout(1000);
				in = socket.getInputStream();
				if(in == null)	return;
				bis = new BufferedInputStream(in);
				out = socket.getOutputStream();
				bos = new BufferedOutputStream(out);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//send and receive
		while(true) {
			ProtocolPair pair = null;
			try {
				pair = receive();
				if(pair != null)
					System.out.println(pair.protocol+" : "+pair.content);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	private ProtocolPair receive() throws IOException {
        ProtocolPair pair = null;
        byte[] header = new byte[8];
        byte[] headerCopy = new byte[8];
        byte[] content = new byte[256];
        int len = 0;
        int cnt = 0;
        int contentLen = -1;
        int protocol = -1;
        int once;
        StringBuilder sb = new StringBuilder();

        while (true) {
            len = bis.read(header, cnt, 4 - cnt);
            if (len <= 0)
                break;
            System.arraycopy(header, cnt, headerCopy, cnt, len);
            cnt += len;
            if (cnt == 4) {
                contentLen = DataToolkit.byteArrayToInt(headerCopy, 4);
                break;
            }
        }

        if (contentLen != -1) {
            cnt = 0;
            while (true) {
                len = bis.read(header, cnt, 2 - cnt);
                System.arraycopy(header, cnt, headerCopy, cnt, len);
                cnt += len;
                if (cnt == 2) {
                    protocol = DataToolkit.byteArrayToInt(headerCopy, 2);
                    break;
                }
            }
        }

        if (protocol != -1) {
            cnt = 0;
            while (true) {
                once = Math.min(256, contentLen - cnt);
                if ((len = bis.read(content, 0, once)) > 0) {
                    cnt += len;
                    sb.append(new String(content, 0, len));
                    System.err.println(new String(content, 0, len));
                    if (cnt >= contentLen) {
                        pair = new ProtocolPair(protocol, sb.toString());
                        break;
                    }
                }
            }
        }
        return pair;
    }
	
}
