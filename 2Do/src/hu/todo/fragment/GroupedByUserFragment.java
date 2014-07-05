package hu.todo.fragment;

import hu.todo.adapter.TodoAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import android.support.v4.app.ListFragment;

// megjeleníti az összes teendőt felhasználókra bontva
@EFragment
public class GroupedByUserFragment extends ListFragment {
	
	@Bean
	TodoAdapter adapter;
	
	@AfterViews
    void binddAdapter() {
        setListAdapter(adapter);
    }
	
}
