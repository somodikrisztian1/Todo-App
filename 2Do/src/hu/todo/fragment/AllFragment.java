package hu.todo.fragment;

import hu.todo.adapter.TodoAdapter;
import hu.todo.entity.Task;
import hu.todo.function.ApplicationFunctions;
import hu.todo.item.TodoItem;
import hu.todo.service.TaskRestInterface;
import hu.todo.toast.Toaster;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;

// megjeleníti az összes teendőt időrendben
@EFragment
public class AllFragment extends ListFragment {
	
	@Bean
	TodoAdapter adapter;
	
	@RestService
    TaskRestInterface taskManager; // Inject
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setRetainInstance(true);
	        Log.d("lol", "oncre");
	        if(ApplicationFunctions.getInstance().getUserFunctions().getLoginStatus()) {
	        	getItemsInBackground();
	        }
	        else {
	        	Toaster.loginWarning(getActivity()); // setretaininstance miatt a dialog sem fog ujraindulni
	        	        FragmentManager fm = getActivity().getSupportFragmentManager();
	        	        LoginDialogFragment_ editNameDialog = new LoginDialogFragment_();
	        	        editNameDialog.show(fm, "fragment_edit_name");
	        }
	    }
	 
	@AfterViews
    void binddAdapter() {
        setListAdapter(adapter);
    }
	
	
	@Background
    void getItemsInBackground() {
		String token = ApplicationFunctions.getInstance().getUserFunctions().getLoggedInUser().getToken();
        List<Task> tasks = taskManager.fetchAllTasks(token); // itt is kell majd ellenőrizni pl mivan ha elavult a session id
        showResult(tasks);
    }
 
 
    @UiThread
    void showResult(List<Task> tasks) {
    	ArrayList<TodoItem> items = adapter.getItems();
    	for(Task task : tasks) {
    		items.add(new TodoItem(task.getTitle(), task.getDescription(),
    				task.getDate().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()), 
    				task.getDate().get(Calendar.MONTH) + "/" + task.getDate().get(Calendar.DAY_OF_MONTH)));
    	}
    	adapter.notifyDataSetChanged();
    }

	
}
