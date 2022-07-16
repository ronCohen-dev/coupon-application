package app.core.dao;

public class User {

	private String password;
	private String clientType;
	private String email;
	private String token;

	public User() {
		super();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "User [password=" + password + ", clientType=" + clientType + ", email=" + email + ", token=" + token
				+ "]";
	}

}
