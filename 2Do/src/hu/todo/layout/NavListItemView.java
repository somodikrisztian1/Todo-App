package hu.todo.layout;

import hu.todo.R;
import hu.todo.item.SpinnerNavItem;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

// spinner leugró listájának nézetét írja le
// így kiváltható AA -ban a View Holder minta
@EViewGroup(R.layout.list_item_navigation)
public class NavListItemView extends LinearLayout {

	@ViewById
	TextView txtListTitle;

	@ViewById
	TextView txtListSize;

	public NavListItemView(Context context) {
		super(context);
	}

	public void bind(SpinnerNavItem navItem) {
		txtListTitle.setText(navItem.getTitle());
		txtListSize.setText(navItem.getSize());
	}

}
