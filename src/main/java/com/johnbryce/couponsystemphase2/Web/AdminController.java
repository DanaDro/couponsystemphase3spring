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

import com.johnbryce.couponsystemphase2.Bean.Company;
import com.johnbryce.couponsystemphase2.Bean.Customer;
import com.johnbryce.couponsystemphase2.Exception.CanNotChangeExeption;
import com.johnbryce.couponsystemphase2.Exception.NotExistException;
import com.johnbryce.couponsystemphase2.Security.ClientType;
import com.johnbryce.couponsystemphase2.Security.LoginManager;
import com.johnbryce.couponsystemphase2.Service.AdminService;
import com.johnbryce.couponsystemphase2.tokens.TokenManager;

@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private TokenManager tokenManager;
	@Autowired
	private LoginManager loginManager;

	@PostMapping("login/{email}/{password}")
	public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
		try {

			return new ResponseEntity<>(loginManager.login(email, password, ClientType.ADMINISTRATOR),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
	
	@DeleteMapping("logout")
	public ResponseEntity<?> logout(@RequestHeader("authorization") String token){
		tokenManager.deleteToken(token);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("add-company")
	public ResponseEntity<?> addCompany(@RequestBody Company company, @RequestHeader("authorization") String token) {
		try {
			if (tokenManager.isTokenClientType(token, ClientType.ADMINISTRATOR)) {
				return new ResponseEntity<>(adminService.addCompany(company), HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("update-customer")
	public ResponseEntity<?> updateCustomer(@RequestBody Customer customer,
			@RequestHeader("authorization") String token) {
		try {
			if (tokenManager.isTokenClientType(token, ClientType.ADMINISTRATOR)) {
				return new ResponseEntity<>(adminService.updateCustomer(customer), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}

	@PutMapping("update-company")
	public ResponseEntity<?> updateCompany(@RequestBody Company company, @RequestHeader("authorization") String token) {
		try {
			if (tokenManager.isTokenClientType(token, ClientType.ADMINISTRATOR)) {
				return new ResponseEntity<>(adminService.updateCompany(company), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}

	@PostMapping("add-customer")
	public ResponseEntity<?> addCustomer(@RequestBody Customer customer, @RequestHeader("authorization") String token) {
		try {
			if (tokenManager.isTokenClientType(token, ClientType.ADMINISTRATOR)) {
				return new ResponseEntity<>(adminService.addCustomer(customer), HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("delete-company/{id}")
	public ResponseEntity<?> deleteCompany(@PathVariable(name = "id") int id,
			@RequestHeader("authorization") String token) {
		try {
			if (tokenManager.isTokenClientType(token, ClientType.ADMINISTRATOR)) {
				adminService.deleteCompany(id);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (NotExistException | CanNotChangeExeption e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("get-companies")
	public ResponseEntity<?> getAllCompanies(@RequestHeader("authorization") String token) {
		try {
			if (tokenManager.isTokenClientType(token, ClientType.ADMINISTRATOR)) {
				return new ResponseEntity<>(adminService.getCompanies(), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("get-one-company/{id}")
	public ResponseEntity<?> getOneCompany(@PathVariable(name = "id") int id,
			@RequestHeader("authorization") String token) {
		try {
			if (tokenManager.isTokenClientType(token, ClientType.ADMINISTRATOR)) {
				return new ResponseEntity<Company>(adminService.getOneCompany(id), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}

	@DeleteMapping("delete-customer/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable(name = "id") int id,
			@RequestHeader("authorization") String token) {
		try {
			if (tokenManager.isTokenClientType(token, ClientType.ADMINISTRATOR)) {
				adminService.deleteCustomer(id);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (NotExistException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("get-customers")
	public ResponseEntity<?> getAllCustomers(@RequestHeader("authorization") String token) {
		try {
			if (tokenManager.isTokenClientType(token, ClientType.ADMINISTRATOR)) {
				return new ResponseEntity<>(adminService.getCustomers(), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping("get-one-customer/{id}")
	public ResponseEntity<?> getOneCustomer(@PathVariable(name = "id") int id,
			@RequestHeader("authorization") String token) {
		try {
			if (tokenManager.isTokenClientType(token, ClientType.ADMINISTRATOR)) {
				return new ResponseEntity<Customer>(adminService.getOneCustomer(id), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("This client doesn't have permissaion", HttpStatus.UNAUTHORIZED);
	}

}
