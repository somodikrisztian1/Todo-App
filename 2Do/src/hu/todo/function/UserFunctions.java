package hu.todo.function;

import hu.todo.entity.User;

public class UserFunctions {
	private User loggedUser;
	
	
	// TODO kivenni
	public UserFunctions() {
		super();
		loggedUser = new User();
		loggedUser.setToken("jsn38snakx84nd91jxamfnxbi1k24n8xcqnfkf92nfalaci12n");
	}

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
