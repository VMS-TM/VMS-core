package vms.services.absr;

import vms.models.postenvironment.Post;

import java.util.List;
import java.util.Set;

public interface VkPostService {
	//adding to DataBase
	void addPosts(List<Post> posts);

	//getting from DateBase
	List<Post> getAllPostFromDb();

	void update(Post post);

	void delete(Post post);

	void addPost(Post post);

	void deletePost(Long id);

	void deleteAllPosts(List<Post> posts);

	Post getById(Long id);

	Post getByIdAndFrom(Long id, String from);

	List<Post> getAllBlackPosts();

	List<Post> findAllFrom(String from);

	List<Post> findPostsBlackListAndFrom(boolean blacklist, String from);
}
