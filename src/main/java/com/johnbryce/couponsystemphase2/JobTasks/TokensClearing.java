package com.johnbryce.couponsystemphase2.JobTasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.johnbryce.couponsystemphase2.tokens.TokenManager;

@Component
public class TokensClearing {

	@Autowired
	private TokenManager tokensManager;
	
	@Scheduled(fixedRate = (1000 * 60 * 30))
	public void deleteExpiredTokenOver30Min() {
		tokensManager.deleteExpiredToken();
	}
}
