package com.johnbryce.couponsystemphase2.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnbryce.couponsystemphase2.Bean.Category;
import com.johnbryce.couponsystemphase2.Bean.Company;
import com.johnbryce.couponsystemphase2.Bean.Coupon;
import com.johnbryce.couponsystemphase2.Exception.NotExistException;
import com.johnbryce.couponsystemphase2.Security.ClientType;
import com.johnbryce.couponsystemphase2.Security.LoginManager;
import com.johnbryce.couponsystemphase2.Service.CompanyService;
import com.johnbryce.couponsystemphase2.tokens.TokenManager;

@RestController
@RequestMapping("company")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class CompanyController {

	@Autowired
	private TokenManager tokenManager;
	@Autowired
	private LoginManager loginManager;

	@PostMapping("login/{email}/{password}")
	public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
		try {
			return new ResponseEntity<>(loginManager.login(email, password, ClientType.COMPANY), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
	
	@DeleteMapping("logout")
	public ResponseEntity<?> logout(@RequestHeader("authorization") String token){
		tokenManager.deleteToken(token);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("add-coupon")
	public ResponseEntity<?> addCoupon(@RequestBody Coupon coupon, @RequestHeader("authorization") String token) {
		try {
			if (tokenManager.isTokenClientType(token, ClientType.COMPANY)) {
				CompanyService companyService = (CompanyService)tokenManager.get(token).getClientService();
				return new ResponseEntity<>(companyService.addCoupon(coupon), HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("update-coupon")
	public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon, @RequestHeader("authorization") String token) {
		try {
			if (tokenManager.isTokenClientType(token, ClientType.COMPANY)) {
				CompanyService companyService = (CompanyService)tokenManager.get(token).getClientService();
				return new ResponseEntity<>(companyService.updateCoupon(coupon), HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("delete-coupon/{id}")
	public ResponseEntity<?> deleteCoupon(@PathVariable(name = "id") int id,
			@RequestHeader("authorization") String token) {
		try {
			if (tokenManager.isTokenClientType(token, ClientType.COMPANY)) {
				CompanyService companyService = (CompanyService)tokenManager.get(token).getClientService();
				companyService.deleteCoupon(id);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (NotExistException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("get-company-coupon")
	public ResponseEntity<?> getAllCompanyCoupon(@RequestHeader("authorization") String token) {
		try {
			if (tokenManager.isTokenClientType(token, ClientType.COMPANY)) {
				CompanyService companyService = (CompanyService)tokenManager.get(token).getClientService();
				return new ResponseEntity<>(companyService.getCompanyCoupons(), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("findCouponsByPrice/{price}")
	public ResponseEntity<?> findByMaxPrice(@PathVariable(name = "price") double maxPrice,
			@RequestHeader("authorization") String token) {
		try {
			if (tokenManager.isTokenClientType(token, ClientType.COMPANY)) {
				CompanyService companyService = (CompanyService)tokenManager.get(token).getClientService();
				return new ResponseEntity<>(companyService.getCompanyCouponsByPrice(maxPrice), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("findCouponsByCategory/{category}")
	public ResponseEntity<?> findByCategory(@PathVariable(name = "category") Category category,
			@RequestHeader("authorization") String token) {
		try {
			if (tokenManager.isTokenClientType(token, ClientType.COMPANY)) {
				CompanyService companyService = (CompanyService)tokenManager.get(token).getClientService();
				return new ResponseEntity<>(companyService.getCompanyCouponsByCategory(category), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("get-one/{id}")
	public ResponseEntity<?> getOneCompany(@PathVariable(name = "id") int id,
			@RequestHeader("authorization") String token) {
		try {
			if (tokenManager.isTokenClientType(token, ClientType.COMPANY)) {
				CompanyService companyService = (CompanyService)tokenManager.get(token).getClientService();
				return new ResponseEntity<Company>(companyService.getOneCompany(id), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("get-details")
	public ResponseEntity<?> getCompanyDetails(@RequestHeader("authorization") String token){
		try {
			if (tokenManager.isTokenClientType(token, ClientType.COMPANY)) {
				CompanyService companyService = (CompanyService)tokenManager.get(token).getClientService();
				return new ResponseEntity<>(companyService.getCompanyDetails(), HttpStatus.OK);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}

}
