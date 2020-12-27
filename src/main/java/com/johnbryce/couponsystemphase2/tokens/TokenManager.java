package com.johnbryce.couponsystemphase2.tokens;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.johnbryce.couponsystemphase2.Exception.NotExistException;
import com.johnbryce.couponsystemphase2.Security.ClientType;
import com.johnbryce.couponsystemphase2.Security.UserData;
import com.johnbryce.couponsystemphase2.Service.AdminService;
import com.johnbryce.couponsystemphase2.Service.ClientService;
import com.johnbryce.couponsystemphase2.Service.CompanyService;
import com.johnbryce.couponsystemphase2.Service.CustomerService;

@Component
public class TokenManager {

	private Map<String, UserData> tokens = new HashMap<>();

	public String createToken(ClientService clientService) {
		UserData userData = new UserData(clientService, System.currentTimeMillis());
		String token = UUID.randomUUID().toString();
		tokens.put(token, userData);
		return token;
	}
	
	public boolean isTokenExist(String token) throws NotExistException {
		if(tokens.containsKey(token)) {
			return true;
		}
		throw new NotExistException("sorry");
	}
	
	public boolean isTokenClientType(String token ,ClientType clientType) throws NotExistException {
		if(isTokenExist(token)) {
			UserData userData= tokens.get(token);
			switch (clientType) {
			case ADMINISTRATOR:
				return userData.getClientService() instanceof AdminService;
			case COMPANY:
				return userData.getClientService() instanceof CompanyService;
			case CUSTOMER:
				return userData.getClientService() instanceof CustomerService;
			}
		}
		return false;
	}

	public UserData get(String token) {
		return tokens.get(token);
	}
	
	public UserData deleteToken(String token) {
		return tokens.remove(token);
	}
	
	public void deleteExpiredToken() {
		tokens.values().removeIf((UserData userData) -> userData.getTimestamp()+30*60*1000<= System.currentTimeMillis());
	}	
}
