
package com.johnbryce.couponsystemphase2.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.johnbryce.couponsystemphase2.Bean.LoginResponse;
import com.johnbryce.couponsystemphase2.Exception.NotExistException;
import com.johnbryce.couponsystemphase2.Service.AdminService;
import com.johnbryce.couponsystemphase2.Service.ClientService;
import com.johnbryce.couponsystemphase2.Service.CompanyService;
import com.johnbryce.couponsystemphase2.Service.CustomerService;
import com.johnbryce.couponsystemphase2.tokens.TokenManager;

import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Lazy
@Data
@NoArgsConstructor

public class LoginManager {

	@Autowired
	private ApplicationContext ctx;
	@Autowired
	private TokenManager tokenManager;

	public LoginResponse login(String email, String password, ClientType clientType) throws NotExistException {
		switch (clientType) {
		case ADMINISTRATOR:
			ClientService adminService = ctx.getBean(AdminService.class);
			if (adminService.login(email, password)) {
				return new LoginResponse(tokenManager.createToken(adminService));
			}
			break;
		case COMPANY:
			ClientService companyService = ctx.getBean(CompanyService.class);

			if (companyService.login(email, password)) {
				return new LoginResponse(tokenManager.createToken(companyService));
			}

			break;
		case CUSTOMER:
			ClientService customerService = ctx.getBean(CustomerService.class);
			if (customerService.login(email, password)) {
				return new LoginResponse(tokenManager.createToken(customerService));
			}
			break;

		default:
			throw new NotExistException("User");
		}
		return null;
	}
}
