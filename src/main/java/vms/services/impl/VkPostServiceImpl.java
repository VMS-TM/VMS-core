package vms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vms.models.postenvironment.Post;
import vms.repositories.PostRepository;
import vms.services.absr.VkPostService;

import java.util.List;

@Service
public class VkPostServiceImpl implements VkPostService {

	@Autowired
	private PostRepository postRepo;

	@Override
	public void addPosts(List<Post> posts) {
		postRepo.save(posts);
	}

	@Override
	public List<Post> getAllPostFromDb() {
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
	public void addPost(Post post) {
		postRepo.save(post);
	}


}
