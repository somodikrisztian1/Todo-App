package hu.todo.layout;

import hu.todo.R;
import hu.todo.entity.Task;

import java.util.Calendar;
import java.util.Locale;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 *	a teendőt leíró nézet 
 */
@EViewGroup(R.layout.list_item_todo)
public class TodoItemView extends LinearLayout {
	
	@ViewById
	ImageView todoImage;
	
	@ViewById
	TextView txtContent1stRow;
	
	@ViewById
	TextView txtContent2ndRow;
	
	@ViewById
	TextView txtDate1stRow;
	
	@ViewById
	TextView txtDate2ndRow;
	
	public TodoItemView(Context context) {
		super(context);
	}

	public void bind(Task task) {
		if(task.isLocal()) {
			todoImage.setImageResource(R.drawable.local_mode_icon);
		}
		txtContent1stRow.setText(task.getTitle());
		txtContent2ndRow.setText(task.getDescription());
		txtDate1stRow.setText(task.getDate().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()));
		txtDate2ndRow.setText((task.getDate().get(Calendar.MONTH) + 1) + "/" + task.getDate().get(Calendar.DAY_OF_MONTH));
	}
	
}
