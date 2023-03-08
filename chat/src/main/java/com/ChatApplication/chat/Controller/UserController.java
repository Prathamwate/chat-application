package com.ChatApplication.chat.Controller;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ChatApplication.chat.Model.Users;
import com.ChatApplication.chat.Service.UserService;
import com.ChatApplication.chat.util.UserSet;

import jakarta.annotation.Nullable;


@RestController
@RequestMapping("api/v1/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserSet userSet;
	
   @PostMapping("/create-user")
   public ResponseEntity<String> createUser(@RequestBody String userData) {
	   
	   JSONObject isValid = userSet.validateUserRequest(userData);

       Users user = null;
       int userId;
       if(isValid.isEmpty()) {
           user =userSet.setUser(userData);
           userId=userService.addUser(user);
       } else {
           return new ResponseEntity<String>(isValid.toString(), HttpStatus.BAD_REQUEST);
       }
       
       return new ResponseEntity<>("Saved User"+userId, HttpStatus.CREATED);

   }
   
   @GetMapping(value = "/get-user")
   public ResponseEntity<String> getUser(@Nullable @RequestBody String userid){
	   JSONArray jsonArray=userService.getUser(userid);
	   return new ResponseEntity<String>(jsonArray.toString(),HttpStatus.OK);
   }
   
   @PostMapping(value = "/login")
   public ResponseEntity<String> login(@RequestBody String requestData) {
       JSONObject requestJson = new JSONObject(requestData);

       JSONObject isValidLogin = userSet.validateLogin(requestJson);

       if (isValidLogin.isEmpty()) {
           String username = requestJson.getString("username");
           String password = requestJson.getString("password");
           JSONObject responseObj = userService.login(username, password);
           if(responseObj.has("errorMessage")) {
               return new ResponseEntity<String>(responseObj.toString(), HttpStatus.BAD_REQUEST);
           }else {
               return new ResponseEntity<String>(responseObj.toString(), HttpStatus.OK);
           }
       } else {
           return new ResponseEntity<String>(isValidLogin.toString(), HttpStatus.BAD_REQUEST);
       }
   }
   
   @PutMapping(value = "/update-user/{userId}")
   public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody String requestData) {
       JSONObject isRequestValid =userSet.validateUserRequest(requestData);
       Users user = null;

       if(isRequestValid.isEmpty()) {
           user = userSet.setUser(requestData);
           JSONObject responseObj = userService.updateUser(user, userId);
           if(responseObj.has("errorMessage")) {
               return new ResponseEntity<String>(responseObj.toString(), HttpStatus.BAD_REQUEST);
           }
       } else {
           return new ResponseEntity<String>(isRequestValid.toString(), HttpStatus.BAD_REQUEST);
       }
       return new ResponseEntity<String>("user updated", HttpStatus.OK);
   }
   
   @DeleteMapping(value = "/delete-user/{userId}")
   public ResponseEntity<String> deleteUser(@PathVariable int userId){

       userService.deleteUserByUserId(userId);
       return new ResponseEntity<>("Deleted", HttpStatus.NO_CONTENT);
   }
}
