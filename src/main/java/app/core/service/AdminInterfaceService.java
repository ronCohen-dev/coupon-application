package app.core.service;

import java.util.List;

import app.core.dao.Company;
import app.core.dao.Coupon;
import app.core.dao.Customer;
import app.core.exception.CouponAppException;

public interface AdminInterfaceService extends Clients {

	boolean logIn(String user, String email) throws CouponAppException;

	Company addNewCompany(Company company) throws CouponAppException;

	Company deleteExistingCompany(int companyId) throws CouponAppException;

	Company updateExistingCompany(Company company, int companyId) throws CouponAppException;

	Company getOneCompanyById(Integer companyId) throws CouponAppException;

	List<Company> getAllExistsingCompanies() throws CouponAppException;

	Customer addNewCustomer(Customer customer) throws CouponAppException;

	Customer deleteExistingCustomer(Integer customerId) throws CouponAppException;

	Customer getOneCustomerById(Integer customerId) throws CouponAppException;

	Customer updateExistingCustomer(Customer customer, int customerId) throws CouponAppException;

	List<Customer> getAllExistingCustomers() throws CouponAppException;
	
	

}
