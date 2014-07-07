package hu.todo.activity;

import java.util.Calendar;

import hu.todo.R;
import hu.todo.entity.Task;
import hu.todo.rest.TaskRestInterface;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import android.app.Activity;
import android.view.MenuItem;
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
	Task task;
	
	@RestService
    TaskRestInterface taskManager;
	
	@OptionsItem(android.R.id.home)
	void navigateBackOnHomePress(MenuItem item) {
		onBackPressed();
	}
	
	@AfterViews
	void afterViews() {
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
	} 
	
	@Click(R.id.btnCreate)
	void plusButtonWasClicked() {
		
	}
}
