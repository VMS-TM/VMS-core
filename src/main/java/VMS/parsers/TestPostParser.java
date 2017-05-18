package VMS.parsers;

import VMS.model.Post;

import java.util.List;

/**
 * Created by Кирилл on 23.04.2017.
 */
public class TestPostParsingService {

    public static void main(String[] args) {
        PostParser postParser = new PostParser();
        String nameGroup = "snyat_kvartiru_ekb";
        String query = "ботаническая";
        List <Post> posts = null;
        posts = postParser.getPostsByGroupName(nameGroup, query);
        for (Post post: posts){
            System.out.println(post.toString());
            System.out.println("****************");
        }
    }
}
