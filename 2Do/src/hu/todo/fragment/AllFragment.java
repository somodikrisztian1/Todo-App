package hu.todo.fragment;

import hu.todo.activity.TaskActivity_;
import hu.todo.adapter.TodoAdapter;
import hu.todo.entity.Task;
import hu.todo.function.ApplicationFunctions;
import hu.todo.rest.MyErrorHandler;
import hu.todo.rest.TaskRestInterface;
import hu.todo.toast.Toaster;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
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
	
	@Bean
	MyErrorHandler myErrorHandler;
	
	@RestService
    TaskRestInterface taskManager; // Inject
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setRetainInstance(true);
	        if(ApplicationFunctions.getInstance().getUserFunctions().getLoginStatus()) {
	        	getItemsInBackground();
	        	Log.d("lol", "utana");
	        }
	        else {
	        	Toaster.loginWarning(getActivity()); // setretaininstance miatt a dialog sem fog ujraindulni
    	        FragmentManager fm = getActivity().getSupportFragmentManager();
    	        LoginDialogFragment_ editNameDialog = new LoginDialogFragment_();
    	        editNameDialog.show(fm, "dialog_login");
	        }
	    }
	 
	@AfterViews
    void binddAdapter() {
        setListAdapter(adapter);
    }
	
	@ItemClick(android.R.id.list)
    public void myListItemClicked(Task task) {
		TaskActivity_.intent(this).task(task).start();
    }
	
	
	@Background
    void getItemsInBackground() {
		String token = ApplicationFunctions.getInstance().getUserFunctions().getLoggedUser().getToken();
		taskManager.setRestErrorHandler(myErrorHandler);
//		Task task = new Task();
//		task.setCreated_at(Calendar.getInstance());
//		task.setDate(Calendar.getInstance());
//		task.setDescription("jolesz");
//		task.setId(2);
//		task.setTitle("CYbeugro");
//		task.setUpdated_at(Calendar.getInstance());
//		task.setUser_id(3);
//		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
//		map.set("task[user_id]", "3");
//		map.set("task[title]", "CYfeladat");
//		map.set("task[description]", "jolesz");
//		map.set("task[date]", "2014-07-08T00:00:00.000Z");
//		taskManager.addTask(map, token);
        List<Task> tasks = taskManager.getAllTask(token); // itt is kell majd ellenőrizni pl mivan ha elavult a session id
        showResult(tasks);
    }
 
 
    @UiThread
    void showResult(List<Task> tasks) {
    	if(tasks != null) {
	    	ArrayList<Task> items = adapter.getItems();
	    	for(Task task : tasks) {
	    		items.add(task);
	    	}
	    	adapter.notifyDataSetChanged();
    	} //TODO vmi üzenet
    }

	
}