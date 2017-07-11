package vms.services.absr.property;


import vms.models.property.Property;

import java.util.List;

public interface PropertyService {

	Property getPropertyByName(String name);

	Property getPropertyById(Long id);

	List<Property> getAllProperties();

	void addProperty(Property Property);

	void deleteProperty(Property property);

	void updateProperty(Property property);


}
