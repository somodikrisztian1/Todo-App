package hu.todo.function;

import hu.todo.entity.LoggedUser;
import hu.todo.entity.User;

public class UserFunctions {
	private LoggedUser loggedInUser;
	
	public boolean getLoginStatus() {
		return loggedInUser == null ? false : true;
	}

	public void setLoggedInUser(LoggedUser loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public User getLoggedInUser()
	{
		return loggedInUser;
	}

	public void logout()
	{
		loggedInUser = null;
	}

}
