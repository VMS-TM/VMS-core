package vms.services.impl.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vms.models.posts.Post;
import vms.repositories.posts.PostRepository;
import vms.services.absr.posts.VkPostService;

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
	public void delete(List<Post> post) {
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
		return postRepo.getPostById(id);
	}

	@Override
	public List<Post> getAllBlackPosts() {
		return postRepo.findPostsByBlackList(true);
	}

	@Override
	public void deleteAllPosts(List<Post> posts) {
		postRepo.delete(posts);
	}

	@Override
	public List<Post> findAllFrom(String from) {
		return postRepo.findAllByFromWhere(from);
	}

	@Override
	public List<Post> findPostsBlackListAndFrom(boolean blacklist, String from) {
		return postRepo.findAllByBlackListAndFromWhere(blacklist, from);
	}

	@Override
	public Post getByIdAndFrom(Long dbId, String from) {
		return postRepo.getPostByDbIdAndFromWhere(dbId, from);
	}

	@Override
	public List<Post> getParticualPosts(String from) {
		return postRepo.findAllByFromWhere(from);
	}
}
