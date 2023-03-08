package com.ChatApplication.chat.util;

import java.util.regex.*;

public class CheckValidation {

	 public static boolean isValidPassword(String password) {
	        String regex = "^(?=.*[0-9])"
	                + "(?=.*[a-z])(?=.*[A-Z])"
	                + "(?=.*[@#$%^&+=])"
	                + "(?=\\S+$).{8,20}$";

	        Pattern p = Pattern.compile(regex);
	        Matcher m = p.matcher(password);
	        return m.matches();
	    }

	    public static boolean isValidEmail(String email) {
	        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
	                "[a-zA-Z0-9_+&*-]+)*@" +
	                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
	                "A-Z]{2,7}$";

	        Pattern pat = Pattern.compile(emailRegex);
	        return pat.matcher(email).matches();
	    }


	    public static boolean isValidPhoneNumber(String phoneNumber) {
	    	 if(phoneNumber.length()!=10) { return false; }
			 
			 else {
				 for(char c:phoneNumber.toCharArray()) {
					 if(!(c>=48 && c<=57))
						 return false;
				 }
				 return true;
			 }
	    }
}
