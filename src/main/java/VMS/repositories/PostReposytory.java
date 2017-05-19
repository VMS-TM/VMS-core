package vms.repositories;

import org.springframework.data.repository.CrudRepository;
import vms.models.Post;


public interface PostReposytory extends CrudRepository<Post, Integer> {
}
