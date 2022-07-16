package app.core.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import app.core.dao.Company;
import app.core.dao.Coupon;
import app.core.dao.Customer;
import app.core.exception.CouponAppException;
import app.core.repository.CompanyRepository;
import app.core.repository.CouponRepository;
import app.core.repository.CustomerRepository;

/**
 * @see all methods in the class is a @Service and @Transactional
 */
@Service
@Transactional
public class AdminService implements AdminInterfaceService {

	/**
	 * @see This statement on the parameters are to get referenc to the JPA
	 *      interface that have the imformation to work with the date bace
	 * @param admin password and email is a hard coded , they cant be replace at all
	 */
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private CouponRepository couponRepository;

	private static final String adminPassword = "admin";

	private static final String adminEmail = "admin@admin.com";

	/**
	 * This method is used to check the email that the user input
	 * 
	 * @param 'Password' This is the first parameter to login method , and this
	 *                   param tell the email for user required by the client
	 * @param 'email'    This is the second parameter to login method , and this
	 *                   param tell the email for user required by the client
	 * @return the method return http status to the client for his required
	 */
	@Override
	public boolean logIn(String email, String Password) throws CouponAppException {
		try {
			if (adminEmail.equals(email) && adminPassword.equals(Password)) {
				return true;

			} else {
				throw new CouponAppException(HttpStatus.UNAUTHORIZED, "plz enter admin user..");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.UNAUTHORIZED, "you have entere wrong credentials..", e.getCause());
		}

	}

	/**
	 * Creates an company {@value company} company that takes from the 'admin'
	 * client that add company to the data base
	 * 
	 * @throws the method throws exception if the method field because input is not
	 *             valid
	 */
	@Override
	public Company addNewCompany(Company company) throws CouponAppException {
		try {
			if (!companyRepository.existsByName(company.getName())
					&& !companyRepository.existsByEmail(company.getEmail())) {
				companyRepository.save(company);
				return company;
			} else {
				throw new CouponAppException(HttpStatus.FOUND,
						"this company with the same name or email already exists");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS, "this company could not bedd added",
					e.getCause());
		}
	}

