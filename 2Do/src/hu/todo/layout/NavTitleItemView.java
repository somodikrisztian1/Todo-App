package hu.todo.layout;

import hu.todo.R;
import hu.todo.item.SpinnerNavItem;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;


// spinner címét leíró nézet
@EViewGroup(R.layout.list_item_title_navigation)
public class NavTitleItemView extends LinearLayout {

	@ViewById(R.id.txtTitle)
	TextView titleView;
	
	public NavTitleItemView(Context context) {
		super(context);
	}

	public void bind(SpinnerNavItem navItem) {
        titleView.setText(navItem.getTitle());
    }
	
}
