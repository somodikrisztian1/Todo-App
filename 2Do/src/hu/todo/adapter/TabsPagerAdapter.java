package hu.todo.adapter;

import hu.todo.fragment.AllFragment_;
import hu.todo.fragment.GroupedByUserFragment_;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

// adapter a viewpagerhez, ami belehelyezi a fragmenteket
public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return new AllFragment_();
		case 1:
			return new GroupedByUserFragment_();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 2;
	}

}
