package vms.repositories;


import org.springframework.data.jpa.repository.Query;
import vms.models.postenvironment.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
	Post getPostById(Long id);
	List<Post> findPostsByBlackList(boolean blacklist);
	List<Post> findAllByFromWhere(String from);
	List<Post> findAllByBlackListAndFromWhere(boolean blacklist, String from);
}
