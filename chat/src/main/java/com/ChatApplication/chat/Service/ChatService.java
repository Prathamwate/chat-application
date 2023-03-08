package com.ChatApplication.chat.Service;

import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ChatApplication.chat.Model.ChatHistory;
import com.ChatApplication.chat.Repository.IChatHistory;


@Service
public class ChatService {

	@Autowired
	IChatHistory iChatHistory;

	public int saveMessage(ChatHistory chat) {
		 ChatHistory chatHistory = iChatHistory.save(chat);
	     return chatHistory.getChatId();
	}

	public JSONObject getChatsByUserId(int senderId) {
	   
	        List<ChatHistory> chatList = iChatHistory.getChatsByUserId(senderId);
	        JSONObject response = new JSONObject();

	        if(!chatList.isEmpty()) {
	            response.put("senderId" , chatList.get(0).getSender().getUserId());
	            response.put("senderName" , chatList.get(0).getSender().getFirstName());
	        }
	        JSONArray receivers = new JSONArray();
	        for (ChatHistory chats: chatList) {
	            JSONObject receiverObj = new JSONObject();
	            receiverObj.put("receiverId" , chats.getReceiver().getUserId());
	            receiverObj.put("receiverName" , chats.getReceiver().getFirstName());
	            receiverObj.put("message" , chats.getMessage());
	            receivers.put(receiverObj);
	        }
	        response.put("receivers", receivers);

	        return response;
	    }

	public JSONObject getConversation(int user1, int user2) {
		  JSONObject response = new JSONObject();
	        JSONArray conversations = new JSONArray();
	        List<ChatHistory> chatList = iChatHistory.getConversation(user1, user2);

	        for (ChatHistory chat : chatList) {
	            JSONObject messageObj = new JSONObject();
	            messageObj.put("chatId" , chat.getChatId());
	            messageObj.put("timestamp" , chat.getCreatedDate());
	            messageObj.put("senderName" , chat.getSender().getFirstName());
	            messageObj.put("message", chat.getMessage());
	            conversations.put(messageObj);
	        }
	        response.put("conversation", conversations);
	        return response;
	    }
	}


