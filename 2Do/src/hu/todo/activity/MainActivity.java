package hu.todo.activity;

import hu.todo.R;
import hu.todo.adapter.TabsPagerAdapter;
import hu.todo.adapter.TitleNavigationAdapter;
import hu.todo.entity.Task;
import hu.todo.fragment.AllFragment;
import hu.todo.function.ApplicationFunctions;
import hu.todo.rest.MyErrorHandler;
import hu.todo.rest.RestInterface;
import hu.todo.utility.CalendarFormatter;
import hu.todo.utility.LocalDatabaseOpenHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.Transactional;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MenuItem;

// a fő activity, amely egy viewpagert tartalmaz
@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_activity_main)
public class MainActivity extends FragmentActivity implements OnNavigationListener, OnPageChangeListener {
	
	@ViewById(R.id.pager)
	public ViewPager viewPager;
	
	@Bean
	MyErrorHandler myErrorHandler;

	@RestService
	RestInterface taskManager;
	
	@Bean
	TitleNavigationAdapter adapter;
	
	@OptionsMenuItem(R.id.action_refresh)
    MenuItem refreshMenuItem;
	
	@OptionsItem(R.id.action_refresh)
	void menuRefresh(MenuItem item) {
		refresh();
	}
	
	@OptionsItem(R.id.action_new)
	void menuAddTask(MenuItem item) {
		addTask();
	}
	
    ActionBar actionBar;
    AllFragment frag;
    
	@AfterViews
    void init() {
    	actionBar = getActionBar();
    	TabsPagerAdapter mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);        
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setListNavigationCallbacks(adapter, this);
    	viewPager.setAdapter(mAdapter);
    	viewPager.setOnPageChangeListener(this);
    }
    
	void addTask() {
		AddTaskActivity_.intent(this).start();
	}
	
    // frissítés
    @Background
    void refresh() {
    	onPreExecute();
    	
    	LocalDatabaseOpenHelper helper = new LocalDatabaseOpenHelper(this);
    	SQLiteDatabase writableDatabase = helper.getWritableDatabase();
    	taskManager.setRestErrorHandler(myErrorHandler);
    	frag = (AllFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.pager+":"+viewPager.getCurrentItem()); 
    	for(Task task : frag.adapter.getItems()) {
    		if(task.isLocal()) {
    			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
				map.set("task[user_id]", "" + task.getUser_id());
				map.set("task[title]", task.getTitle());
				map.set("task[description]", task.getDescription());
				map.set("task[date]", CalendarFormatter.ISO8601(task.getDate()));
						
				map.set("task[created_at]", CalendarFormatter.ISO8601(task.getCreated_at()));
				map.set("task[updated_at]", CalendarFormatter.ISO8601(task.getUpdated_at()));
				String token = ApplicationFunctions.getInstance().getUserFunctions().getLoggedUser().getToken();
				addTask(map, token);
				deleteLocal(writableDatabase, task.getId());
    		}
    	}
    	
        
    	onPostExecute();
    }
    
    @Transactional
    void deleteLocal(SQLiteDatabase db, int id) {
    	db.delete("tasks", "id=?", new String[]{"" + id});
    }
    
    @UiThread
    void addTask(MultiValueMap<String, String> formFields, String token) {
    	taskManager.addTask(formFields, token);
    }
    
    @UiThread
    void onPreExecute() {
        refreshMenuItem.setActionView(R.layout.action_progressbar);
        refreshMenuItem.expandActionView();
    }
    
    @UiThread
    void onPostExecute() {
    	refreshMenuItem.collapseActionView();
        refreshMenuItem.setActionView(null);
        frag.adapter.notifyDataSetChanged();
    }

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		viewPager.setCurrentItem(itemPosition, true);
		return true;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int position) {
		actionBar.setSelectedNavigationItem(position);
	}    
	
}
