package com.ChatApplication.chat.util;

import java.sql.Timestamp;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ChatApplication.chat.Model.Status;
import com.ChatApplication.chat.Model.Users;
import com.ChatApplication.chat.Repository.IStatusRepo;
import com.ChatApplication.chat.Repository.IUserRepo;





@Component
public class UserSet {
	
	@Autowired
	private IStatusRepo iStatusRepo;
	
	@Autowired
    private IUserRepo iUserRepo;	
	
	 public  JSONObject validateUserRequest(String userData) {
		 
		 JSONObject userJson = new JSONObject(userData);
	        JSONObject errorList = new JSONObject();


	        if(userJson.has("username")) {
	            String username = userJson.getString("username");
	            if(!userJson.has("isUpdate")){
	                List<Users> userList = iUserRepo.findByUsername(username);
	                if(userList.size() > 0) {
	                    errorList.put("username", "This username already exists");
	                    return errorList;
	                }
	            }

	        } else {
	            errorList.put("username", "Missing parameter");
	        }

	        if(!userJson.has("isUpdate")) {
	            if(userJson.has("password") ) {
	                String password = userJson.getString("password");
	                if(!CheckValidation.isValidPassword(password)) {
	                    errorList.put("password", "Please enter valid password eg: Akshay@12390");
	                }
	            } else {
	                errorList.put("password", "Missing parameter");
	            }
	        }


	        if(userJson.has("firstName")) {
	            String firstName = userJson.getString("firstName");
	        } else {
	            errorList.put("firstName", "Missing parameter");
	        }

	        if(userJson.has("email")) {
	            String email = userJson.getString("email");
	            if(!CheckValidation.isValidEmail(email)) {
	                errorList.put("email", "Please enter a valid email");
	            }
	        } else {
	            errorList.put("email", "Missing parameter");
	        }

	        if(userJson.has("phoneNumber")) {
	            String phoneNumber = userJson.getString("phoneNumber");
	            if(!CheckValidation.isValidPhoneNumber(phoneNumber)) {
	                errorList.put("phoneNumber", "Please enter a valid phone number");
	            }
	        } else {
	            errorList.put("phoneNumber", "Missing parameter");
	        }

	        return errorList;

	    }

	   public Users setUser(String userData) {
		   JSONObject jsonObject=new JSONObject(userData);
		   Users user = new Users();
		   
		   user.setUsername(jsonObject.getString("username"));
		   user.setPassword(jsonObject.getString("password"));
		   user.setFirstName(jsonObject.getString("firstName"));
		   user.setLastName(jsonObject.getString("lastName"));
		   user.setAge(jsonObject.getInt("age"));
		   user.setPhoneNumber(jsonObject.getString("phoneNumber"));
		   user.setGender(jsonObject.getString("gender"));
		   user.setEmail(jsonObject.getString("email"));
		   Timestamp createDate=new Timestamp(System.currentTimeMillis());
		   user.setCreatedDate(createDate);
		   Timestamp UpdateDate=new Timestamp(System.currentTimeMillis());
		   user.setUpdatedDate(UpdateDate);
		   
		   int statusId=jsonObject.getInt("stutusId");
		   
		   Status status=iStatusRepo.findById(statusId).get();
		   
		   user.setStatusId(status);
		   
		   return user;

	   }
	   
	   public JSONObject validateLogin(JSONObject requestJson) {

	        JSONObject errorList = new JSONObject();

	        if(!requestJson.has("username")) {
	            errorList.put("username", "missing parameter");
	        }
	        if(!requestJson.has("password")) {
	            errorList.put("password", "missing parameter");
	        }
	        return errorList;
	    }
}
