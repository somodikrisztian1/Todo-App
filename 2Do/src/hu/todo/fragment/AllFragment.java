package hu.todo.fragment;

import hu.todo.adapter.TodoAdapter;
import hu.todo.entity.Task;
import hu.todo.service.TaskRestInterface;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

import android.os.Bundle;
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
	        getItemsInBackground();
	    }
	
	@AfterViews
    void binddAdapter() {
        setListAdapter(adapter);
    }

	@Background
    void getItemsInBackground() {
        List<Task> tasks = taskManager.fetchAllTasks("jsn38snakx84nd91jxamfnxbi1k24n8xcqnfkf92nfalaci12n");
        showResult(tasks);
    }
 
 
    @UiThread
    void showResult(List<Task> tasks) {
    	for(Task item : tasks)
    		Log.d("lol", item.toString());
 
    }
}
