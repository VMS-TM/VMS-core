package VMS.services.impl;

import VMS.dao.abstr.PropertyRepo;
import VMS.model.Property;
import VMS.services.abstr.VkPostService;
import VMS.services.abstr.VkPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Кирилл on 03.05.2017.
 */
@Service
public class PropertyServiceImpl implements VkPropertyService {

    @Autowired
    PropertyRepo propertyRepo;

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
