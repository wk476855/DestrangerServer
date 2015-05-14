package com.demo.destranger.server.push;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class SendQueue {

	private static Map<String, LinkedBlockingQueue<ProtocolPair>> data = new HashMap<String, LinkedBlockingQueue<ProtocolPair>>();
	
	public static LinkedBlockingQueue<ProtocolPair> getQueue(String username) {
		if(!data.containsKey(username))
			register(username);
		return data.get(username);
	}
	
	public static void register(String username) {
		data.put(username, new LinkedBlockingQueue<ProtocolPair>());
	}
	
	public static boolean exist(String username) {
		return data.containsKey(username);
	}
	
}
