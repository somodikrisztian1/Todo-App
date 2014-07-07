package hu.todo.adapter;

import hu.todo.entity.Task;
import hu.todo.layout.TodoItemView;
import hu.todo.layout.TodoItemView_;

import java.util.ArrayList;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

// adapter a teendők listájához
@EBean
public class TodoAdapter extends BaseAdapter {
	
	ArrayList<Task> items;
	
	@RootContext
	Context context;
	
	public ArrayList<Task> getItems() {
		return items;
	}
	 
    public TodoAdapter(Context context) {
    	items = new ArrayList<Task>();
    }
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Task getItem(int position) {
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
