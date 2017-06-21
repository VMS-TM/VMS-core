package vms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vms.models.postenvironment.Post;
import vms.repositories.PostRepository;
import vms.services.absr.VkPostService;

import java.util.List;

@Service
@Transactional
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

	@Override
	public void deletePost(Long id) {
		postRepo.delete(id);
	}

	@Override
	public Post getById(Long id) {
		return postRepo.getProxyServerById(id);
	}

	@Override
	public List<Post> getAllBlackPosts() {
		return postRepo.findPostsByBlackList(true);
	}
}
