package vms.repositories.posts;


import org.springframework.data.repository.CrudRepository;
import vms.models.posts.Post;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
	Post getPostById(Long id);

	Post getPostByIdAndFromWhere(Long id, String from);

	Post getPostByDbIdAndFromWhere(Long vkId, String from);

	List<Post> findPostsByBlackList(boolean blacklist);

	List<Post> findAllByFromWhere(String from);

	List<Post> findAllByBlackListAndFromWhere(boolean blacklist, String from);
}
