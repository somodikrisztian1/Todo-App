package hu.todo.activity;

import hu.todo.R;
import hu.todo.entity.Task;
import hu.todo.entity.User;
import hu.todo.function.ApplicationFunctions;
import hu.todo.rest.MyErrorHandler;
import hu.todo.rest.RestInterface;

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
import org.androidannotations.annotations.FocusChange;
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
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

// ez a felülete egy task nak, amikor a listábol kattintva jutunk ide
@EActivity(R.layout.activity_show_task) 
@OptionsMenu(R.menu.menu_show_task)
public class ShowTaskActivity extends Activity implements OnItemClickListener {
	
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
	
	@InstanceState
	boolean isEdit = false;
	
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
		if(!isEdit) {
			setViewsFocusable();
			isEdit = true;
		}
	}

	private String ISO8601(Calendar c) {
		String s = ISO8601.format(c.getTime());
		return (s.substring(0, 22)+":"+s.substring(22));
	}
	
	private final SimpleDateFormat ISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
	
	@OptionsItem(R.id.save)
	void saveTask() {
		if(isEdit) {
			setViewsUnFocusable();
			isEdit = false;
			taskManager.setRestErrorHandler(myErrorHandler);
			Log.d("lol", "iddd: " + selectedUser.getId());
			String token = ApplicationFunctions.getInstance().getUserFunctions().getLoggedUser().getToken();
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			Calendar now = Calendar.getInstance();   // TODO updated mezot meg a createdet le lehet tiltani
			map.set("task[user_id]", "" + (selectedUser != null ? selectedUser.getId() : task.getUser_id()));  // TODO
			map.set("task[title]", "" + title.getText());
			map.set("task[description]", desc);
			map.set("task[date]", ISO8601(now));  // TODO a pickerbol majd
			map.set("task[created_at]", ISO8601(task.getCreated_at()));
			map.set("task[updated_at]", ISO8601(now));
			updateTask(map, token);
		}
	}
	
	@Background
	void updateTask(MultiValueMap<String, String> map, String token) {
		taskManager.updateTask(map, task.getId(), token);  // TODO kf
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null) {
			instanceState = savedInstanceState;
			isEdit = savedInstanceState.getBoolean("isEdit");
		}
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@AfterViews
	void afterViews() {
			getActionBar().setDisplayHomeAsUpEnabled(true);
			title.setText(task.getTitle());
			description.setText( instanceState != null && instanceState.getString("desc") != null ? instanceState.getString("desc") : task.getDescription());
			datePicker.setText(instanceState != null && instanceState.getString("dateP") != null ? instanceState.getString("dateP") : (task.getDate().get(Calendar.YEAR) + 
					" / " + (task.getDate().get(Calendar.MONTH) + 1) + 
					" / " + task.getDate().get(Calendar.DAY_OF_MONTH)));
			createdPicker.setText(instanceState != null && instanceState.getString("createdP") != null ? instanceState.getString("createdP") : (task.getCreated_at().get(Calendar.YEAR) + 
					" / " + (task.getCreated_at().get(Calendar.MONTH) + 1 )+ 
					" / " + task.getCreated_at().get(Calendar.DAY_OF_MONTH)));
			updatedPicker.setText(instanceState != null && instanceState.getString("updatedP") != null ? instanceState.getString("updatedP") : (task.getUpdated_at().get(Calendar.YEAR) + 
					" / " + (task.getUpdated_at().get(Calendar.MONTH) + 1 ) + 
					" / " + task.getUpdated_at().get(Calendar.DAY_OF_MONTH)));
			
			if(isEdit) {
				setViewsFocusable();
			}
			
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
	
	void setViewsFocusable() {
		description.setFocusableInTouchMode(true);
		user.setFocusableInTouchMode(true);
		user.setEnabled(true);
		datePicker.setFocusableInTouchMode(true);
		createdPicker.setFocusableInTouchMode(true);
		updatedPicker.setFocusableInTouchMode(true);
	}
	
	void setViewsUnFocusable(){
		description.setFocusable(false);
		user.setFocusable(false);
		user.setEnabled(false);
		datePicker.setFocusable(false);
		createdPicker.setFocusable(false);
		updatedPicker.setFocusable(false);
	}
	
	@Background
	void getUsers(String token) {
		 allUser = taskManager.getAllUser(token);
		 setAuCompleteUser();
		 Log.d("lol", "itt jarsz?");
	}
	
	@UiThread
	void setAuCompleteUser() {
		if(allUser != null) {
			ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, android.R.layout.simple_dropdown_item_1line, allUser);
			 user.setAdapter(adapter);
			 user.setOnItemClickListener(this);
			 String owner = null;
			 for(User u : allUser) {
				 if(u.getId() == task.getUser_id()) {
					 owner = u.getName();
				 }
			 }
			 if(owner != null) {
				 user.setText(owner);
			 }
		}
	}

	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		selectedUser = (User) parent.getItemAtPosition(position);
		Log.d("lol", "" + selectedUser.getId());
	}
	
	
}
