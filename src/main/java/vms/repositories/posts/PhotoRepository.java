package vms.repositories.posts;

import org.springframework.data.repository.CrudRepository;
import vms.models.posts.Photo;

public interface PhotoRepository extends CrudRepository<Photo, Long> {

}