	/**
	 * * {@value company} company that takes from the data base
	 * 
	 * @param the copanyId param this is to make sure that the company exists
	 *            updating existing company could not update existing company name
	 *            could not update email if there is already exists one in the data
	 *            base this method check if company exists if so it will make option
	 *            to update email or password however if email already exists it
	 *            will throw exception
	 */
	@Override
	public Company updateExistingCompany(Company company, int companyId) throws CouponAppException {
		try {
			if (companyRepository.existsById(companyId)) {
				Company toUpdateCompany = companyRepository.findById(companyId).orElse(null);
				if (!companyRepository.existsByEmail(companyRepository.findAllByEmail(company.getEmail()))) {
					toUpdateCompany.setPassword(company.getPassword());
					toUpdateCompany.setEmail(company.getEmail());
					return companyRepository.save(toUpdateCompany);

				} else
					throw new CouponAppException(HttpStatus.FOUND,
							"could not change email it's already exists in other company try something else");
			} else
				throw new CouponAppException(HttpStatus.NOT_FOUND, "The company dont exists at all");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.BAD_REQUEST, "could not update company: ", e.getCause());
		}

	}

	/**
	 * @param the copanyId param this is to make sure that the company exists the
	 *            method delete the company are exists and required to delete from
	 *            'admin' * @throws the method throws exception if the method field
	 *            because input is not valid
	 */
	@Override
	public Company deleteExistingCompany(int companyId) throws CouponAppException {
		try {
			if (companyRepository.existsById(companyId)) {
				Optional<Company> optional = companyRepository.findById(companyId);
				if (optional.isEmpty()) {
					throw new CouponAppException(HttpStatus.NOT_ACCEPTABLE, "the company id cany be null pointer");
				}
				Company company = optional.get();
				companyRepository.deleteById(companyId);
				System.out.println("company deleted with id : " + companyId);
				return company;
			} else {
				throw new CouponAppException(HttpStatus.NOT_FOUND, "this company does not exists");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.NOT_ACCEPTABLE, "could not delete Company", e.getCause());
		}
	}

	/**
	 * @return this method return list<company> that exists in the data base else,
	 * @throws the required from the method field , connection to the DB this the
	 *             repository field
	 */
	@Override
	public List<Company> getAllExistsingCompanies() throws CouponAppException {
		try {
			return companyRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.NOT_ACCEPTABLE, "the metode field", e.getCause());
		}

	}

	/**
	 * @param the copanyId param this is to make sure that the company exists and to
	 *            get this specepic company by id
	 * @return the method returns the specepic requird company
	 */
	@Override
	public Company getOneCompanyById(Integer companyId) throws CouponAppException {
		try {
			if (companyRepository.existsById(companyId)) {
				Optional<Company> optional = companyRepository.findById(companyId);
				Company company = new Company();
				if (optional.isPresent()) {
					company = optional.get();
				}
				return company;
			} else
				throw new CouponAppException(HttpStatus.NOT_FOUND, "there is no such company whis this id");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.UNAUTHORIZED, "could not make correct request  for this company",
					e.getCause());
		}
	}

	/**
	 * Creates an customer {@value} customer that takes from the 'admin' client that
	 * add company to the data base
	 * 
	 * @throws the method throws exception if the method field because this email
	 *             exsists
	 */
	@Override
	public Customer addNewCustomer(Customer customer) throws CouponAppException {
		try {
			if (!customerRepository.existsByEmail(customer.getEmail())) {
				customerRepository.save(customer);
				return customer;
			} else
				throw new CouponAppException(HttpStatus.NOT_FOUND,
						"this customer already exists with this email please enter other email adress");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.UNAUTHORIZED, "could not add customer", e.getCause());
		}
	}

	/**
	 * {@value customer} customer that takes from the data base
	 * 
	 * @param the copanyId param this is to make sure that the company exists there
	 *            is no other customer with the same email address all other options
	 *            can be updated
	 */
	@Override
	public Customer updateExistingCustomer(Customer customer, int customerId) throws CouponAppException {
		try {
			if (customerRepository.getOne(customerId) != null) {
				Customer toUpdateThisCustomer = customerRepository.findById(customerId).orElse(null);
				if (!customerRepository.existsByEmail(customerRepository.existsAllByEmail(customer.getEmail()))) {
					toUpdateThisCustomer.setEmail(customer.getEmail());
					toUpdateThisCustomer.setFirstName(customer.getFirstName());
					toUpdateThisCustomer.setLastName(customer.getLastName());
					toUpdateThisCustomer.setPassword(customer.getPassword());
					return customerRepository.save(toUpdateThisCustomer);

				} else
					throw new CouponAppException(HttpStatus.FOUND,
							"could not update this customer the email already eists");
			} else
				throw new CouponAppException(HttpStatus.NOT_FOUND, "this customer does not exists");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.UNAUTHORIZED, "could not update customer", e.getCause());
		}

	}

	/**
	 * @param the customerId param this is to make sure that the customer exists the
	 *            method delete the customer are exists and required to delete from
	 *            'admin' * @throws the method throws exception if the method field
	 *            because input is not valid
	 * @return
	 */
	@Override
	public Customer deleteExistingCustomer(Integer customerId) throws CouponAppException {
		try {
			if (customerRepository.existsById(customerId)) {
				Optional<Customer> optional = customerRepository.findById(customerId);
				if (optional.isEmpty()) {
					throw new CouponAppException(HttpStatus.NOT_ACCEPTABLE, "the customer id cany be null pointer");
				}
				Customer customer = optional.get();
				customerRepository.deleteById(customerId);
				System.out.println("customer deleted : " + customer);
				return customer;
			} else
				throw new CouponAppException(HttpStatus.NOT_FOUND, "this customer does not exists");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.UNAUTHORIZED, "could not delete customer", e.getCause());
		}
	}

	/**
	 * @return this method return list<customer> that exists in the data base else,
	 * @throws the required from the method field , connection to the DB this the
	 *             repository field
	 */
	@Override
	public List<Customer> getAllExistingCustomers() throws CouponAppException {
		try {
			return customerRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.UNAUTHORIZED, "method filed cant get all customers", e.getCause());
		}

	}

	/**
	 * @param the customerId param this is to make sure that the company exists and
	 *            to get this specepic customer by id
	 * @return the method returns the specepic requird customer
	 */
	@Override
	public Customer getOneCustomerById(Integer customerId) throws CouponAppException {
		try {
			if (customerRepository.existsById(customerId)) {
				Optional<Customer> optional = customerRepository.findById(customerId);
				Customer customer = new Customer();
				if (optional.isPresent()) {
					customer = optional.get();
				}
				return customer;
			} else
				throw new CouponAppException(HttpStatus.NOT_FOUND, "there is no such customer");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.UNAUTHORIZED, "could not retrieve customer", e.getCause());
		}
	}

}
