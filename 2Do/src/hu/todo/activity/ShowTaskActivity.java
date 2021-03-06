package hu.todo.activity;

import hu.todo.R;
import hu.todo.entity.Task;
import hu.todo.entity.User;
import hu.todo.function.ApplicationFunctions;
import hu.todo.function.SystemFunctions;
import hu.todo.rest.MyErrorHandler;
import hu.todo.rest.RestInterface;
import hu.todo.utility.OrientationLocker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * A felülete egy tasknak, ami akkor jön elő, ha az AllFragmentben kattintottunk
 * listaelemre
 */
@EActivity(R.layout.activity_show_task)
@OptionsMenu(R.menu.menu_show_task)
public class ShowTaskActivity extends FragmentActivity implements
		OnItemClickListener, OnClickListener {

	private static Calendar dateCal;
	private static Calendar timeCal;

	/**
	 * Ez válósítja meg az óra-perc és a dátum kiválasztását pickerrel
	 */
	public static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener,
			TimePickerDialog.OnTimeSetListener {

		private ShowTaskActivity activity;
		private FragmentManager fragmentManager;

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			this.activity = (ShowTaskActivity) activity;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);
			fragmentManager = getFragmentManager();

			if (fragmentManager.findFragmentByTag("DATE") != null) {
				return new DatePickerDialog(getActivity(), this, year, month,
						day);
			} else {
				return new TimePickerDialog(getActivity(), this, hour, minute,
						true);
			}

		}

		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			dateCal = Calendar.getInstance();
			dateCal.set(Calendar.YEAR, year);
			dateCal.set(Calendar.MONTH, month);
			dateCal.set(Calendar.DAY_OF_MONTH, day);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd",
					Locale.getDefault());
			activity.datePicker.setText(formatter.format(dateCal.getTime()));
		}

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			timeCal = Calendar.getInstance();
			timeCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
			timeCal.set(Calendar.MINUTE, minute);
			String format = String.format(Locale.getDefault(), "%02d:%02d",
					timeCal.get(Calendar.HOUR_OF_DAY),
					timeCal.get(Calendar.MINUTE));
			activity.timePicker.setText(format);
		}

	}

	@ViewById
	TextView title;
	@ViewById
	EditText description;
	@ViewById
	AutoCompleteTextView user;
	@ViewById
	TextView date;
	@ViewById
	EditText datePicker;
	@ViewById
	EditText timePicker;
	@ViewById
	TextView created;
	@ViewById
	EditText createdPicker;
	@ViewById
	TextView updated;
	@ViewById
	EditText updatedPicker;

	// a teendő listaelem amire kattintás történt
	@Extra
	Task task;

	// szerkesztés alatt áll-e a task
	@InstanceState
	boolean isEdit = false;

	// azert kellenek hogy az edittext valtoztatás után is tartalmazza a jó
	// értéket
	@InstanceState
	String desc;
	@InstanceState
	String dateP;
	@InstanceState
	String dateT;
	@InstanceState
	String createdP;
	@InstanceState
	String updatedP;

	@RestService
	RestInterface taskManager;

	@Bean
	MyErrorHandler myErrorHandler;

	@OptionsMenuItem(R.id.edit)
	MenuItem editMenuItem;

	@OptionsMenuItem(R.id.save)
	MenuItem saveMenuItem;
	private Bundle instanceState;

	private User selectedUser;
	private List<User> allUser;

	@OptionsItem(android.R.id.home)
	void navigateBackOnHomePress() {
		onBackPressed();
	}

	@OptionsItem(R.id.edit)
	void editTask() {
		if (!isEdit) {
			setViewsFocusable();
			isEdit = true;
		}
	}

	private String ISO8601(Calendar c) {
		String s = ISO8601.format(c.getTime());
		return (s.substring(0, 22) + ":" + s.substring(22));
	}

	private final SimpleDateFormat ISO8601 = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());

	@OptionsItem(R.id.save)
	void saveTask() {
		if (isEdit) {
			setViewsUnFocusable();
			isEdit = false;
			taskManager.setRestErrorHandler(myErrorHandler);
			String token = ApplicationFunctions.getInstance()
					.getUserFunctions().getLoggedUser().getToken();
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			Calendar now = Calendar.getInstance(); // TODO updated mezot meg a
													// createdet le lehet
													// tiltani
			map.set("task[user_id]",
					""
							+ (selectedUser != null ? selectedUser.getId()
									: task.getUser_id())); // TODO
			map.set("task[title]", "" + title.getText());
			map.set("task[description]", desc);
			map.set("task[date]",
					fromDatePickerToDateString(datePicker.getText().toString(),
							timePicker.getText().toString())); // TODO a
																// pickerbol
																// majd
			map.set("task[created_at]", ISO8601(task.getCreated_at()));
			map.set("task[updated_at]", ISO8601(now));
			updateTask(map, token);
			finish();
		}
	}

	private String fromDatePickerToDateString(String dateText, String timeText) {
		return dateText + "T" + timeText + ":00" + ".000Z";
	}

	@Background
	void updateTask(MultiValueMap<String, String> map, String token) {
		showDialog();
		if (SystemFunctions.isOnline(this)) {
			Task taskError = taskManager.updateTask(map, task.getId(), token);

			if (taskError.getErrors() != null) {
				AlertDialog.Builder b = new AlertDialog.Builder(this)
						.setTitle("Hiba történt!")
						.setPositiveButton("OK", this)
						.setNegativeButton("Cancel", this);

				for (String s : taskError.getErrors()) {
					b.setMessage(s + "\n");
				}
				b.show();
			}
		}
		dismissDialog();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null) {
			instanceState = savedInstanceState;
			isEdit = savedInstanceState.getBoolean("isEdit");
		}
	}

	@AfterViews
	void afterViews() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		title.setText(task.getTitle());
		description.setText(instanceState != null
				&& instanceState.getString("desc") != null ? instanceState
				.getString("desc") : task.getDescription());
		datePicker.setText(instanceState != null
				&& instanceState.getString("dateP") != null ? instanceState
				.getString("dateP") : (task.getDate().get(Calendar.YEAR) + "-"
				+ (task.getDate().get(Calendar.MONTH) + 1) + "-" + task
				.getDate().get(Calendar.DAY_OF_MONTH)));
		timePicker.setText(instanceState != null
				&& instanceState.getString("timeP") != null ? instanceState
				.getString("timeP") : (task.getDate().get(Calendar.HOUR_OF_DAY)
				+ ":" + task.getDate().get(Calendar.MINUTE)));
		createdPicker
				.setText(instanceState != null
						&& instanceState.getString("createdP") != null ? instanceState
						.getString("createdP")
						: (task.getCreated_at().get(Calendar.YEAR)
								+ "-"
								+ (task.getCreated_at().get(Calendar.MONTH) + 1)
								+ "-" + task.getCreated_at().get(
								Calendar.DAY_OF_MONTH)));
		updatedPicker
				.setText(instanceState != null
						&& instanceState.getString("updatedP") != null ? instanceState
						.getString("updatedP")
						: (task.getUpdated_at().get(Calendar.YEAR)
								+ "-"
								+ (task.getUpdated_at().get(Calendar.MONTH) + 1)
								+ "-" + task.getUpdated_at().get(
								Calendar.DAY_OF_MONTH)));

		// azert kellenek hogy ne legyen ilyen: 12:1 vagy 2014-7-8
		String[] dateSplit = datePicker.getText().toString().split("-");
		String s = String.format(Locale.getDefault(), "%d-%02d-%02d",
				Integer.parseInt(dateSplit[0]), Integer.parseInt(dateSplit[1]),
				Integer.parseInt(dateSplit[2]));
		datePicker.setText(s);

		String[] timeSplit = timePicker.getText().toString().split(":");
		String s2 = String.format(Locale.getDefault(), "%02d:%02d",
				Integer.parseInt(timeSplit[0]), Integer.parseInt(timeSplit[1]));
		timePicker.setText(s2);

		String[] createdSplit = createdPicker.getText().toString().split("-");
		String s3 = String.format(Locale.getDefault(), "%d-%02d-%02d",
				Integer.parseInt(createdSplit[0]),
				Integer.parseInt(createdSplit[1]),
				Integer.parseInt(createdSplit[2]));
		createdPicker.setText(s3);

		String[] updatedSplit = updatedPicker.getText().toString().split("-");
		String s4 = String.format(Locale.getDefault(), "%d-%02d-%02d",
				Integer.parseInt(updatedSplit[0]),
				Integer.parseInt(updatedSplit[1]),
				Integer.parseInt(updatedSplit[2]));
		updatedPicker.setText(s4);

		if (isEdit) {
			setViewsFocusable();
		}

		// feltölti a user autocomplete textviewt
		taskManager.setRestErrorHandler(myErrorHandler);
		String token = ApplicationFunctions.getInstance().getUserFunctions()
				.getLoggedUser().getToken();
		if (token != null) {
			getUsers(token);
		}

	}

	// még az afterviews és oncreate előtt lefut
	@AfterTextChange(R.id.description)
	void descChanged() {
		desc = description.getText().toString();
	}

	@AfterTextChange(R.id.datePicker)
	void datePChanged() {
		dateP = datePicker.getText().toString();
	}

	@AfterTextChange(R.id.timePicker)
	void timePChanged() {
		dateT = timePicker.getText().toString();
	}

	@AfterTextChange(R.id.createdPicker)
	void createdPChanged() {
		createdP = createdPicker.getText().toString();
	}

	@AfterTextChange(R.id.updatedPicker)
	void updatedPChanged() {
		updatedP = updatedPicker.getText().toString();
	}

	void setViewsFocusable() {
		description.setFocusableInTouchMode(true);
		user.setFocusableInTouchMode(true);
		user.setEnabled(true);
	}

	void setViewsUnFocusable() {
		description.setFocusable(false);
		user.setFocusable(false);
		user.setEnabled(false);
	}

	private ProgressDialog progressDialog;

	@UiThread
	void showDialog() {
		OrientationLocker.lockScreenOrientation(this);
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Kérem várjon ... ");
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
	}

	@Background
	void getUsers(String token) {
		showDialog();
		if (SystemFunctions.isOnline(this)) {
			allUser = taskManager.getAllUser(token);
			// hiba történt
			if (allUser.size() > 0 && allUser.get(0).getErrors() != null) {
				AlertDialog.Builder b = new AlertDialog.Builder(this)
						.setTitle("Hiba történt!")
						.setPositiveButton("OK", this)
						.setNegativeButton("Cancel", this);

				for (String s : allUser.get(0).getErrors()) {
					b.setMessage(s + "\n");
				}
				b.show();
			} else {
				setAuCompleteUser();
			}
		}
		dismissDialog();
	}

	@UiThread
	void dismissDialog() {
		progressDialog.dismiss();
		OrientationLocker.unlockScreenOrientation(this);
	}

	// beállítja a usereket az autocomplete textviewnak és beállítja a task
	// felelősét
	@UiThread
	void setAuCompleteUser() {
		if (allUser != null) {
			ArrayAdapter<User> adapter = new ArrayAdapter<User>(this,
					android.R.layout.simple_dropdown_item_1line, allUser);
			user.setAdapter(adapter);
			user.setOnItemClickListener(this);
			String owner = null;
			for (User u : allUser) {
				if (u.getId() == task.getUser_id()) {
					owner = u.getName();
				}
			}
			if (owner != null) {
				user.setText(owner);
			}
		}
	}

	// elinditja a dátum kiválasztó pickert
	@Click(R.id.datePicker)
	void showDatePicker() {
		if (isEdit) {
			FragmentTransaction fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();
			DatePickerFragment fragment = new DatePickerFragment();
			fragmentTransaction.add(fragment, "DATE");
			fragmentTransaction.commit();
		}
	}

	// elinditja az időkiválasztó pickert
	@Click(R.id.timePicker)
	void showTimePicker() {
		if (isEdit) {
			FragmentTransaction fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();
			DatePickerFragment fragment = new DatePickerFragment();
			fragmentTransaction.add(fragment, "TIME");
			fragmentTransaction.commit();
		}
	}

	// az autocomplete textviewból amire kattintottunk arra tesz egy referenciát
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		selectedUser = (User) parent.getItemAtPosition(position);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub

	}

}
