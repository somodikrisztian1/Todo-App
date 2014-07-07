package hu.todo.layout;

import hu.todo.R;
import hu.todo.item.TodoItem;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

// a teendőt leíró nézet
@EViewGroup(R.layout.list_item_todo)
public class TodoItemView extends LinearLayout {
	
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

	public void bind(TodoItem todoItem) {
		txtContent1stRow.setText(todoItem.getTitle());
		txtContent2ndRow.setText(todoItem.getDescription());
		txtDate1stRow.setText(todoItem.getDay());
		txtDate2ndRow.setText(todoItem.getDate());
	}
	
}
