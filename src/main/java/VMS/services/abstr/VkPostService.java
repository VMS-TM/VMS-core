package VMS.services.abstr;

import VMS.model.postenv.Post;
import VMS.model.postenv.PostResponse;

import java.util.List;

/**
 * Created by Кирилл on 23.04.2017.
 */
public interface VkPostService {
    PostResponse getPostByGroupNameFromApi(String name, String keyWords);

    void addPosts(List<Post> posts);

    List<Post> getAllPostFromDb();

	void update(Post post);

	void delete(Post post);

	void addPost(Post post);

	}
