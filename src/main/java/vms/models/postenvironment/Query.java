package vms.models.postenvironment;

import com.fasterxml.jackson.annotation.JsonProperty;
import vms.models.Role;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "query")
public class Query {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Post.class)
	@JoinTable(name = "query_post",
			joinColumns = {@JoinColumn(name = "post_id")},
			inverseJoinColumns = {@JoinColumn(name = "query_id")})
	private Set<Post> posts;

	private String word;


	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Query() {
	}

	public Query(String word) {
		this.word = word;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Query query = (Query) o;

		if (posts != null ? !posts.equals(query.posts) : query.posts != null) return false;
		return word != null ? word.equals(query.word) : query.word == null;
	}

	@Override
	public int hashCode() {
		int result = posts != null ? posts.hashCode() : 0;
		result = 31 * result + (word != null ? word.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Query{" +
				"id=" + id +
				", posts=" + posts +
				", word='" + word + '\'' +
				'}';
	}
}
