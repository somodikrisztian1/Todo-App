package hu.todo.activity;

import hu.todo.R;
import hu.todo.entity.Task;
import hu.todo.function.ApplicationFunctions;
import hu.todo.rest.MyErrorHandler;
import hu.todo.rest.TaskRestInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

// ez a felülete egy task nak, amikor a listábol kattintva jutunk ide
@EActivity(R.layout.activity_show_task) 
@OptionsMenu(R.menu.menu_show_task)
public class ShowTaskActivity extends Activity {
	
	@ViewById
	TextView title;
	@ViewById
	EditText description; 
	@ViewById
	MultiAutoCompleteTextView user;
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
    TaskRestInterface taskManager;
	
	@Bean
	MyErrorHandler myErrorHandler;
	
	@OptionsMenuItem(R.id.edit)
    MenuItem editMenuItem;
	
	@OptionsMenuItem(R.id.save)
    MenuItem saveMenuItem;
	private Bundle instanceState;
	
	@OptionsItem(android.R.id.home)
	void navigateBackOnHomePress() {
		onBackPressed();
	}
	
	@OptionsItem(R.id.edit)
	void editTask() {
		setViewsFocusable();
		isEdit = true;
	}

	private String ISO8601(Calendar c) {
		String s = ISO8601.format(c.getTime());
		return (s.substring(0, 22)+":"+s.substring(22));
	}
	
	private final SimpleDateFormat ISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
	
	@OptionsItem(R.id.save)
	void saveTask() {
		setViewsUnFocusable();
		isEdit = false;
		taskManager.setRestErrorHandler(myErrorHandler);
		
		String token = ApplicationFunctions.getInstance().getUserFunctions().getLoggedUser().getToken();
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		Calendar now = Calendar.getInstance();   // TODO updated mezot meg a createdet le lehet tiltani
		map.set("task[user_id]", "" + ApplicationFunctions.getInstance().getUserFunctions().getLoggedUser().getId());
		map.set("task[title]", "" + title.getText());
		map.set("task[description]", desc);
		map.set("task[date]", ISO8601(now));  // TODO a pickerbol majd
		map.set("task[created_at]", ISO8601(task.getCreated_at()));
		map.set("task[updated_at]", ISO8601(now));
		updateTask(map, token);
	}
	
	@Background
	void updateTask(MultiValueMap<String, String> map, String token) {
		taskManager.updateTask(map, task.getId(), token);
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
			
			Log.d("lol", updatedPicker.getText().toString());
			if(isEdit) {
				setViewsFocusable();
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
		datePicker.setFocusableInTouchMode(true);
		createdPicker.setFocusableInTouchMode(true);
		updatedPicker.setFocusableInTouchMode(true);
	}
	
	void setViewsUnFocusable(){
		description.setFocusable(false);
		user.setFocusable(false);
		datePicker.setFocusable(false);
		createdPicker.setFocusable(false);
		updatedPicker.setFocusable(false);
	}

}
