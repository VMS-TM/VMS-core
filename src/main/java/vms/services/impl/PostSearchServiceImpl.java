package vms.services.impl;

import vms.models.postenvironment.Post;
import vms.models.postenvironment.PostResponse;
import vms.models.postenvironment.RootObject;
import vms.models.rawgroup.Group;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vms.services.absr.PostSearchService;

import java.util.ArrayList;
import java.util.List;


@Service
public class PostSearchServiceImpl implements PostSearchService {
    //constants for query
    final String ACCESS_TOKEN = "808bcfd51bd94b4d0593a2dda57037fc4fdc46cac46e20d1b260c1a90d88b4c23023dd977e9639f7f8279";
    final String uri = "https://api.vk.com/method";
    final String version = "&v=5.63";
    private int count = 0;


    private List<RootObject> rootObjectList = new ArrayList<>();

    /**
     * confirm one PostResponse object from any PostResponse
     *
     * @param groups list of Groups
     * @param query keywords for search
     * @return object PostResponse
     */
    @Override
    public PostResponse getPostResponseByGroupsList(Iterable<Group> groups, String query) {
        PostResponse postResponseSum = new PostResponse();
        PostResponse postResponseCurrent = new PostResponse();
        int count = 0;
        ArrayList<Post> posts = new ArrayList<>();
        for (Group group : groups) {
            postResponseCurrent = getPostResponseByGroupName(group.getId(),query);
            //check when we don't have access to walls of groups
            if (postResponseCurrent != null){
                posts.addAll(postResponseCurrent.getPosts());
                count += postResponseCurrent.getCount();
            }
        }
        //when we don't have access to all walls of groups
        if (posts.size()>0){
            postResponseSum.setPosts(posts);
            postResponseSum.setCount(count);
        }
        return postResponseSum;
    }
    @Override
    public PostResponse getPostResponseByGroupName(String nameGroup, String query){
        RestTemplate restTemplate = new RestTemplate();
        RootObject rootObject  = restTemplate.getForObject(getUriQueryWall(nameGroup, query),RootObject.class);

        rootObject.getPostResponse().getPosts().removeIf(post -> post.getMarkedAsAds() == 1);

        return rootObject.getPostResponse();
    }


    private String getUriQueryWall(String ownerId, String query){
        StringBuilder sb =  new StringBuilder(uri);
        sb.append("/wall.search?owner_id=-");
        sb.append(ownerId);
        sb.append("&v=5.63");
        sb.append("&query=");
        sb.append(query);
        sb.append("&count=100");
        sb.append("&access_token=");
        sb.append(ACCESS_TOKEN);
        return sb.toString();
    }

    private String getUriQueryWall(String ownerId, String query, int count){
        StringBuilder sb =  new StringBuilder(uri);
        sb.append("/wall.search?owner_id=");
        sb.append(ownerId);
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

