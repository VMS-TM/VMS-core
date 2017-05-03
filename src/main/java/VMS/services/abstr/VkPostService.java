package VMS.services.abstr;

import VMS.model.Post;

import java.util.List;

/**
 * Created by Кирилл on 23.04.2017.
 */
public interface VkPostService {
    List<Post> getPostByGroupNameFromApi(String name, String keyWords);

    void addPosts(List<Post> posts);

    List<Post> getAllPost();

}
