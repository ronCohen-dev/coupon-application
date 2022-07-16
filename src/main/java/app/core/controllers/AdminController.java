package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.core.dao.Company;
import app.core.dao.Customer;
import app.core.exception.CouponAppException;
import app.core.service.AdminService;
import app.core.system.Session;
import app.core.system.SessionManagaer;

@RequestMapping("/admin")
@RestController
@CrossOrigin
public class AdminController {

	@Autowired
	SessionManagaer sessionManagaer;

	@Autowired
	private AdminService admin;

	@PostMapping("/company")
	public ResponseEntity<?> addCompany(@RequestBody Company company, @RequestHeader String token)
			throws CouponAppException {
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			admin = (AdminService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.CREATED).body(admin.addNewCompany(company));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@PutMapping("/company")
	public ResponseEntity<?> updateCompany(@RequestBody Company company, @RequestParam int companyId,
			@RequestHeader String token) throws CouponAppException {
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			admin = (AdminService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(admin.updateExistingCompany(company, companyId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@DeleteMapping("/company")
	public ResponseEntity<?> deleteCompany(@RequestParam int companyId, @RequestHeader String token)
			throws CouponAppException {
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			admin = (AdminService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(admin.deleteExistingCompany(companyId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@GetMapping("/company")
	public ResponseEntity<?> getOneCompany(@RequestParam int companyId, @RequestHeader String token)
			throws CouponAppException {
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			admin = (AdminService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(admin.getOneCompanyById(companyId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@GetMapping("/companies")
	public ResponseEntity<?> getCompanies(@RequestHeader String token) throws CouponAppException {
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			admin = (AdminService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(admin.getAllExistsingCompanies());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@PostMapping("/customer")
	public ResponseEntity<?> addCustomer(@RequestBody Customer customer, @RequestHeader String token)
			throws CouponAppException {
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			admin = (AdminService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.CREATED).body(admin.addNewCustomer(customer));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@DeleteMapping("/customer")
	public ResponseEntity<?> deleteCustomer(@RequestParam int customerId, @RequestHeader String token)
			throws CouponAppException {
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			admin = (AdminService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.OK).body(admin.deleteExistingCustomer(customerId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@GetMapping("/customer")
	public ResponseEntity<?> getCustomer(@RequestParam int customerId, @RequestHeader String token)
			throws CouponAppException {
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			admin = (AdminService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(admin.getOneCustomerById(customerId));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@PutMapping("/customer")
	public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @RequestParam int customerId,
			@RequestHeader String token) throws CouponAppException {
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			admin = (AdminService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(admin.updateExistingCustomer(customer, customerId));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@GetMapping("/customers")
	public ResponseEntity<?> getCostomers(@RequestHeader String token) throws CouponAppException {
		try {
			Session session = this.sessionManagaer.getSessionExsists(token);
			admin = (AdminService) session.getInformations("service");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(admin.getAllExistingCustomers());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
	
}
