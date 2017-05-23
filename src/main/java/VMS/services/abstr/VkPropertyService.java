package VMS.services.abstr;

import VMS.model.Property;

import java.util.List;

/**
 * Created by Кирилл on 03.05.2017.
 */
public interface VkPropertyService {

    Property getPropertyByName(String name);

    List<Property> getAllProperties();

    void addProperty(Property Property);

    void deleteProperty(Property property);

    void updateProperty(Property property);

}
