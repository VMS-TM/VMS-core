package VMS.parsers;

import VMS.model.Group;
import VMS.model.postenv.Post;
import VMS.model.postenv.PostResponse;
import VMS.model.postenv.RootObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Кирилл on 22.04.2017.
 */

@Service
public class PostParser {
    //константы
    final String ACCESS_TOKEN = "f0874a39a169ec6e0b35749c71cdcecc7da034205785e5d622c173454ff95b4532cbf6bf20bf924f365e4";
    final String uri = "https://api.vk.com/method";
    final String version = "&v=5.63";
    private int count = 0;

    /**
     * формирует один объект ответа из нескольких
     *
     * @param groups list of Groups
     * @param query keywords for search
     * @return сущность PostResponse
     */
    public PostResponse getPostResponseByGroupsList(List<Group> groups, String query) {
        PostResponse postResponse = new PostResponse();
        int count = 0;
        ArrayList<Post> posts = new ArrayList<>();
        for (Group group : groups) {
            postResponse = getPostsByGroup(group,query);
            posts.addAll(postResponse.getPosts());
            count += postResponse.getCount();
        }
        postResponse.setPosts(posts);
        postResponse.setCount(count);
        return postResponse;
    }
    //написать свое исключение если нет имени группы или запроса
    public PostResponse getPostsByGroup(Group group, String query){
       return getPostResponseByGroupName(group.getName(), query);
    }
    //написать свое исключение
    public PostResponse getPostResponseByGroupName(String nameGroup, String query){
//
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(getUriQueryWall(nameGroup, query),String.class);

        //Работа с Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        RootObject rootObject = null;

        try {
            rootObject = objectMapper.readValue(result, RootObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rootObject.getPostResponse();

    }

    private String getUriQueryWall(String domain, String query){
        StringBuilder sb =  new StringBuilder(uri);
        sb.append("/wall.search");
        sb.append("?");
        sb.append("domain=");
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
        sb.append("/wall.search");
        sb.append("?");
        sb.append("domain=");
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
