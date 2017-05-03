package VMS.dao.abstr;

import VMS.model.Post;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Кирилл on 30.04.2017.
 */
public interface PostRepository extends CrudRepository<Post,Long> {

}
