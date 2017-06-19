package vms.repositories;

import org.springframework.data.repository.CrudRepository;
import vms.models.postenvironment.Photo;

public interface PhotoRepository extends CrudRepository<Photo, Long> {

}