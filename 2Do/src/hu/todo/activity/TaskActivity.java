package hu.todo.activity;

import hu.todo.R;
import hu.todo.entity.Task;
import hu.todo.rest.TaskRestInterface;

import java.util.Calendar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

// ez a felülete egy task nak új hozzáadásakor és már meglévő megtekintésekor is
@EActivity(R.layout.activity_task) 
@OptionsMenu(R.menu.menu_task)
public class TaskActivity extends Activity {
	
	@ViewById
	TextView title;
	@ViewById
	EditText description;  // emiatt megmarad az erteke?
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
	@ViewById
	Button btnCreate;
	
	// a teendő listaelem amire kattintás történt
	@Extra
	Task task;
	
	// az értéke dönt arrol h shoWTask (true) vagy addTask (false)
	@Extra
	boolean isShowTask;
	
	@InstanceState
	boolean isEdit = false;
	
	@RestService
    TaskRestInterface taskManager;
	
	@OptionsMenuItem(R.id.edit)
    MenuItem editMenuItem;
	
	@OptionsMenuItem(R.id.save)
    MenuItem saveMenuItem;
	
	@OptionsItem(android.R.id.home)
	void navigateBackOnHomePress() {
		onBackPressed();
	}
	
	@OptionsItem(R.id.edit)
	void editTask() {
		setViewsFocusable();
		isEdit = true;
	}
	
	@OptionsItem(R.id.save)
	void saveTask() {
		setViewsUnFocusable();
		isEdit = false;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState != null) {
			isEdit = savedInstanceState.getBoolean("isEdit");
		}
		Log.d("lol", isEdit == true ? "true" : "false");
		Log.d("lol", isShowTask == true ? "truee" : "falsee");
	}
	
	@AfterViews
	void afterViews() {
		if(isShowTask) { 
			getActionBar().setDisplayHomeAsUpEnabled(true);
			title.setText(task.getTitle());
			description.setText(task.getDescription());
			datePicker.setText(task.getDate().get(Calendar.YEAR) + 
					" / " + (task.getDate().get(Calendar.MONTH) + 1) + 
					" / " + task.getDate().get(Calendar.DAY_OF_MONTH));
			createdPicker.setText(task.getCreated_at().get(Calendar.YEAR) + 
					" / " + (task.getCreated_at().get(Calendar.MONTH) + 1 )+ 
					" / " + task.getCreated_at().get(Calendar.DAY_OF_MONTH));
			updatedPicker.setText(task.getUpdated_at().get(Calendar.YEAR) + 
					" / " + (task.getUpdated_at().get(Calendar.MONTH) + 1 ) + 
					" / " + task.getUpdated_at().get(Calendar.DAY_OF_MONTH));
			btnCreate.setVisibility(View.GONE);
			if(isEdit) {
				setViewsFocusable();
			}
		}
		else{
			btnCreate.setVisibility(View.VISIBLE);
		}
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
	
	@Click(R.id.btnCreate)
	void plusButtonWasClicked() {
		
	}
}
