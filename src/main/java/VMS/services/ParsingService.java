package VMS.services;

import VMS.model.Post;
import VMS.model.postenv.Item;
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
public class ParsingService {
    //константы
    final String ACCESS_TOKEN = "f0874a39a169ec6e0b35749c71cdcecc7da034205785e5d622c173454ff95b4532cbf6bf20bf924f365e4";
    final String uri = "https://api.vk.com/method";
    final String version = "&v=5.63";

    public List<Post> getPostsByGroupName(String nameGroup, String query){
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

        return item2Post(rootObject.getResponse().getItems());

    }

    private String getUriQueryWall(String domain, String query){
        StringBuilder sb =  new StringBuilder(uri);
        sb.append("/wall.search");
        sb.append("?");
        sb.append("domain=");
        sb.append(domain);
        sb.append("&v=5.63");
        sb.append("&count=6");
        sb.append("&query=");
        sb.append(query);
        sb.append("&access_token=");
        sb.append(ACCESS_TOKEN);
        return sb.toString();
    }

    public List<Post> item2Post(List<Item> items){
        List<Post> posts = new ArrayList<Post>();
        for (Item item: items){
            posts.add(new Post(item.getFromId(),item.getOwnerId(),item.getDate(),item.getPostId(),item.getText()));
        }
        return posts;
    }

}
