package vms.repositories;


import org.springframework.data.jpa.repository.Query;
import vms.models.postenvironment.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
	Post getProxyServerById(Long id);
	List<Post> findPostsByBlackList(boolean blacklist);
}
