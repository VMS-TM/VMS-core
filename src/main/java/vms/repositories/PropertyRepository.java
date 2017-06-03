package vms.repositories;

import vms.models.Property;
import org.springframework.data.repository.CrudRepository;

public interface PropertyRepository extends CrudRepository<Property, Long> {
	public Property getPropertyByName(String name);
}
