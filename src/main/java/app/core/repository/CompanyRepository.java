package app.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.dao.Company;
import app.core.exception.CouponAppException;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

	boolean existsByNameAndEmail(String name, String email) throws CouponAppException;

	boolean existsByName(String name) throws CouponAppException;

	boolean existsByEmail(String email) throws CouponAppException;

	boolean existsByPassword(String password) throws CouponAppException;

	String findAllByEmail(String email) throws CouponAppException;

	Company getCompanyByEmailAndPassword(String email, String password) throws CouponAppException;

	boolean existsById(Integer companyId);

}
