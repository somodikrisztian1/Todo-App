package hu.todo.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

@SuppressWarnings("serial")
public class User implements Serializable{
	
	@JsonProperty("id")
	int id;
	String name;
	String token;
	String email;
	String password;
	Calendar created_at;
	Calendar updated_at;
	
	@JsonProperty("errors")
	List<String> errors;

	
	
	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
  
	public User(String name) {
		super();
		this.name = name;
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

	public Calendar getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Calendar created_at) {
		this.created_at = created_at;
	}

	public Calendar getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Calendar updated_at) {
		this.updated_at = updated_at;
	}
	 
	@Override
	public String toString() {
		return name;
	}
	
}
