package app.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CouponAppException extends ResponseStatusException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CouponAppException(HttpStatus status, String reason, Throwable cause) {
		super(status, reason, cause);
	}

	public CouponAppException(HttpStatus status, String reason) {
		super(status, reason);
	}

	public CouponAppException(HttpStatus status) {
		super(status);
	}

}
