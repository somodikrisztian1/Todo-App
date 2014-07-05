package hu.todo.activity;

import hu.todo.R;
import hu.todo.adapter.TabsPagerAdapter;
import hu.todo.adapter.TitleNavigationAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MenuItem;

// a fő activity, amely egy viewpagert tartalmaz
@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.activity_main_actions)
public class MainActivity extends FragmentActivity implements OnNavigationListener, OnPageChangeListener {

	@ViewById(R.id.pager)
	ViewPager viewPager;

	@Bean
	TitleNavigationAdapter adapter;
	
	@OptionsMenuItem(R.id.action_refresh)
    MenuItem refreshMenuItem;
	
	@OptionsItem(R.id.action_refresh)
	void menuRefresh(MenuItem item) {
		refresh();
	}
	 
    ActionBar actionBar;

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
    
    // frissítés
    @Background
    void refresh() {
    	onPreExecute();
    	try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	onPostExecute();
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
