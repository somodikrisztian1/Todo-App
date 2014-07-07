package hu.todo.rest;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import android.util.Log;


// kerest lehet vele vizsgalni
public class HttpBasicAuthenticatorInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] data, ClientHttpRequestExecution execution) throws IOException {
        Log.d("lol", "fej: " + request.getURI() 
        		+ " " + request.getHeaders());
        return execution.execute(request, data);
    }
}
