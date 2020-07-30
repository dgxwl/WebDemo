package cn.abc.def.entity;

import com.fasterxml.jackson.annotation.JsonView;

public class User {

	private Integer id;
	private String username;
	private String password;
	private String phone;

	public interface UserInterface {}
	public interface AdminInterface extends UserInterface {}
	
	public User() {
		
	}

	public User(Integer id, String username, String password, String phone) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.phone = phone;
	}

	@JsonView(AdminInterface.class)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonView(UserInterface.class)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonView(AdminInterface.class)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonView(UserInterface.class)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", phone=" + phone + "]";
	}
}
