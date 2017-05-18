package vms.repositories;

import org.springframework.data.repository.CrudRepository;
import vms.models.Post;

/**
 * Created by magic on 12.05.2017.
 */
public interface PostReposytory extends CrudRepository<Post, Integer> {
}
