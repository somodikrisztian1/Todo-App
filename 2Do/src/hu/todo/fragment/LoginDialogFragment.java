package hu.todo.fragment;

import hu.todo.R;
import hu.todo.activity.MainActivity;
import hu.todo.entity.User;
import hu.todo.function.ApplicationFunctions;
import hu.todo.function.SystemFunctions;
import hu.todo.rest.MyErrorHandler;
import hu.todo.rest.RestInterface;
import hu.todo.sharedprefs.MyPrefs_;
import hu.todo.utility.OrientationLocker;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

@EFragment
public class LoginDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {
	
	AllFragment fragment;
	
	@Pref
	MyPrefs_ myPrefs;
	
	@Bean
	MyErrorHandler myErrorHandler;
	
	@RestService
    RestInterface taskManager;
	
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
		if(fragment == null)
			fragment = (AllFragment) activity.getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + activity.viewPager.getCurrentItem());
		if(which == Dialog.BUTTON_POSITIVE) {
			login();
		}
		else {
			// negativ gombnal nem fut le az oncancel
			
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
	
	private ProgressDialog progressDialog;
	
	@UiThread
	void showDialog() {
		OrientationLocker.lockScreenOrientation(getActivity());
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage("Kérem várjon ... ");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
	}
	
	@Background
    void login() {
		showDialog();
		User loggedUser = null;
	
		try {
			
			taskManager.setRestErrorHandler(myErrorHandler);
			if(SystemFunctions.isOnline(getActivity()))
				loggedUser = taskManager.login("somodikrisztian1@gmail.com", "letmein");
		}catch (Exception e) {
			e.printStackTrace();
			Log.d("lol", "itt hiba");
			// ha már nem érvényes a token
			myPrefs.clear();
			
			activity.getSupportFragmentManager().popBackStack();  // nem szükséges ?!
			show(activity.getSupportFragmentManager(), "dialog_login");
		}
		if(loggedUser != null) {
			if(loggedUser.getErrors() != null) {
				AlertDialog.Builder b =  new  AlertDialog.Builder(getActivity())
			    .setTitle("Hiba történt!")
			    .setPositiveButton("OK", this)
			    .setNegativeButton("Cancel",this);
				
				for(String s : loggedUser.getErrors()) {
					b.setMessage(s + "\n");
				}
				b.show();
				shouldRe = true;
			}
			else {
				ApplicationFunctions.getInstance().getUserFunctions().setLoggedUser(loggedUser);
				String token = ApplicationFunctions.getInstance().getUserFunctions().getLoggedUser().getToken();
				myPrefs.token().put(token);
				fragment.getItemsInBackground();
			} // TODO sikertelen a login
		}
		dismissDialog();
    }
	
	@UiThread
	void dismissDialog() {
		progressDialog.dismiss();
		OrientationLocker.unlockScreenOrientation(getActivity());
	}
}