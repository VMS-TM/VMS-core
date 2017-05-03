package VMS.services.impl;

import VMS.model.Post;

import java.util.List;

/**
 * Created by Кирилл on 30.04.2017.
 */
public class TestVkPostService {
       public static void main(String[] args) {
        VkPostServiceImpl vkPostService = new VkPostServiceImpl();
           String nameGroup = "snyat_kvartiru_ekb";
           String query = "ботаническая";

           //testing getFromVkApi
           List<Post> posts;
           posts = vkPostService.getPostByGroupNameFromApi(nameGroup, query);
           System.out.println(posts);

           //testing add to DB
           vkPostService.addPosts(posts);

           //testing get from BD
           vkPostService.getAllPost();


    }
}
