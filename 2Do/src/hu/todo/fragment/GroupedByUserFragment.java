package hu.todo.fragment;

import hu.todo.activity.ShowTaskActivity_;
import hu.todo.adapter.TodoAdapter;
import hu.todo.entity.Task;
import hu.todo.function.ApplicationFunctions;
import hu.todo.function.SystemFunctions;
import hu.todo.rest.MyErrorHandler;
import hu.todo.rest.RestInterface;
import hu.todo.toast.Toaster;
import hu.todo.utility.CalendarFormatter;
import hu.todo.utility.LocalDatabaseOpenHelper;
import hu.todo.utility.OrientationLocker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.Transactional;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;

/**
 * Megjeleníti az összes teendőt user_id alapján.
 */
@EFragment
public class GroupedByUserFragment extends ListFragment implements
		Comparator<Task>, OnClickListener {

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
			Toaster.loginWarning(getActivity()); // setretaininstance miatt a
													// dialog sem fog
													// ujraindulni
			FragmentManager fm = getActivity().getSupportFragmentManager();
			LoginDialogFragment_ editNameDialog = new LoginDialogFragment_();
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
		showDialog();
		String token = ApplicationFunctions.getInstance().getUserFunctions()
				.getLoggedUser().getToken();
		taskManager.setRestErrorHandler(myErrorHandler);
		List<Task> tasks = null;
		if (SystemFunctions.isOnline(getActivity())) {
			tasks = taskManager.getAllTask(token);

			// hiba történt
			if (tasks.size() > 0 && tasks.get(0).getErrors() != null) {
				AlertDialog.Builder b = new AlertDialog.Builder(getActivity())
						.setTitle("Hiba történt!")
						.setPositiveButton("OK", this)
						.setNegativeButton("Cancel", this);

				for (String s : tasks.get(0).getErrors()) {
					b.setMessage(s + "\n");
				}
				b.show();
			}
		}
		if (tasks != null)
			showResult(tasks);

		dismissDialog();
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
			Collections.sort(items, this);
			adapter.notifyDataSetChanged();
		} // TODO vmi üzenet
	}

	@Override
	public int compare(Task lhs, Task rhs) {
		return lhs.getUser_id() - rhs.getUser_id();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub

	}

}
