package app.core.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import app.core.dao.Coupon;
import app.core.dao.Customer;
import app.core.dao.Coupon.Category;
import app.core.exception.CouponAppException;
import app.core.repository.CouponRepository;
import app.core.repository.CustomerRepository;

/**
 * @see all methods in the class is a @Service and @Transactional
 */
@Service
@Transactional
public class CustomerService implements CustomerInterfaceService {

	/**
	 * @see This statement on the parameters are to get referenc to the JPA
	 *      interface that have the imformation to work with the date bace
	 * @param customer pointer is the param that point the current customer
	 * @param min      help to creat a between query and this is cant be replace
	 */
	private static Integer customerPointer;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * This method is used to check the email that the user input
	 * 
	 * @param 'email' This is the first parameter to login method , and this param
	 *                tell the email for user required by the client * @param
	 *                'password' This is the second parameter to login method , and
	 *                this param tell the password for user required by the client
	 * @see the param customer pointer is initialized and point to the currect login
	 *      customer
	 * 
	 */
	@Override
	public boolean logIn(String email, String password) throws CouponAppException {
		try {
			customerPointer = customerRepository.getCustomerByEmailAndPassword(email, password).getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.UNAUTHORIZED, "you have entered wrong email or password",
					e.getCause());
		}
		return true;
	}

	/**
	 * {@code} method get the customer that do login to by coupon, you cant buy more
	 * than one of specepic coupon method to not allowed to buy any coupon than
	 * cuurent time after end date and before start date method check before buy
	 * coupon if amount big than zero
	 * 
	 * @throws the method throws exception if the method field because input is not
	 *             valid
	 */
	@Override
	public Coupon customerCouponPurchase(Integer couponId) throws CouponAppException {
		try {
			Customer customer = customerRepository.getOne(customerPointer);
			LocalDate currentTime = LocalDate.now();
			List<Coupon> coupons = getAllCostomerCoupons();
			Optional<Coupon> op = couponRepository.findById(couponId);
			if (op.isEmpty()) {
				throw new CouponAppException(HttpStatus.NO_CONTENT, "the coupon must not be null");
			}
			Coupon thisCoupon = op.get();
			for (Coupon coupons2 : coupons) {
				if (coupons2.getCouponId().equals(couponId)) {
					throw new CouponAppException(HttpStatus.FOUND,
							"you already purchased this coupon, can't buy another one");
				}
			}

			if (thisCoupon.getAmount() <= 0) {
				throw new CouponAppException(HttpStatus.NOT_FOUND, "no coupons exists sold out");
			}

			if (currentTime.isAfter(thisCoupon.getEndDate())) {
				throw new CouponAppException(HttpStatus.LOCKED, "the coupon expierd");
			}
			thisCoupon.setAmount(thisCoupon.getAmount() - 1);
			customer.addCoupons(thisCoupon);
			System.out.println(thisCoupon);
			return thisCoupon;

		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.UNAUTHORIZED, "plz logged in first", e.getCause());
		}

	}

	/**
	 * @return this method return list<Coupon> that exists in the data base that
	 *         customer purchase
	 * @throws the required from the method field , connection to the DB this the
	 *             repository field
	 */

	@Override
	public List<Coupon> getAllCostomerCoupons() throws CouponAppException {
		try {
			Optional<Customer> optional = customerRepository.findById(customerPointer);
			if (optional.isEmpty()) {
				throw new CouponAppException(HttpStatus.UNAUTHORIZED, "plz login id cant be null");
			}
			Customer customer = optional.get();
			List<Coupon> customerCoupons = customer.getCoupons();
			return customerCoupons;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.NOT_FOUND, "customer dosen't have any coupons", e.getCause());
		}
	}

	/**
	 * @return this method return list<Coupon> that exists in the data base that
	 *         customer purchase By specific category
	 * @param this is the specific category required from the cliet
	 * @throws the required from the method field , connection to the DB this the
	 *             repository field
	 */

	@Override
	public List<Coupon> getAllCustomerCouponsByCategory(Category category) throws CouponAppException {
		try {
			List<Coupon> customerCouponsByCategory = couponRepository.findByCustomerIdAndCategoryId(customerPointer,
					category);
			return customerCouponsByCategory;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.NOT_FOUND,
					"customer dont have any coupon with this category at all ", e.getCause());
		}

	}

	/**
	 * @return this method return list<Coupon> that exists in the data base that
	 *         customer purchase By specific category
	 * @param This parameter determines the maximum coupon return price
	 * @throws the required from the method field , connection to the DB this the
	 *             repository field
	 */

	@Override
	public List<Coupon> getAllCustomerCouponsByMaxPrice(double maxPrice) throws CouponAppException {
		try {
			List<Coupon> customerCouponsByMaxPrice = couponRepository.findByCustomerIdAndPriceLessThan(customerPointer,
					maxPrice);
			return customerCouponsByMaxPrice;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.NOT_FOUND, "customer dont have any coupon with this price",
					e.getCause());
		}
	}

	/**
	 * @return the method returns the specepic customer details that do login
	 */

	@Override
	public Customer getCustomerDetails() throws CouponAppException {
		try {
			Customer customer = null;
			Optional<Customer> optional = customerRepository.findById(customerPointer);
			if (optional.isPresent()) {
				customer = optional.get();
			}
			return customer;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.UNAUTHORIZED, "customer dont have this customer at all ",
					e.getCause());
		}
	}
	
	@Override
	public List<Coupon> getAllCoupons() throws CouponAppException {
		try {
			return couponRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.UNAUTHORIZED, "method filed cant get all coupons", e.getCause());
		}
	}

}
