package hu.todo.function;

public class ApplicationFunctions {
	private static ApplicationFunctions instance;

	private UserFunctions userFunctions;

	public ApplicationFunctions() {
		super();
		userFunctions = new UserFunctions();
	}

	/**
	 * Singleton. Az alkalmazásréteg belépési pontja.
	 * 
	 * @return példány.
	 */
	public static ApplicationFunctions getInstance() {
		synchronized (ApplicationFunctions.class) {
			if (instance == null)
				instance = new ApplicationFunctions();

			return instance;
		}
	}

	public UserFunctions getUserFunctions() {
		return userFunctions;
	}
}
