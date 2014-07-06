package hu.todo.rest;

import hu.todo.entity.Task;
import hu.todo.entity.User;

import java.util.List;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


// Task REST kommunikációja rooturl t majd
@Rest(rootUrl = "http://37.139.18.133", converters = { MappingJacksonHttpMessageConverter.class}, interceptors = { HttpBasicAuthenticatorInterceptor.class }) 
@Accept(MediaType.APPLICATION_JSON)
public interface TaskRestInterface extends RestClientErrorHandling { 
	 
	RestTemplate getRestTemplate();
	void setRestTemplate(RestTemplate restTemplate);
	
	 
	 @Get("/tasks/?token={token}")  
	 List<Task> fetchAllTasks(String token);
	 
	 @Post("/sessions/?email={email}&password={password}")
	 User login(String email, String password);
}
