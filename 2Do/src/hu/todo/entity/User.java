package hu.todo.entity;

import java.util.Date;

public class User {

	private int id;
	private String name;
	private String token;
	private String email;
	private String password;
	private Date created_at;
	private Date updated_at;
	
	public User(int id, String name, String token, String email,
			String password, Date created_at, Date updated_at) {
		super();
		this.id = id;
		this.name = name;
		this.token = token;
		this.email = email;
		this.password = password;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	 
}
