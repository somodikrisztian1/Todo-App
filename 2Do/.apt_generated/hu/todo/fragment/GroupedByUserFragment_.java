//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package hu.todo.fragment;

import java.util.List;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import hu.todo.adapter.TodoAdapter_;
import hu.todo.rest.MyErrorHandler_;
import hu.todo.rest.RestInterface_;
import org.androidannotations.api.BackgroundExecutor;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class GroupedByUserFragment_
    extends GroupedByUserFragment
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    private View contentView_;
    private Handler handler_ = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
    }

    public View findViewById(int id) {
        if (contentView_ == null) {
            return null;
        }
        return contentView_.findViewById(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        return contentView_;
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        taskManager = new RestInterface_();
        myErrorHandler = MyErrorHandler_.getInstance_(getActivity());
        adapter = TodoAdapter_.getInstance_(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static GroupedByUserFragment_.FragmentBuilder_ builder() {
        return new GroupedByUserFragment_.FragmentBuilder_();
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        {
            AdapterView<?> view = ((AdapterView<?> ) hasViews.findViewById(android.R.id.list));
            if (view!= null) {
                view.setOnItemClickListener(new OnItemClickListener() {


                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        GroupedByUserFragment_.this.myListItemClicked(((hu.todo.entity.Task) parent.getAdapter().getItem(position)));
                    }

                }
                );
            }
        }
        binddAdapter();
    }

    @Override
    public void putsome(final SQLiteDatabase db) {
        db.beginTransaction();
        try {
            GroupedByUserFragment_.super.putsome(db);
            db.setTransactionSuccessful();
            return ;
        } catch (RuntimeException e) {
            Log.e("GroupedByUserFragment_", "Error in transaction", e);
            throw e;
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void getLocalTasks(final SQLiteDatabase db) {
        db.beginTransaction();
        try {
            GroupedByUserFragment_.super.getLocalTasks(db);
            db.setTransactionSuccessful();
            return ;
        } catch (RuntimeException e) {
            Log.e("GroupedByUserFragment_", "Error in transaction", e);
            throw e;
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void showDialog() {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                GroupedByUserFragment_.super.showDialog();
            }

        }
        );
    }

    @Override
    public void dismissDialog() {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                GroupedByUserFragment_.super.dismissDialog();
            }

        }
        );
    }

    @Override
    public void showResult(final List<hu.todo.entity.Task> tasks) {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                GroupedByUserFragment_.super.showResult(tasks);
            }

        }
        );
    }

    @Override
    public void getItemsInBackground() {
        BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0, "") {


            @Override
            public void execute() {
                try {
                    GroupedByUserFragment_.super.getItemsInBackground();
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }

        }
        );
    }

    public static class FragmentBuilder_ {

        private Bundle args_;

        private FragmentBuilder_() {
            args_ = new Bundle();
        }

        public GroupedByUserFragment build() {
            GroupedByUserFragment_ fragment_ = new GroupedByUserFragment_();
            fragment_.setArguments(args_);
            return fragment_;
        }

    }

}
