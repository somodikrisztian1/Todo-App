package hu.todo.fragment;

import hu.todo.R;
import hu.todo.entity.User;
import hu.todo.function.ApplicationFunctions;
import hu.todo.rest.TaskRestInterface;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

@EFragment
public class LoginDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

	@RestService
    TaskRestInterface taskManager;
	
    @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	
	    AlertDialog.Builder b=  new  AlertDialog.Builder(getActivity())
	    .setTitle("Jelentkezz be!")
	    .setPositiveButton("OK",this)
	    .setNegativeButton("Cancel",this);
	
	    LayoutInflater i = getActivity().getLayoutInflater();
	
	    View v = i.inflate(R.layout.fragment_login_dialog, null);
	
	
	    b.setView(v);
	    return b.create();
	}
    
	@Override
	public void onClick(DialogInterface dialog, int which) {
		if(which == Dialog.BUTTON_NEGATIVE) {
			dialog.dismiss();
		}
		else {
			getItemsInBackground();
		}
		
	}
	
	@Background
    void getItemsInBackground() {
		User loggedUser = taskManager.login("somodikrisztian1@gmail.com", "letmein");
		showResult(loggedUser);
    }
 
 
    @UiThread
    void showResult(User loggedUser) {
    	if(loggedUser != null) {
			ApplicationFunctions.getInstance().getUserFunctions().setLoggedUser(loggedUser);
			Log.d("lol", "logged: " + ApplicationFunctions.getInstance().getUserFunctions().getLoggedUser().getToken() + " " + ApplicationFunctions.getInstance().getUserFunctions().getLoggedUser().getName());
		}
		else {
			Log.d("lol", "null");
		}
    }
	
}
