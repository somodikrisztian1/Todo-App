package hu.todo.activity;

import hu.todo.R;
import hu.todo.entity.Task;
import hu.todo.entity.User;
import hu.todo.function.ApplicationFunctions;
import hu.todo.function.SystemFunctions;
import hu.todo.rest.MyErrorHandler;
import hu.todo.rest.RestInterface;
import hu.todo.utility.LocalDatabaseOpenHelper;

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
import org.androidannotations.annotations.FocusChange;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.Transactional;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

// ez a felülete egy task létrehozásának
@EActivity(R.layout.activity_add_task) 
public class AddTaskActivity extends FragmentActivity implements OnItemClickListener {
	
	private static Calendar dateCal;
	private static Calendar timeCal;
	
	public static class DatePickerFragment extends DialogFragment
	implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
	
		private AddTaskActivity activity; 
		private FragmentManager fragmentManager;
	
		
		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			this.activity = (AddTaskActivity) activity;
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
		
		if(fragmentManager.findFragmentByTag("DATE") != null) {
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		else {
			return new TimePickerDialog(getActivity(), this, hour, minute, true);
		}
		
	}
	
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		dateCal = Calendar.getInstance();
		dateCal.set(Calendar.YEAR, year);
		dateCal.set(Calendar.MONTH, month);
		dateCal.set(Calendar.DAY_OF_MONTH, day);
		
		activity.datePicker.setText((dateCal.get(Calendar.YEAR) + " / " + (dateCal.get(Calendar.MONTH) + 1) + " / " + (dateCal.get(Calendar.DAY_OF_MONTH))));
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		timeCal = Calendar.getInstance();
		timeCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		timeCal.set(Calendar.MINUTE, minute);
		
		activity.timePicker.setText((timeCal.get(Calendar.HOUR_OF_DAY)) + ":" + (timeCal.get(Calendar.MINUTE)));
	}
	
	
//	@Override
//	public void onPause() {
//		Log.d("lol", "pop");
//		fragmentManager.popBackStack();
//		super.onPause();
//	}

}
	
	@ViewById
	EditText title;
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
	@ViewById
	Button btnCreate;
	
	// azert kellenek hogy az edittext valtoztatás után is tartalmazza a jó értéket
	@InstanceState
	String desc;
	@InstanceState
	String dateP;
	@InstanceState
	String createdP;
	@InstanceState
	String updatedP;
	
	@RestService
    RestInterface taskManager;
	
	@Bean
	MyErrorHandler myErrorHandler;
	
	
	private User selectedUser;
	private List<User> allUser;
	
	@OptionsItem(android.R.id.home)
	void navigateBackOnHomePress() {
		onBackPressed();
	}

	private String ISO8601(Calendar c) {
		String s = ISO8601.format(c.getTime());
		return (s.substring(0, 22)+":"+s.substring(22));
	}
	private Bundle instanceState;
	private final SimpleDateFormat ISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
	
	
//	@Background
//	void updateTask(MultiValueMap<String, String> map, String token) {
//		taskManager.updateTask(map, task.getId(), token);  // TODO kf
//	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@AfterViews
	void afterViews() {
			getActionBar().setDisplayHomeAsUpEnabled(true);
			
			
			// feltölti a user autocomplete textviewt
			taskManager.setRestErrorHandler(myErrorHandler);
			String token = ApplicationFunctions.getInstance().getUserFunctions().getLoggedUser().getToken();
			if(token != null) {
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
	
	@AfterTextChange(R.id.createdPicker)
	void createdPChanged() {
		createdP = createdPicker.getText().toString();
	}
	
	@AfterTextChange(R.id.updatedPicker)
	void updatedPChanged() {
		updatedP = updatedPicker.getText().toString();
	}

	
	@Background
	void getUsers(String token) {
		 allUser = taskManager.getAllUser(token);
		 setAuCompleteUser();
	}
	
	@UiThread
	void setAuCompleteUser() {
		if(allUser != null) {
			ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, android.R.layout.simple_dropdown_item_1line, allUser);
			 user.setAdapter(adapter);
			 user.setOnItemClickListener(this);
		}
	}

	@FocusChange(R.id.datePicker)
	void showDatePicker() {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		DatePickerFragment fragment = new DatePickerFragment();
		fragmentTransaction.add(fragment, "DATE"); 
		fragmentTransaction.commit();
	}
	
	@FocusChange(R.id.timePicker)
	void showTimePicker() {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		DatePickerFragment fragment = new DatePickerFragment();
		fragmentTransaction.add(fragment, "TIME");
		fragmentTransaction.commit();
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		selectedUser = (User) parent.getItemAtPosition(position);
		Log.d("lol", "" + selectedUser.getId());
	}
	
	Task task;
	
	@Click(R.id.btnCreate)
	void clickCreate() {
		if(title.getText() != null && user.getText() != null
				&& description.getText() != null && datePicker.getText() != null) {
			task = new Task();
			task.setUser_id(selectedUser.getId());
			task.setTitle(title.getText().toString());
			task.setDescription(description.getText().toString());
//			task.setDate(description.getText().toString());
			task.setCreated_at(Calendar.getInstance());
			task.setUpdated_at(Calendar.getInstance());
			
			if (SystemFunctions.isOnline(this)) {
				taskManager.setRestErrorHandler(myErrorHandler);
				String token = ApplicationFunctions.getInstance().getUserFunctions().getLoggedUser().getToken();
				MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
				map.set("task[user_id]", "" + task.getUser_id());
				map.set("task[title]", task.getTitle());
				map.set("task[description]", task.getDescription());
				map.set("task[date]", "2014-07-08T00:00:00.000Z");
				map.set("task[created_at]", ISO8601(task.getCreated_at()));
				map.set("task[updated_at]", ISO8601(task.getUpdated_at()));
				addTask(map, token);
			}
			else {
				Toast.makeText(this, "nincs net igy lokálba megy", Toast.LENGTH_SHORT).show();
				LocalDatabaseOpenHelper helper = new LocalDatabaseOpenHelper(this);
				SQLiteDatabase writableDatabase = helper.getWritableDatabase();
				insertTask(writableDatabase);
				writableDatabase.close();
			}
		}
	}
	
	@Background
	void addTask(MultiValueMap<String, String> formFields, String token) {
		taskManager.addTask(formFields, token);
	}
	
	@Transactional
	void insertTask(SQLiteDatabase db) {
		db.execSQL("INSERT INTO tasks (user_id, title, description, date, created_at, updated_at) " +
				"VALUES (?, ?, ?, ?, ?, ?);", new Object[]{task.getUser_id(), task.getTitle(), task.getDescription(), 
				task.getDate(), task.getCreated_at(), task.getUpdated_at()});
	}
	
}
