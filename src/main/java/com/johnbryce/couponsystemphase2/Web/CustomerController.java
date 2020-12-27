package com.johnbryce.couponsystemphase2.Web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johnbryce.couponsystemphase2.Bean.Category;
import com.johnbryce.couponsystemphase2.Bean.Coupon;
import com.johnbryce.couponsystemphase2.Bean.Customer;
import com.johnbryce.couponsystemphase2.Repo.CouponRepository;
import com.johnbryce.couponsystemphase2.Security.ClientType;
import com.johnbryce.couponsystemphase2.Security.LoginManager;
import com.johnbryce.couponsystemphase2.Service.CustomerService;
import com.johnbryce.couponsystemphase2.tokens.TokenManager;

@RestController
@RequestMapping("customer")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class CustomerController {

	@Autowired
	private TokenManager tokenManager;
	@Autowired
	private LoginManager loginManager;
	@Autowired
	private CouponRepository couponRepository;

	
	@PostMapping("login/{email}/{password}")
	public ResponseEntity<?> login(@PathVariable(name = "email") String email, @PathVariable(name = "password") String password){
		try {
			return new ResponseEntity<>(loginManager.login(email, password, ClientType.CUSTOMER), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
		}
	}
	
	@DeleteMapping("logout")
	public ResponseEntity<?> logout(@RequestHeader("authorization") String token){
		tokenManager.deleteToken(token);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("add-purchase-coupon")
	public ResponseEntity<?> addPurchaseCoupon(@RequestBody Coupon coupon, @RequestHeader("authorization") String token){
		try {
			if (tokenManager.isTokenClientType(token, ClientType.CUSTOMER)) {
				CustomerService customerService = (CustomerService)tokenManager.get(token).getClientService();
			customerService.purchastCoupon(coupon);
			return new ResponseEntity<>(HttpStatus.CREATED);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("get-customer-coupon")
	public ResponseEntity<?> getAllCustomerCoupon(@RequestHeader("authorization") String token){
		try {
			if (tokenManager.isTokenClientType(token, ClientType.CUSTOMER)) {
				CustomerService customerService = (CustomerService)tokenManager.get(token).getClientService();
				return new ResponseEntity<>(customerService.getCustomersCoupons(), HttpStatus.OK);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("findCouponsByPrice/{maxPrice}")
	public ResponseEntity<?> findByMaxPrice(@PathVariable(name = "maxPrice") double maxPrice, @RequestHeader("authorization") String token){
		try {
			if (tokenManager.isTokenClientType(token, ClientType.COMPANY)) {
				CustomerService customerService = (CustomerService)tokenManager.get(token).getClientService();
				return new ResponseEntity<>(customerService.getCustoemrCouponsByPrice(maxPrice), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("findCouponsByCategory/{category}")
	public ResponseEntity<?> findByCategory(@PathVariable(name = "category") Category category, @RequestHeader("authorization") String token){
		try {
			if (tokenManager.isTokenClientType(token, ClientType.COMPANY)) {
				CustomerService customerService = (CustomerService)tokenManager.get(token).getClientService();
				return new ResponseEntity<>(customerService.getCustomersCouponsByCategory(category), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("get-one/{id}")
	public ResponseEntity<?> getOneCustomer(@PathVariable(name = "id") int id, @RequestHeader("authorization") String token){
		try {
			if (tokenManager.isTokenClientType(token, ClientType.COMPANY)) {
				CustomerService customerService = (CustomerService)tokenManager.get(token).getClientService();
				return new ResponseEntity<Customer>(customerService.getOneCustomer(id), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("get-details")
	public ResponseEntity<?> getCustomerDetails(@RequestHeader("authorization") String token){
		try {
			if (tokenManager.isTokenClientType(token, ClientType.CUSTOMER)) {
				CustomerService customerService = (CustomerService)tokenManager.get(token).getClientService();
				return new ResponseEntity<>(customerService.getCustomerDetalis(), HttpStatus.OK);
			}
		}catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("get-coupons")
	public ResponseEntity<?> getAllCoupons() {
		return new ResponseEntity<>(couponRepository.findAll(), HttpStatus.OK);
	}

}
