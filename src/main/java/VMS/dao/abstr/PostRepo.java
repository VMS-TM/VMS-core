package VMS.dao.abstr;

import VMS.model.postenv.Post;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Кирилл on 30.04.2017.
 */
public interface PostRepo extends CrudRepository<Post,Long> {

}
