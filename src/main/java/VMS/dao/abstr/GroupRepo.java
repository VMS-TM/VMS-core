package VMS.dao.abstr;

import VMS.model.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepo extends CrudRepository<Group,Long> {
	public Group getGroupByName(String name);
}
