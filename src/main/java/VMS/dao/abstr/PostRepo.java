package VMS.dao.abstr;

import VMS.model.postenv.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepo extends CrudRepository<Post,Long> {

}
