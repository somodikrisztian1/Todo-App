package hu.todo.rest;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

// kerest lehet vele vizsgalni
public class HttpBasicAuthenticatorInterceptor implements
		ClientHttpRequestInterceptor {
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] data,
			ClientHttpRequestExecution execution) throws IOException {
		return execution.execute(request, data);
	}
}
