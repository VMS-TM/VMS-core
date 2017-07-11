package vms.models.posts;

import javax.persistence.*;

@Entity
@Table(name = "photos")
public class Photo {

	@Id
	@Column(name = "id", length = 1024)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Post.class)
	@JoinColumn(name = "postID", referencedColumnName = "id")
	private Post post;

	@Column(name = "url", length = 1024)
	private String referenceOnPost;

	private long postIdInVk;

	private long ownerIdInVk;

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

	public long getPostIdInVk() {
		return postIdInVk;
	}

	public void setPostIdInVk(long postIdInVk) {
		this.postIdInVk = postIdInVk;
	}

	public long getOwnerIdInVk() {
		return ownerIdInVk;
	}

	public void setOwnerIdInVk(long ownerIdInVk) {
		this.ownerIdInVk = ownerIdInVk;
	}

	@Override
	public String toString() {
		return "Photo{" +
				"id=" + id +
				", posts=" + post +
				", referenceOnPost='" + referenceOnPost + '\'' +
				", postIdInVk=" + postIdInVk +
				", ownerIdInVk=" + ownerIdInVk +
				'}';
	}
}
