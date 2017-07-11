package vms.repositories.property;

import org.springframework.data.repository.CrudRepository;
import vms.models.property.Property;

public interface PropertyRepository extends CrudRepository<Property, Long> {
	Property getPropertyByName(String name);

	Property getPropertyById(Long id);
}
