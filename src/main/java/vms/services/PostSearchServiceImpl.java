package vms.services;

import vms.models.postenvironment.Post;
import vms.models.postenvironment.PostResponse;
import vms.models.postenvironment.RootObject;
import vms.models.rawgroup.Group;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vms.services.absr.PostSearchService;

import java.util.ArrayList;


@Service
public class PostSearchServiceImpl implements PostSearchService {
    //constants for query
    final String ACCESS_TOKEN = "f0874a39a169ec6e0b35749c71cdcecc7da034205785e5d622c173454ff95b4532cbf6bf20bf924f365e4";
    final String uri = "https://api.vk.com/method";
    final String version = "&v=5.63";
    private int count = 0;

    /**
     * confirm one response object from any
     *
     * @param groups list of Groups
     * @param query keywords for search
     * @return object PostResponse
     */
    @Override
    public PostResponse getPostResponseByGroupsList(Iterable<Group> groups, String query) {
        PostResponse postResponse = new PostResponse();
        int count = 0;
        ArrayList<Post> posts = new ArrayList<>();
        for (Group group : groups) {
            postResponse = getPostResponseByGroupName(group.getName(),query);
            posts.addAll(postResponse.getPosts());
            count += postResponse.getCount();
        }
        postResponse.setPosts(posts);
        postResponse.setCount(count);
        return postResponse;
    }
    @Override
    public PostResponse getPostResponseByGroupName(String nameGroup, String query){
        RestTemplate restTemplate = new RestTemplate();
        RootObject rootObject  = restTemplate.getForObject(getUriQueryWall(nameGroup, query),RootObject.class);
        return rootObject.getPostResponse();
    }


    private String getUriQueryWall(String domain, String query){
        StringBuilder sb =  new StringBuilder(uri);
        sb.append("/wall.search?domain=");
        sb.append(domain);
        sb.append("&v=5.63");
        sb.append("&query=");
        sb.append(query);
        sb.append("&access_token=");
        sb.append(ACCESS_TOKEN);
        return sb.toString();
    }

    private String getUriQueryWall(String domain, String query, int count){
        StringBuilder sb =  new StringBuilder(uri);
        sb.append("/wall.search").append("?").append("domain=");
        sb.append(domain);
        sb.append("&v=5.63");
        sb.append("&count=");
        sb.append(count);
        sb.append("&query=");
        sb.append(query);
        sb.append("&access_token=");
        sb.append(ACCESS_TOKEN);
        return sb.toString();
    }
}

