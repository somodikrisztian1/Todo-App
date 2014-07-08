package hu.todo.sharedprefs;

import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref
public interface MyPrefs {

    @DefaultString("N/A")
    String token();

}
