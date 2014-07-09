package hu.todo.function;

import hu.todo.entity.User;

public class UserFunctions {
	private User loggedUser;
	
	
	// TODO kivenni
	public UserFunctions() {
		super();
		loggedUser = new User();
	}

	public boolean getLoginStatus() {
		return loggedUser.getToken() == null ? false : true;
	}

	public void setLoggedUser(User loggedInUser) {
		this.loggedUser = loggedInUser;
	}

	public User getLoggedUser()
	{
		return loggedUser;
	}

	public void logout()
	{
		loggedUser = null;
	}

}
