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
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Rest(rootUrl = "http://37.139.18.133", converters = {
		FormHttpMessageConverter.class,
		MappingJacksonHttpMessageConverter.class })
@Accept(MediaType.APPLICATION_JSON)
public interface RestInterface extends RestClientErrorHandling {

	RestTemplate getRestTemplate();

	void setRestTemplate(RestTemplate restTemplate);

	@Get("/tasks/?token={token}")
	List<Task> getAllTask(String token);

	@Post("/sessions/?email={email}&password={password}")
	User login(String email, String password);

	@Get("/users/?token={token}")
	List<User> getAllUser(String token);

	@Post("/tasks/?token={token}")
	Task addTask(MultiValueMap<String, String> formFields, String token);

	@Post("/tasks/{id}/?token={token}")
	Task updateTask(MultiValueMap<String, String> formFields, int id,
			String token);

}
