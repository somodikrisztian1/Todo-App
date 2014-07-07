package hu.todo.activity;

import hu.todo.R;
import hu.todo.item.TodoItem;
import hu.todo.rest.TaskRestInterface;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

// ez a felülete egy task nak
@EActivity(R.layout.activity_task) 
public class TaskActivity extends Activity {
	
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
	@ViewById
	Button btnCreate;
	
	// a teendő listaelem amire kattintás történt
	@Extra
	TodoItem todoItem;
	
	@RestService
    TaskRestInterface taskManager;
	
	@AfterViews
	void afterViews() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		title.setText(todoItem.getTitle());
		description.setText(todoItem.getDescription());
		datePicker.setText(todoItem.getDate());
	}
	
	@Click(R.id.btnCreate)
	void plusButtonWasClicked() {
		
	}
}
