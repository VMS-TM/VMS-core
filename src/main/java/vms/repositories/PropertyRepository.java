package vms.repositories;

import vms.models.Property;
import org.springframework.data.repository.CrudRepository;

public interface PropertyRepository extends CrudRepository<Property, Long> {
	Property getPropertyByName(String name);
	Property getPropertyById(Long id);
}
