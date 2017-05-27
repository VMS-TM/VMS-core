package vms.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vms.models.Property;
import vms.repositories.PropertyRepository;
import vms.services.absr.PropertyService;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    PropertyRepository propertyRepo;

    @Override
    public Property getPropertyByName(String name) {
        return propertyRepo.getPropertyByName(name);
    }

    @Override
    public List<Property> getAllProperties() {
        return (List<Property>) propertyRepo.findAll();
    }

    @Override
    public void addProperty(Property property) {
        propertyRepo.save(property);
    }

    @Override
    public void deleteProperty(Property property) {
        propertyRepo.delete(property);
    }

    @Override
    public void updateProperty(Property property) {
        propertyRepo.save(property);
    }
}
