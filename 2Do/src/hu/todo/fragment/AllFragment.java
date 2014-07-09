package hu.todo.fragment;

import hu.todo.R;
import hu.todo.activity.ShowTaskActivity_;
import hu.todo.adapter.TodoAdapter;
import hu.todo.entity.Task;
import hu.todo.entity.User;
import hu.todo.function.ApplicationFunctions;
import hu.todo.function.SystemFunctions;
import hu.todo.rest.MyErrorHandler;
import hu.todo.rest.RestInterface;
import hu.todo.sharedprefs.MyPrefs_;
import hu.todo.utility.CalendarFormatter;
import hu.todo.utility.LocalDatabaseOpenHelper;
import hu.todo.utility.OrientationLocker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.Transactional;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Megjeleníti az összes teendőt időrendben.
 */
@EFragment
public class AllFragment extends ListFragment implements OnClickListener {
	
	AllFragment frag;
	
	public class LoginDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {
		
		class Async extends AsyncTask<Void, Void, User> implements OnClickListener {

			@Override
			protected User doInBackground(Void... params) {
			User loggedUser = null;
		

				
				taskManager.setRestErrorHandler(myErrorHandler);
				if(SystemFunctions.isOnline(getActivity())) {
					Log.d("lol", "e: " + email.getText().toString() + " p: " + password.getText().toString());
					loggedUser = taskManager.login(email.getText().toString(), password.getText().toString());
				}
				
				return loggedUser;

				}
				@Override
				protected void onPostExecute(User loggedUser) {
					super.onPostExecute(loggedUser);
					
					if(loggedUser != null) {
						if(loggedUser.getErrors() != null) {
							AlertDialog.Builder b =  new  AlertDialog.Builder(frag.getActivity())
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
							frag.myPref.token().put(token);
							frag.getItemsInBackground();
						} // TODO sikertelen a login
					}

				
				}
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
		}
		
		
		EditText email;
		EditText password;
		
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			shouldRe = false;
		}
		
		
	    @Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		    AlertDialog.Builder b=  new  AlertDialog.Builder(getActivity())
		    .setTitle("Jelentkezz be!")
		    .setPositiveButton("OK",this)
		    .setNegativeButton("Cancel",this);
		
		    LayoutInflater i = getActivity().getLayoutInflater();
		
		    View v = i.inflate(R.layout.fragment_login_dialog, null);
		    email = (EditText)v.findViewById(R.id.email);
		    password = (EditText) v.findViewById(R.id.password);
		
		    b.setView(v);
		    
		    return b.create();
		}
	    
	    int which = -10;
	    boolean shouldRe;
	    
		@Override
		public void onClick(DialogInterface dialog, int which) {
			this.which = which;
			if(which == Dialog.BUTTON_POSITIVE) {
				Log.d("lol", "email: " + email.getText());
				Log.d("lol", "pass: " + password.getText());
				Async async = new Async();
				async.execute();
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
				shouldRe = true;
			}

		}
		
		@Override
		public void onPause() {
			Log.d("lol", "pauz");
			if(shouldRe) {
				// kulonben azt mondja h maar hozza van adva
				frag.getActivity().getSupportFragmentManager().popBackStack();
				show(frag.getActivity().getSupportFragmentManager(), "dialog_login");
			}
			super.onPause();
		}
		
		
	}
	
	@Pref
	MyPrefs_ myPref;
	
	@Bean
	public TodoAdapter adapter;

	@Bean
	MyErrorHandler myErrorHandler;

	@RestService
	RestInterface taskManager; // Inject

	@Transactional
	void putsome(SQLiteDatabase db) {
		// db.execSQL("INSERT INTO tasks (id, user_id, title) VALUES (1, 3, 'lol');");
	}

	@Transactional
	void getLocalTasks(SQLiteDatabase db) {
		Cursor c = db.query("tasks", new String[] { "*" }, null, null, null,
				null, null);
		Log.d("lol", "cnt: " + c.getCount());
		if (c.moveToFirst()) {
			ArrayList<Task> tasks = new ArrayList<Task>();
			while (!c.isAfterLast()) {
				int id = c.getInt(0);
				int user_id = c.getInt(1);
				String title = c.getString(2);
				String description = c.getString(3);
				String date = c.getString(4);
				String created_at = null;
				String updated_at = null;
				if (c.getString(5) != null)
					created_at = c.getString(5);
				if (c.getString(6) != null)
					updated_at = c.getString(6);

				Log.d("lol", date == null ? "DATE null" : "DATE nem null");
				Task task = new Task(id, user_id, title, description,
						CalendarFormatter.ISO8601(date),
						created_at == null ? null : CalendarFormatter
								.ISO8601(created_at), updated_at == null ? null
								: CalendarFormatter.ISO8601(updated_at), null);
				task.setLocal(true);
				tasks.add(task);
				c.move(1);
			}
			showResult(tasks);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		frag = this;
		if(myPref.token().exists()) {
			ApplicationFunctions.getInstance().getUserFunctions().getLoggedUser().setToken(myPref.token().get());
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		LocalDatabaseOpenHelper helper = new LocalDatabaseOpenHelper(
				getActivity());
		SQLiteDatabase readableDatabase = helper.getReadableDatabase();
		getLocalTasks(readableDatabase);
		readableDatabase.close();
		if (ApplicationFunctions.getInstance().getUserFunctions()
				.getLoginStatus()) {
			getItemsInBackground();
		} else {
			FragmentManager fm = getActivity().getSupportFragmentManager();
			AllFragment.LoginDialogFragment editNameDialog = new AllFragment.LoginDialogFragment();
			editNameDialog.show(fm, "dialog_login");
		}
	}

	@AfterViews
	void binddAdapter() {
		setListAdapter(adapter);
	}

	@ItemClick(android.R.id.list)
	public void myListItemClicked(Task task) {
		ShowTaskActivity_.intent(this).task(task).start();
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
	void getItemsInBackground() {
		if(ApplicationFunctions.getInstance().getUserFunctions()
				.getLoggedUser().getToken() != null) {
			showDialog();
			String token = ApplicationFunctions.getInstance().getUserFunctions()
					.getLoggedUser().getToken();
			taskManager.setRestErrorHandler(myErrorHandler);
			List<Task> tasks = null;
			if(SystemFunctions.isOnline(getActivity())) {
				tasks = taskManager.getAllTask(token);
				
				// hiba történt
				if(tasks.size() > 0 && tasks.get(0).getErrors() != null) {
					AlertDialog.Builder b =  new  AlertDialog.Builder(getActivity())
				    .setTitle("Hiba történt!")
				    .setPositiveButton("OK", this)
				    .setNegativeButton("Cancel",this);
					
					for(String s : tasks.get(0).getErrors()) {
						b.setMessage(s + "\n");
					}
					b.show();
				}
			}
			
			if(tasks != null)
				showResult(tasks);
			
			dismissDialog();
		}
	}

	@UiThread
	void dismissDialog() {
		progressDialog.dismiss();
		OrientationLocker.unlockScreenOrientation(getActivity());
	}

	@UiThread
	void showResult(List<Task> tasks) {
		if (tasks != null) {
			ArrayList<Task> items = adapter.getItems();
			items.clear();
			for (Task task : tasks) {
				items.add(task);
			}
			Collections.sort(items);
			adapter.notifyDataSetChanged();
		} // TODO vmi üzenet
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub

	}

}