package vms.services;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vms.models.Photo2;
import vms.models.Post;
import vms.models.rawpost.Attachment;
import vms.models.rawpost.Item;
import vms.models.rawpost.RootObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by magic on 12.05.2017.
 */
@Service
public class CollectPostService {

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
                Post post = new Post();
                post.setText(item.getText());
                if(item.getAttachments()!= null)
                {
                    List<Photo2> photo = new ArrayList<Photo2>();
                    for(Attachment a : item.getAttachments()) {
                        if(a!=null) {
                            Photo2 photo2 = new Photo2();
                            photo2.setUrl(a.getPhoto().getPhoto604());
                            photo.add(photo2);
                        }
                    }
                    post.setAttachments(photo);
                }
                posts.add(post);
            }
            return posts;
        }

}
