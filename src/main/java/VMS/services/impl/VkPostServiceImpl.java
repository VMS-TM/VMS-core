package VMS.services.impl;

import VMS.dao.abstr.PostRepo;
import VMS.model.postenv.Post;
import VMS.model.postenv.PostResponse;
import VMS.parsers.PostParser;
import VMS.services.abstr.VkGroupService;
import VMS.services.abstr.VkPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Кирилл on 25.04.2017.
 */
@Service
public class VkPostServiceImpl implements VkPostService {

    @Autowired
    private PostParser postParser;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private VkGroupService vkGroupService;

    //получение из АПИ
    public PostResponse getPostByGroupNameFromApi(String name, String keyWords){
        return postParser.getPostResponseByGroupName(name, keyWords);
    }

    //добавление в БД
    public void addPosts(List<Post> posts){
        postRepo.save(posts);
    }

    //получение из БД
    public List<Post> getAllPostFromDb(){
        return (List<Post>) postRepo.findAll();
    }

    @Override
    public void update(Post post) {
        postRepo.save(post);
    }

    @Override
    public void delete(Post post) {
        postRepo.delete(post);
    }

    @Override
    public void addPost(Post post){
        postRepo.save(post);
    }


}
