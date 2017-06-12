package vms.models.postenvironment;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "photos")
public class Photo {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToMany(fetch = FetchType.EAGER, targetEntity = Post.class)
	@JoinTable(name = "postphotos",
			joinColumns = {@JoinColumn(name = "photo_id")},
			inverseJoinColumns = {@JoinColumn(name = "post_id")})
	private List<Post> posts;

	private String referenceOnPost;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public String getReferenceOnPost() {
		return referenceOnPost;
	}

	public void setReferenceOnPost(String referenceOnPost) {
		this.referenceOnPost = referenceOnPost;
	}

	@Override
	public String toString() {
		return "Photo{" +
				"posts=" + posts +
				", referenceOnPost='" + referenceOnPost + '\'' +
				'}';
	}
}
