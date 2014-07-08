//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package hu.todo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import hu.todo.R.layout;
import hu.todo.rest.MyErrorHandler_;
import hu.todo.rest.RestInterface_;
import org.androidannotations.api.BackgroundExecutor;
import org.androidannotations.api.SdkVersionHelper;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;
import org.springframework.util.MultiValueMap;

public final class ShowTaskActivity_
    extends ShowTaskActivity
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    public final static String TASK_EXTRA = "task";
    private Handler handler_ = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.activity_show_task);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        injectExtras_();
        taskManager = new RestInterface_();
        myErrorHandler = MyErrorHandler_.getInstance_(this);
        restoreSavedInstanceState_(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static ShowTaskActivity_.IntentBuilder_ intent(Context context) {
        return new ShowTaskActivity_.IntentBuilder_(context);
    }

    public static ShowTaskActivity_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new ShowTaskActivity_.IntentBuilder_(fragment);
    }

    public static ShowTaskActivity_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new ShowTaskActivity_.IntentBuilder_(supportFragment);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (((SdkVersionHelper.getSdkInt()< 5)&&(keyCode == KeyEvent.KEYCODE_BACK))&&(event.getRepeatCount() == 0)) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        description = ((EditText) hasViews.findViewById(hu.todo.R.id.description));
        title = ((TextView) hasViews.findViewById(hu.todo.R.id.title));
        user = ((AutoCompleteTextView) hasViews.findViewById(hu.todo.R.id.user));
        createdPicker = ((EditText) hasViews.findViewById(hu.todo.R.id.createdPicker));
        updated = ((TextView) hasViews.findViewById(hu.todo.R.id.updated));
        updatedPicker = ((EditText) hasViews.findViewById(hu.todo.R.id.updatedPicker));
        date = ((TextView) hasViews.findViewById(hu.todo.R.id.date));
        timePicker = ((EditText) hasViews.findViewById(hu.todo.R.id.timePicker));
        created = ((TextView) hasViews.findViewById(hu.todo.R.id.created));
        datePicker = ((EditText) hasViews.findViewById(hu.todo.R.id.datePicker));
        {
            View view = hasViews.findViewById(hu.todo.R.id.datePicker);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ShowTaskActivity_.this.showDatePicker();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(hu.todo.R.id.timePicker);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ShowTaskActivity_.this.showTimePicker();
                    }

                }
                );
            }
        }
        {
            final TextView view = ((TextView) hasViews.findViewById(hu.todo.R.id.description));
            if (view!= null) {
                view.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        ShowTaskActivity_.this.descChanged();
                    }

                }
                );
            }
        }
        {
            final TextView view = ((TextView) hasViews.findViewById(hu.todo.R.id.updatedPicker));
            if (view!= null) {
                view.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        ShowTaskActivity_.this.updatedPChanged();
                    }

                }
                );
            }
        }
        {
            final TextView view = ((TextView) hasViews.findViewById(hu.todo.R.id.timePicker));
            if (view!= null) {
                view.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        ShowTaskActivity_.this.timePChanged();
                    }

                }
                );
            }
        }
        {
            final TextView view = ((TextView) hasViews.findViewById(hu.todo.R.id.datePicker));
            if (view!= null) {
                view.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        ShowTaskActivity_.this.datePChanged();
                    }

                }
                );
            }
        }
        {
            final TextView view = ((TextView) hasViews.findViewById(hu.todo.R.id.createdPicker));
            if (view!= null) {
                view.addTextChangedListener(new TextWatcher() {


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        ShowTaskActivity_.this.createdPChanged();
                    }

                }
                );
            }
        }
        afterViews();
    }

    private void injectExtras_() {
        Bundle extras_ = getIntent().getExtras();
        if (extras_!= null) {
            if (extras_.containsKey(TASK_EXTRA)) {
                task = extras_.getParcelable(TASK_EXTRA);
            }
        }
    }

    @Override
    public void setIntent(Intent newIntent) {
        super.setIntent(newIntent);
        injectExtras_();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(hu.todo.R.menu.menu_show_task, menu);
        editMenuItem = menu.findItem(hu.todo.R.id.edit);
        saveMenuItem = menu.findItem(hu.todo.R.id.save);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = super.onOptionsItemSelected(item);
        if (handled) {
            return true;
        }
        int itemId_ = item.getItemId();
        if (itemId_ == android.R.id.home) {
            navigateBackOnHomePress();
            return true;
        }
        if (itemId_ == hu.todo.R.id.save) {
            saveTask();
            return true;
        }
        if (itemId_ == hu.todo.R.id.edit) {
            editTask();
            return true;
        }
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("dateP", dateP);
        bundle.putString("dateT", dateT);
        bundle.putString("updatedP", updatedP);
        bundle.putString("desc", desc);
        bundle.putBoolean("isEdit", isEdit);
        bundle.putString("createdP", createdP);
    }

    private void restoreSavedInstanceState_(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return ;
        }
        dateP = savedInstanceState.getString("dateP");
        dateT = savedInstanceState.getString("dateT");
        updatedP = savedInstanceState.getString("updatedP");
        desc = savedInstanceState.getString("desc");
        isEdit = savedInstanceState.getBoolean("isEdit");
        createdP = savedInstanceState.getString("createdP");
    }

    @Override
    public void setAuCompleteUser() {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                ShowTaskActivity_.super.setAuCompleteUser();
            }

        }
        );
    }

    @Override
    public void getUsers(final String token) {
        BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0, "") {


            @Override
            public void execute() {
                try {
                    ShowTaskActivity_.super.getUsers(token);
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }

        }
        );
    }

    @Override
    public void updateTask(final MultiValueMap<String, String> map, final String token) {
        BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0, "") {


            @Override
            public void execute() {
                try {
                    ShowTaskActivity_.super.updateTask(map, token);
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }

        }
        );
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;
        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, ShowTaskActivity_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            fragment_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, ShowTaskActivity_.class);
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            fragmentSupport_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, ShowTaskActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public ShowTaskActivity_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

        public void startForResult(int requestCode) {
            if (fragmentSupport_!= null) {
                fragmentSupport_.startActivityForResult(intent_, requestCode);
            } else {
                if (fragment_!= null) {
                    fragment_.startActivityForResult(intent_, requestCode);
                } else {
                    if (context_ instanceof Activity) {
                        ((Activity) context_).startActivityForResult(intent_, requestCode);
                    } else {
                        context_.startActivity(intent_);
                    }
                }
            }
        }

        public ShowTaskActivity_.IntentBuilder_ task(hu.todo.entity.Task task) {
            intent_.putExtra(TASK_EXTRA, task);
            return this;
        }

    }

}
