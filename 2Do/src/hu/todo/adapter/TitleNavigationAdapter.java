package hu.todo.adapter;

import hu.todo.item.SpinnerNavItem;
import hu.todo.layout.NavListItemView;
import hu.todo.layout.NavListItemView_;
import hu.todo.layout.NavTitleItemView;
import hu.todo.layout.NavTitleItemView_;

import java.util.ArrayList;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

// adapter a spinnerhez
@EBean
public class TitleNavigationAdapter extends BaseAdapter {

	ArrayList<SpinnerNavItem> navSpinner;
	@RootContext
	Context context;

	public TitleNavigationAdapter(Context context) {
		navSpinner = new ArrayList<SpinnerNavItem>();
		navSpinner.add(new SpinnerNavItem("Mind", "időrendben az összes teendő"));
		navSpinner.add(new SpinnerNavItem("Csoport lista", "felhasználók szerint csoportosítva"));
	}

	@Override
	public int getCount() {
		return navSpinner.size();
	}

	@Override
	public SpinnerNavItem getItem(int position) {
		return navSpinner.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// azt a nézetet adja vissza, ami a spinner címében látszik
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NavTitleItemView navItemView;
		if (convertView == null) {
			navItemView = NavTitleItemView_.build(context);
		} else {
			navItemView = (NavTitleItemView) convertView;
		}
		navItemView.bind(getItem(position));
		return navItemView;
	}

	// azt a nézetet adja vissza, ami a spinner leugró nézetében látszik
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		NavListItemView navListItemView;
		if (convertView == null) {
			navListItemView = NavListItemView_.build(context);
		} else {
			navListItemView = (NavListItemView) convertView;
		}
		navListItemView.bind(getItem(position));
		return navListItemView;
	}

}
