package com.demo.destranger.tools;

import java.util.Map;

import org.json.JSONObject;

import com.tencent.xinge.Message;
import com.tencent.xinge.XingeApp;

public class MessageManager {

	public boolean pushMessage(String username, String friendname, String message) {
		XingeApp push = new XingeApp(000, "A3CK257JL8AH");
		Message msg = new Message();
		msg.setTitle("message");
		Map<String, Object> custom = new HashMap<String, Object>();
		msg.setCustom(custom);
		JSONObject json = push.pushSingleAccount(0, friendname, message);
	}
	
}
