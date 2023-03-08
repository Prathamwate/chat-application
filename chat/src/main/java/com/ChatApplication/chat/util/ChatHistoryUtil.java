package com.ChatApplication.chat.util;


import java.sql.Timestamp;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ChatApplication.chat.Model.ChatHistory;
import com.ChatApplication.chat.Model.Status;
import com.ChatApplication.chat.Model.Users;
import com.ChatApplication.chat.Repository.IStatusRepo;
import com.ChatApplication.chat.Repository.IUserRepo;

@Component
public class ChatHistoryUtil {
	
	@Autowired
	IUserRepo iUserRepo;
	
	@Autowired
	IStatusRepo iStatusRepo;

	 public  JSONObject validateRequest(JSONObject requestObj) {
	        JSONObject errorObj = new JSONObject();
	        if(!requestObj.has("sender")) {
	            errorObj.put("sender", "Missing parameter");
	        }
	        if(!requestObj.has("receiver")) {
	            errorObj.put("receiver", "Missing parameter");
	        }
	        if(requestObj.has("message")) {
	            String message = requestObj.getString("message");
	            if(message.isBlank() || message.isEmpty()) {
	                errorObj.put("message", "message body can't be empty");
	            }
	        } else {
	            errorObj.put("message", "Missing parameter");
	        }
	        return errorObj;
	    }
	 
	  public ChatHistory setChatHistory(JSONObject requestObj) {
		  
	        ChatHistory chat = new ChatHistory();

	        String message = requestObj.getString("message");
	        int senderId =  requestObj.getInt("sender");
	        int receiverId =  requestObj.getInt("receiver");
	        Users senderUserObj = iUserRepo.findById(senderId).get();
	        Users receiverUserObj = iUserRepo.findById(receiverId).get();

	        chat.setReceiver(receiverUserObj);
	        chat.setSender(senderUserObj);
	        chat.setMessage(message);
	        Status status = iStatusRepo.findById(1).get();
	        chat.setStatusId(status);

	        Timestamp createdTime = new Timestamp(System.currentTimeMillis());
	        chat.setCreatedDate(createdTime);

	        return chat;

	    }

	

	
}
