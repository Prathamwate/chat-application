package com.ChatApplication.chat.Service;

import java.sql.Timestamp;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ChatApplication.chat.Model.Users;
import com.ChatApplication.chat.Repository.IUserRepo;

@Service
public class UserService {
	
	@Autowired
	IUserRepo iUserRepo;

	public int addUser(Users user) {
	 Users userId =iUserRepo.save(user);
	 return userId.getUserId();
	}

	public JSONArray getUser(String userid) {
		JSONArray jsonArray=new JSONArray();
		if(null!=userid) {
			List<Users> usersList=iUserRepo.getUserByUserId(Integer.valueOf(userid));
			for(Users list:usersList) {
				JSONObject jsonObject=createResponse(list);
				jsonArray.put(jsonObject);
			}
		}
		else {
			 List<Users> usersList = iUserRepo.getAllUsers();
	            for (Users user:usersList) {
	                JSONObject userObj = createResponse(user);
	                jsonArray.put(userObj);
		}
		}
		return jsonArray;
	}
	
	 private JSONObject createResponse(Users user) {
	        JSONObject jsonObj = new JSONObject();

	        jsonObj.put("userId", user.getUserId());
	        jsonObj.put("username", user.getUsername());
	        jsonObj.put("firstName", user.getFirstName());
	        jsonObj.put("lastName", user.getLastName());
	        jsonObj.put("age", user.getAge());
	        jsonObj.put("email", user.getEmail());
	        jsonObj.put("phoneNumber", user.getPhoneNumber());
	        jsonObj.put("gender", user.getGender());
	        jsonObj.put("createdDate", user.getCreatedDate());

	        return jsonObj;
	    }
	 
	 public JSONObject login (String username, String password) {
	        JSONObject response = new JSONObject();
	        List<Users> user = iUserRepo.findByUsername(username);
	        if(user.isEmpty()) {
	            response.put("errorMessage", "username doesn't exist");
	        } else {
	            Users userObj = user.get(0);
	            if(password.equals(userObj.getPassword())) {
	                response = createResponse(userObj);
	            }else {
	                response.put("errorMessage" , "password is not valid");
	            }
	        }
	        return response;
	    }

	public JSONObject updateUser(Users user, String userId) {
		  List<Users> usersList = iUserRepo.getUserByUserId(Integer.valueOf(userId));
	        JSONObject obj = new JSONObject();
	        if(!usersList.isEmpty()) {
	            Users oldUser = usersList.get(0);
	            user.setUserId(oldUser.getUserId());
	            user.setCreatedDate(oldUser.getCreatedDate());
	            user.setPassword(oldUser.getPassword());
	            Timestamp updatedTime = new Timestamp(System.currentTimeMillis());
	            user.setUpdatedDate(updatedTime);
	            iUserRepo.save(user);
	        } else {
	            obj.put("errorMessage" , "User doesn't exist");
	        }
	        return obj;
	    }

	public void deleteUserByUserId(int userId) {
		iUserRepo.deleteUserByUserId(userId);
		
	}
	

}
