//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package hu.todo.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import hu.todo.entity.Task;
import hu.todo.entity.User;
import hu.todo.rest.java.util.List_Task;
import hu.todo.rest.java.util.List_User;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public final class TaskRestInterface_
    implements TaskRestInterface
{

    private RestTemplate restTemplate;
    private RestErrorHandler restErrorHandler;
    private String rootUrl;

    public TaskRestInterface_() {
        restTemplate = new RestTemplate();
        rootUrl = "http://37.139.18.133";
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
        restTemplate.setInterceptors(new ArrayList<ClientHttpRequestInterceptor>());
        restTemplate.getInterceptors().add(new HttpBasicAuthenticatorInterceptor());
    }

    @Override
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    @Override
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void setRestErrorHandler(RestErrorHandler arg0) {
        this.restErrorHandler = arg0;
    }

    @Override
    public List<Task> getAllTask(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.parseMediaType("application/json")));
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(httpHeaders);
        HashMap<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("token", token);
        try {
            return restTemplate.exchange(rootUrl.concat("/tasks/?token={token}"), HttpMethod.GET, requestEntity, List_Task.class, urlVariables).getBody();
        } catch (RestClientException e) {
            if (restErrorHandler!= null) {
                restErrorHandler.onRestClientExceptionThrown(e);
                return null;
            } else {
                throw e;
            }
        }
    }

    @Override
    public List<User> getAllUser(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.parseMediaType("application/json")));
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(httpHeaders);
        HashMap<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("token", token);
        try {
            return restTemplate.exchange(rootUrl.concat("/users/?token={token}"), HttpMethod.GET, requestEntity, List_User.class, urlVariables).getBody();
        } catch (RestClientException e) {
            if (restErrorHandler!= null) {
                restErrorHandler.onRestClientExceptionThrown(e);
                return null;
            } else {
                throw e;
            }
        }
    }

    @Override
    public User login(String email, String password) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.parseMediaType("application/json")));
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(httpHeaders);
        HashMap<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("email", email);
        urlVariables.put("password", password);
        try {
            return restTemplate.exchange(rootUrl.concat("/sessions/?email={email}&password={password}"), HttpMethod.POST, requestEntity, User.class, urlVariables).getBody();
        } catch (RestClientException e) {
            if (restErrorHandler!= null) {
                restErrorHandler.onRestClientExceptionThrown(e);
                return null;
            } else {
                throw e;
            }
        }
    }

}
