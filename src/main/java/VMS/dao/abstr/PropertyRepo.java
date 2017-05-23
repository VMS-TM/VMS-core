package VMS.dao.abstr;

import VMS.model.Property;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by Кирилл on 03.05.2017.
 */
public interface PropertyRepo extends CrudRepository<Property,Long> {
    public Property getPropertyByName(String name);
}
