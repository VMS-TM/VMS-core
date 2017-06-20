package vms.services.absr;

import vms.models.postenvironment.Post;

import java.util.List;

public interface VkPostService {
	//adding to DataBase
	void addPosts(List<Post> posts);

	//getting from DateBase
	List<Post> getAllPostFromDb();

	void update(Post post);

	void delete(Post post);

	void addPost(Post post);

	void deletePost(Long id);

	Post getById(Long id);

	List<Post> getAllBlackPosts();
}
