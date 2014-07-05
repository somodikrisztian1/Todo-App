package hu.todo.function;

import hu.todo.entity.User;

public class UserFunctions {
	private User loggedUser;
	
	public boolean getLoginStatus() {
		return loggedUser == null ? false : true;
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
