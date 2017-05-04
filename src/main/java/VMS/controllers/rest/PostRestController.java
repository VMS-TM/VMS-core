package VMS.controllers.rest;

import VMS.model.Post;
import VMS.services.abstr.VkPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Кирилл on 30.04.2017.
 */
@RestController
public class PostRestController {

    @Autowired
    VkPostService postService;

    String nameGroup = "snyat_kvartiru_ekb";
    String query = "ботаническая";

    //
    @RequestMapping(value = { "/req"}, method = RequestMethod.GET)
    public List<Post> getPostsFromApi(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "query", required = false) String query ) {

        if (name!=null)
            this.nameGroup = name;
        if (query!=null)
            this.query = query;

        return postService.getPostByGroupNameFromApi(this.nameGroup, this.query);

        //return posts.toString();

    }
    @RequestMapping(value = { "/get"}, method = RequestMethod.GET)
    public List<Post> getPostsFromDb(){

        return postService.getAllPost();

    }

    @RequestMapping(value = {"/add"}, method = RequestMethod.GET)
    public String addPosts() {
        List<Post> posts = postService.getPostByGroupNameFromApi(nameGroup, query);
        postService.addPosts(posts);
        return "sucessfull";
    }


}
