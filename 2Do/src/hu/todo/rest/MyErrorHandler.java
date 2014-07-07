package hu.todo.rest;

import org.androidannotations.annotations.EBean;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.web.client.RestClientException;

import android.util.Log;

@EBean
public class MyErrorHandler implements RestErrorHandler {
    @Override
    public void onRestClientExceptionThrown(RestClientException e) {
        Log.d("lol", "ex");
        e.printStackTrace();
    }
}
