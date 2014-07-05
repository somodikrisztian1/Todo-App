package hu.todo.service;

import hu.todo.entity.Task;

import java.util.List;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;


// Task REST kommunikációja
@Rest(converters = { MappingJacksonHttpMessageConverter.class}, interceptors = { HttpBasicAuthenticatorInterceptor.class }) 
@Accept(MediaType.APPLICATION_JSON)
public interface TaskRestInterface { 
	 RestTemplate getrRestTemplate();
	 
	 @Get("http://37.139.18.133/tasks/?token={token}")  
	 List<Task> fetchAllTasks(String token);
	 
	 
}
