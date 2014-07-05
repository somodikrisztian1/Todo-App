package hu.todo.entity;

import java.util.Date;

public class LoggedUser extends User {

	private boolean isLoggedIn;
	
	public LoggedUser(int id, String name, String token, String email,
			String password, Date created_at, Date updated_at) {
		super(id, name, token, email, password, created_at, updated_at);
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	
}
