package app.core.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Table(name = "coupons")
@Entity
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Integer id;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private LocalDate startDate;
	@Column(nullable = false)
	private LocalDate endDate;
	@Column(nullable = false)
	private Integer amount;
	@Column(nullable = false)
	private Double price;
	@Column(nullable = false)
	private String image;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Category categoryId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.MERGE,
			CascadeType.PERSIST })
	@JoinColumn(name = "companyId")
	@JsonIgnore
	private Company company;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JoinTable(name = "customer_vs_coupon", joinColumns = { @JoinColumn(name = "couponId") }, inverseJoinColumns = {
			@JoinColumn(name = "customerId") })
	@JsonIgnore
	private List<Customer> customer;

	public List<Customer> getCustomer() {
		return customer;
	}

	public void setCustomer(List<Customer> customer) {
		this.customer = customer;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Coupon(Integer couponId, String title, String description, LocalDate startDate, LocalDate endDate,
			Integer amount, double price, String image, Category categoryId) {
		super();
		this.id = couponId;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
		this.categoryId = categoryId;
	}

	public Coupon(Integer couponId, String title, String description, LocalDate startDate, LocalDate endDate,
			Integer amount, double price, String image, Category categoryId, Company company) {
		super();
		this.id = couponId;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
		this.categoryId = categoryId;
		this.company = company;
	}

	public Coupon(String title, String description, LocalDate startDate, LocalDate endDate, Integer amount,
			double price, String image, Category categoryId, Company company) {
		super();
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
		this.categoryId = categoryId;
		this.company = company;
	}

	public Coupon(Integer companieId, Category categoryId, String title, String description, LocalDate startDate,
			LocalDate endDate, Integer amount, Double price, String image) {
		this.categoryId = categoryId;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

	public Coupon() {
		super();
	}

	public enum Category {
		FOOD, ELECTRICITY, SPORT, HOTEL;
	}

	public Integer getCouponId() {
		return id;
	}

	public void setCouponId(Integer couponId) {
		this.id = couponId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Category getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Category categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "Coupons [couponId=" + id + ", title=" + title + ", description=" + description + ", startDate="
				+ startDate + ", endDate=" + endDate + ", amount=" + amount + ", price=" + price + ", image=" + image
				+ ", categoryId=" + categoryId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, categoryId, id, description, endDate, image, price, startDate, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Coupon)) {
			return false;
		}
		Coupon other = (Coupon) obj;
		return Objects.equals(amount, other.amount) && categoryId == other.categoryId
				&& Objects.equals(id, other.id) && Objects.equals(description, other.description)
				&& Objects.equals(endDate, other.endDate) && Objects.equals(image, other.image)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& Objects.equals(startDate, other.startDate) && Objects.equals(title, other.title);
	}

}
