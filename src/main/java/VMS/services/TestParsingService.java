package VMS.services;

import VMS.model.Post;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Кирилл on 23.04.2017.
 */
public class TestParsingService {

    public static void main(String[] args) {
        ParsingService parsingService = new ParsingService();
        String nameGroup = "snyat_kvartiru_ekb";
        String query = "ботаническая";
        List <Post> posts = null;
        posts = parsingService.getPostsByGroupName(nameGroup, query);
        for (Post post: posts){
            System.out.println(post.toString());
            System.out.println("****************");
        }
    }
}
