package app.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.dao.Customer;
import app.core.exception.CouponAppException;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	boolean existsByEmail(String email) throws CouponAppException;

	String existsAllByEmail(String email) throws CouponAppException;

	Customer getCustomerByEmailAndPassword(String email, String password) throws CouponAppException;
}
