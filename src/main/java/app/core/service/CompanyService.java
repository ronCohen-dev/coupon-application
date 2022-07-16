package app.core.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import app.core.dao.Company;
import app.core.dao.Coupon;
import app.core.dao.Coupon.Category;
import app.core.exception.CouponAppException;
import app.core.repository.CompanyRepository;
import app.core.repository.CouponRepository;

/**
 * @see all methods in the class is a @Service and @Transactional
 */
@Service
@Transactional
public class CompanyService implements CompanyInterfaceService {

	/**
	 * @see This statement on the parameters are to get referenc to the JPA
	 *      interface that have the imformation to work with the date bace
	 * @param company id is the param that point the current company
	 */
	private static Integer companyId;

	@Autowired
	private CouponRepository couponRepositpry;

	@Autowired
	private CompanyRepository companyRepository;

	/**
	 * This method is used to check the email that the user input
	 * 
	 * @param 'email' This is the first parameter to login method , and this param
	 *                tell the email for user required by the client * @param
	 *                'password' This is the second parameter to login method , and
	 *                this param tell the password for user required by the client
	 * @see the param company id is initialized and point to the currect login
	 *      company
	 * 
	 */
	@Override
	public boolean logIn(String companyEmail, String companyPassword) throws CouponAppException {
		try {
			companyId = companyRepository.getCompanyByEmailAndPassword(companyEmail, companyPassword).getId();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.UNAUTHORIZED, "you have entered wrong name or email", e.getCause());
		}
		return true;

	}

	/**
	 * @return the methode return the coupon you wont to create {@code}method cant
	 *         add coupon this same coupon title exists
	 * @throws the method throws exception if the method field because input is not
	 *             valid or null
	 */
	@Override
	public Coupon addCouponToCompany(Coupon coupon) throws CouponAppException {
		try {
			Company company = companyRepository.getOne(companyId);
			LocalDate currentTime = LocalDate.now();
			List<Coupon> couponList = company.getCoupons();
			for (Coupon thisCoupon : couponList) {
				if (thisCoupon.getTitle().equals(coupon.getTitle())) {
					throw new CouponAppException(HttpStatus.FOUND, "there is already coupon with this name ");
				}

			}
			if (!(coupon.getStartDate().isBefore(currentTime) && coupon.getEndDate().isBefore(currentTime))) {
				company.addCoupon(coupon);
				couponRepositpry.save(coupon);
				return coupon;
			} else {
				throw new CouponAppException(HttpStatus.NOT_ACCEPTABLE, "the dates input is not valid");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.UNAUTHORIZED, "could not add coupon to this company", e.getCause());
		}

	}

	/**
	 * * {@value coupon} coupon that takes from the data base
	 * 
	 * @param the couponId param this is to make sure that the coupon exists
	 *            updating existing coupon could not update existing coupon name
	 *            could not update email if there is already exists one in the data
	 *            base this method check if company exists if so it will make option
	 *            to update email or password however if email already exists it
	 *            will throw exception
	 */
	@Override
	public Coupon updateCurrentCoupon(Coupon newCoupon, Integer couponId) throws CouponAppException {
		try {
			Optional<Coupon> optional = couponRepositpry.findById(couponId);
			if (optional.isEmpty()) {
				throw new CouponAppException(HttpStatus.NOT_FOUND, "coupon id must nit be null");
			}
			Coupon currentCoupon = optional.get();
			currentCoupon.setAmount(newCoupon.getAmount());
			currentCoupon.setDescription(newCoupon.getDescription());
			currentCoupon.setEndDate(newCoupon.getEndDate());
			currentCoupon.setStartDate(newCoupon.getStartDate());
			currentCoupon.setImage(newCoupon.getImage());
			currentCoupon.setPrice(newCoupon.getPrice());
			currentCoupon.setTitle(newCoupon.getTitle());
			System.out.println(currentCoupon);
			return couponRepositpry.save(currentCoupon);
		} catch (Exception e) {
			throw new CouponAppException(HttpStatus.UNAUTHORIZED, "could not update this Coupon ", e.getCause());
		}
	}

	/**
	 * @param the couponId param this is to make sure that the couopn exists the
	 *            method delete the company are exists and required to delete
	 * @throws the method throws exception if the method field because input is not
	 *             valid
	 */
	@Override
	public Coupon deleteOneCouponOfCompany(Integer couponId) throws CouponAppException {
		try {
			if (companyRepository.existsById(companyId) && couponRepositpry.existsById(couponId)) {
				Optional<Coupon> optional = couponRepositpry.findById(couponId);
				if (optional.isEmpty()) {
					throw new CouponAppException(HttpStatus.NOT_FOUND, "the coupopn id must not be null");
				}
				Coupon coupon = optional.get();
				couponRepositpry.deleteById(couponId);
				System.out.println("coupon from company deleted : " + couponId);
				return coupon;

			}

		} catch (Exception e) {
			throw new CouponAppException(HttpStatus.NOT_FOUND, "comapny not found", e.getCause());
		}
		throw new CouponAppException(HttpStatus.UNAUTHORIZED, "could not delete Coupon" + couponId);

	}

	/**
	 * @return this method return list<Coupon> that exists in the data base of
	 *         specific company else,
	 * @throws the required from the method field , connection to the DB this the
	 *             repository field
	 */
	@Override
	public List<Coupon> getAllCouponsOfOneCompany() throws CouponAppException {
		try {
			List<Coupon> getAllCoupons = couponRepositpry.findByCompanyId(companyId);
			return getAllCoupons;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.UNAUTHORIZED, "could not get all coupons for this company",
					e.getCause());
		}

	}

	/**
	 * @return this method return list<Coupon> that exists in the data base of
	 *         specific company and by category else,
	 * @throws the required from the method field , connection to the DB this the
	 *             repository field
	 */
	@Override
	public List<Coupon> getCompanyCouponsByCategory(Category category) throws CouponAppException {
		try {
			List<Coupon> allCouponsByCaategory = couponRepositpry.findByCompanyIdAndCategoryId(companyId, category);
			return allCouponsByCaategory;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.UNAUTHORIZED, "could not get any category at all", e.getCause());
		}

	}

	/**
	 * @return this method return list<Coupon> that exists in the data base of
	 *         company By specific category
	 * @param This parameter determines the maximum coupon return price
	 * @throws the required from the method field , connection to the DB this the
	 *             repository field
	 */
	@Override
	public List<Coupon> getAllCompanyCouponsByMaxPrice(double maxPrice) throws CouponAppException {
		try {
			List<Coupon> getAllCoupons = couponRepositpry.findByCompanyIdAndPriceLessThan(companyId, maxPrice);
			return getAllCoupons;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.UNAUTHORIZED, "could not retrieve max price", e.getCause());
		}

	}

	/**
	 * @return the method returns the specepic company details that do login
	 */

	@Override
	public Company getCompanyDetails() throws CouponAppException {
		try {
			if (companyRepository.existsById(companyId)) {
				Optional<Company> optional = companyRepository.findById(companyId);
				Company company = new Company();
				if (optional.isPresent()) {
					company = optional.get();
				}
				return company;
			} else
				throw new CouponAppException(HttpStatus.NOT_FOUND, "the id cant be null");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponAppException(HttpStatus.UNAUTHORIZED, "could not get any company details at all",
					e.getCause());
		}

	}

}
