package com.ChatApplication.chat.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ChatApplication.chat.Model.Status;
import com.ChatApplication.chat.Service.StatusService;
import com.ChatApplication.chat.util.SetSatus;

@RestController
@RequestMapping("api/v1/status")
public class StatusController {
		
	@Autowired
	private StatusService statusService;
	
	@Autowired
	private SetSatus setSatus;
	
	@PostMapping("/create-status")
	public ResponseEntity<String> createStatus(@RequestBody String statusData) {

	        Status status =setSatus.setStatus(statusData);
	        int statusId = statusService.saveStatus(status);
	        return new ResponseEntity<>("status saved- " + statusId, HttpStatus.CREATED);

	    }
}
