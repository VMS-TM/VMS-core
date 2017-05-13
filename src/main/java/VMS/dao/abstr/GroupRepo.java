package VMS.dao.abstr;

import VMS.model.Group;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Кирилл on 09.05.2017.
 */
public interface GroupRepo extends CrudRepository<Group,Long> {
	public Group getGroupByName(String name);
}
