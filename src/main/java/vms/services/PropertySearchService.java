package vms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vms.models.Property;
import vms.services.absr.PropertyService;

@Service
public class PropertySearchService {

    @Autowired
    PropertyService propertyService;

    public String getValue(String key) {

        Property property = propertyService.getPropertyByName(key);

        if (property != null) {
            return property.getValue();
        }

        return null;

    }


}
