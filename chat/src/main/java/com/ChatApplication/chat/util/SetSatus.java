package com.ChatApplication.chat.util;


import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.ChatApplication.chat.Model.Status;

@Component
public class SetSatus {
	
	 public Status setStatus(String statusData) {
	        //Create new status object
	        Status status = new Status();

	        //statusData has data type as String but format is JSON, so we create object of json
	        JSONObject json = new JSONObject(statusData);
	        String statusName = json.getString("statusName");
	        String statusDescription = json.getString("statusDescription");

	        status.setStatusName(statusName);
	        status.setStatusDescription(statusDescription);

	        return status;
	     }
}
