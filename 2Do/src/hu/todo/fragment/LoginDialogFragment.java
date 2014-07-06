package hu.todo.fragment;

import hu.todo.R;
import hu.todo.activity.MainActivity;
import hu.todo.entity.User;
import hu.todo.function.ApplicationFunctions;
import hu.todo.rest.TaskRestInterface;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.rest.RestService;

import android.app.Activity;
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
	
	AllFragment fragment;
	
	@RestService
    TaskRestInterface taskManager;
	
	MainActivity activity;
	// mindig ujra create meg dismiss
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		shouldRe = false;
		Log.d("lol", "" + shouldRe);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = (MainActivity) activity;
		Log.d("lol", "attach");
	}
	
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
    
    int which = -10;
    boolean shouldRe;
    
	@Override
	public void onClick(DialogInterface dialog, int which) {
		this.which = which;
		if(which == Dialog.BUTTON_POSITIVE) {
			login();
		}
		else {
			// negativ gombnal nem fut le az oncancel
			if(fragment == null)
			fragment = (AllFragment) activity.getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + activity.viewPager.getCurrentItem());
			shouldRe = true;
		}

	}
	
	@Override
	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
		Log.d("lol", "cancel");
		if(which != Dialog.BUTTON_POSITIVE) {
			Log.d("lol", "ujra");
			if(fragment == null)
				fragment = (AllFragment) activity.getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + activity.viewPager.getCurrentItem());
			shouldRe = true;
		}

	}
	
	@Override
	public void onPause() {
		Log.d("lol", "pauz");
		if(shouldRe) {
			// kulonben azt mondja h maar hozza van adva
			if(fragment == null) Log.d("lol", "ffff");
			activity.getSupportFragmentManager().popBackStack();
			show(activity.getSupportFragmentManager(), "dialog_login");
		}
		super.onPause();
	}
	
	@Background
    void login() {
		User loggedUser = null;
	
		try {
			loggedUser = taskManager.login("somodikrisztian1@gmail.com", "letmein");
		}catch (Exception e) {
			e.printStackTrace();
			activity.getSupportFragmentManager().popBackStack();  // nem szükséges ?!
			show(activity.getSupportFragmentManager(), "dialog_login");
		}
		if(loggedUser != null) {
			ApplicationFunctions.getInstance().getUserFunctions().setLoggedUser(loggedUser);
			fragment.getItemsInBackground();
		}
    }
	
}