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

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Post.class)
	@JoinColumn(name = "postID", referencedColumnName = "id")
	private Post post;

	@Column(name = "url", length = 1024)
	private String referenceOnPost;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
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
				"posts=" + post +
				", referenceOnPost='" + referenceOnPost + '\'' +
				'}';
	}
}
