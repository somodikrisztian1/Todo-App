package hu.todo.adapter;

import hu.todo.item.TodoItem;
import hu.todo.layout.TodoItemView;
import hu.todo.layout.TodoItemView_;

import java.util.ArrayList;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

// adapter a teendők listájához
@EBean
public class TodoAdapter extends BaseAdapter {
	
	ArrayList<TodoItem> items;
	@RootContext
	Context context;
	 
    public TodoAdapter(Context context) {
    	items = new ArrayList<TodoItem>();
    	CheckBox cb1 = new CheckBox(context);
    	cb1.setChecked(true);
    	CheckBox cb2 = new CheckBox(context);
    	cb1.setChecked(false);
    	items.add(new TodoItem(cb1, "1content1stRow", "1content2ndRow", "1date1stRow", "1date2ndRow"));
    	items.add(new TodoItem(cb2, "2content1stRow", "2content2ndRow", "2date1stRow", "2date2ndRow"));
    }
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public TodoItem getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TodoItemView todoItemView = null;
        if (convertView == null) {
            todoItemView = TodoItemView_.build(context);
        }
        else {
        	todoItemView = (TodoItemView) convertView;
        }
        todoItemView.bind(getItem(position));
        return todoItemView;
	}

}
