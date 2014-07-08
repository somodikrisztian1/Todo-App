package hu.todo.fragment;

import hu.todo.activity.ShowTaskActivity_;
import hu.todo.adapter.TodoAdapter;
import hu.todo.entity.Task;
import hu.todo.function.ApplicationFunctions;
import hu.todo.rest.MyErrorHandler;
import hu.todo.rest.RestInterface;
import hu.todo.toast.Toaster;
import hu.todo.utility.LocalDatabaseOpenHelper;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.Transactional;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    RestInterface taskManager; // Inject
	
	@Transactional
	void putsome(SQLiteDatabase db) {
//		db.execSQL("INSERT INTO tasks (id, user_id, title) VALUES (1, 3, 'lol');");
	}
	
	@Transactional
	void getsome(SQLiteDatabase db) {
//		Cursor c = db.query("tasks", new String[]{"title"}, null, null, null, null, null);
//		c.moveToFirst();
//		String string = c.getString(0);
//		Log.d("lol", string);
	}
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setRetainInstance(true);
	        if(ApplicationFunctions.getInstance().getUserFunctions().getLoginStatus()) {
	        	getItemsInBackground();
	        	LocalDatabaseOpenHelper helper = new LocalDatabaseOpenHelper(getActivity());
	        	SQLiteDatabase writableDatabase = helper.getWritableDatabase();
	        	putsome(writableDatabase);
	        	getsome(writableDatabase);
	        	writableDatabase.close();
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
		ShowTaskActivity_.intent(this).task(task).start();
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