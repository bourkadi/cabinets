package com.bourgadix.cryptoUtils;


import org.springframework.security.crypto.bcrypt.BCrypt;

public class BcryptExample {

	 
	   public static void main(String[] args) {
	        String  originalPassword = "reda";
	        String generatedSecuredPasswordHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
	        System.out.println(generatedSecuredPasswordHash);
	        boolean matched = BCrypt.checkpw(originalPassword, generatedSecuredPasswordHash);
	        System.out.println(matched);
	}
}
