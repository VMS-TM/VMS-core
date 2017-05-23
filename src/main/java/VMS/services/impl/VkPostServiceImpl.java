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

@Service
public class VkPostServiceImpl implements VkPostService {

    @Autowired
    private PostParser postParser;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private VkGroupService vkGroupService;

    //getting data from Api
    public PostResponse getPostByGroupNameFromApi(String name, String keyWords){
        return postParser.getPostResponseByGroupName(name, keyWords);
    }

    //adding to DataBase
    public void addPosts(List<Post> posts){
        postRepo.save(posts);
    }

    //getting from DateBase
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
