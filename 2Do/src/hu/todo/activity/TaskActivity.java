package hu.todo.activity;

import hu.todo.R;
import hu.todo.rest.TaskRestInterface;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.rest.RestService;

import android.app.Activity;
import android.os.Bundle;

// ez a felülete egy task nak
@EActivity(R.layout.activity_add_task) 
public class TaskActivity extends Activity {
	
	// a teendő listapozíciója, aminek a részleteit mutatni fogja
	@Extra
	int todoItemPos;
	
	@RestService
    TaskRestInterface taskManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Click(R.id.btnCreate)
	void plusButtonWasClicked() {
		
	}
}
