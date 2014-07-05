package hu.todo.toast;

import android.content.Context;
import android.widget.Toast;

// Toast generáló
public class Toaster {
	
	public static void loginWarning(Context context) {
		Toast.makeText(context, "Ehhez a funkcióhoz be kell jelentkezni!", Toast.LENGTH_SHORT).show();
	}
}
