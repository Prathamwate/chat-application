package com.ChatApplication.chat.Service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ChatApplication.chat.Model.Status;
import com.ChatApplication.chat.Repository.IStatusRepo;

@Service
public class StatusService {
	@Autowired
    IStatusRepo iStatusRepo;
	
	public int saveStatus(Status status) {
		
		  Status statusObj = iStatusRepo.save(status);
		  return statusObj.getStatusId();
		   
		
	}
   

}
