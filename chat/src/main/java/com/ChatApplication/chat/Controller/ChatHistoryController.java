package com.ChatApplication.chat.Controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ChatApplication.chat.Model.ChatHistory;
import com.ChatApplication.chat.Service.ChatService;
import com.ChatApplication.chat.util.ChatHistoryUtil;

@RestController
@RequestMapping(value = "/api/v1/chat")
public class ChatHistoryController {
	
	@Autowired
	ChatHistoryUtil chatHistory;
	
	@Autowired
	ChatService chatService;
	
	 @PostMapping(value = "/send-message")
	    public ResponseEntity<String> saveMessage(@RequestBody String requestData) {
	        JSONObject requestObj = new JSONObject(requestData);
	        JSONObject errorList = chatHistory.validateRequest(requestObj);
	        if(errorList.isEmpty()) {
	            ChatHistory chat =chatHistory.setChatHistory(requestObj);
	            int chatId = chatService.saveMessage(chat);
	            return new ResponseEntity<>("message sent- " + chatId, HttpStatus.CREATED);
	        }else {
	            return new ResponseEntity<String>(errorList.toString(), HttpStatus.BAD_REQUEST);
	        }
	    }
	 
	 @GetMapping(value = "/get-chat")
	    public ResponseEntity<String> getChatsByUserId(@RequestParam int senderId) {
	        JSONObject response = chatService.getChatsByUserId(senderId);
	        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
	    }
	 
	 @GetMapping(value = "/get-conversation")
	    public ResponseEntity<String> getConversationBetweenTwoUsers(@RequestParam int user1,@RequestParam int user2) {
	        JSONObject response = chatService.getConversation(user1, user2);
	        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
	    }

}
