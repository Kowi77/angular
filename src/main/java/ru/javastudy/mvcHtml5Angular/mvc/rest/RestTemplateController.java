package ru.javastudy.mvcHtml5Angular.mvc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.javastudy.mvcHtml5Angular.mvc.rest.model.RestPostsModel;
import ru.javastudy.mvcHtml5Angular.mvc.rest.model.RestUserModel;

import java.util.Arrays;
import java.util.List;

/**
 * Created for JavaStudy.ru on 28.02.2016.
 */
@RestController //will add automatically the @ResponseBody annotation to all methods
public class RestTemplateController {

    /**
     * Accessing a third-party REST service inside a Spring application
     * it can even bind that data to custom domain types.
     */
    @Autowired
    private RestTemplate restTemplate;

    private final String EXTERNAL_REST_URL = "http://jsonplaceholder.typicode.com"; //free JSON services

    @RequestMapping(value = "/rest/users", method = RequestMethod.GET)
    public List<RestUserModel> getRestUsers() {
        System.out.println("RestTemplateController getRestUsers is called");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:49.0) Gecko/20100101 Firefox/49.0");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        //JSON http://jsonplaceholder.typicode.com/users
        ResponseEntity<RestUserModel[]> response = restTemplate.exchange(
                EXTERNAL_REST_URL +"/users",
                HttpMethod.GET,
                entity,
                RestUserModel[].class
        );
        return Arrays.asList(response.getBody());
    }

    @RequestMapping(value = "/rest/posts", method = RequestMethod.GET)
    public List<RestPostsModel> getRestPosts() {
        System.out.println("RestTemplateController getRestPosts is called");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:49.0) Gecko/20100101 Firefox/49.0");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<RestPostsModel[]> response = restTemplate.exchange(
                EXTERNAL_REST_URL +"/posts",
                HttpMethod.GET,
                entity,
                RestPostsModel[].class
        );
        return Arrays.asList(response.getBody());
    }

    @RequestMapping(value = "/rest/posts/{param}", method = RequestMethod.GET)
    public RestPostsModel getRestPostsById(@PathVariable("param") String param) {
        System.out.println("RestTemplateController getRestPostsById is called");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:49.0) Gecko/20100101 Firefox/49.0");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<RestPostsModel> response = restTemplate.exchange(
                EXTERNAL_REST_URL +"/posts/" + param,
                HttpMethod.GET,
                entity,
                RestPostsModel.class
        );
        return response.getBody();
    }

    //JSON Deletes a post
    @RequestMapping(value = "/rest/delPosts/{postId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void deletePostByID(@PathVariable(value="postId") String postId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:49.0) Gecko/20100101 Firefox/49.0");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        //in test case 100 posts. Try to del id 100+ (id=150 for example) and check status in console
        restTemplate.exchange(EXTERNAL_REST_URL +"/posts/" + postId, HttpMethod.DELETE, entity, String.class);
        System.out.println("@RestTemplateControllerExample deletePostByID is called");
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.FORBIDDEN,reason="FORBIDDEN ACCESS (PROVIDE YOUR CUSTOM REASON HERE)")
    public void handleException(Exception ex) {
        System.out.println("@RestTemplateControllerExample handleException");
        System.out.println(ex);
    }
}

