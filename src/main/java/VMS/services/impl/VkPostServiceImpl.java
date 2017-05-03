package VMS.services.impl;

import VMS.dao.abstr.PostRepository;
import VMS.model.Post;
import VMS.services.ParsingService;
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
    private ParsingService parsingService;

    @Autowired
    private PostRepository postRepository;

    public List<Post> getPostByGroupNameFromApi(String name, String keyWords){
        return parsingService.getPostsByGroupName(name, keyWords);
    }

    public void addPosts(List<Post> posts){
        postRepository.save(posts);
    }

    public List<Post> getAllPost(){
        return (List<Post>) postRepository.findAll();
    }




}
