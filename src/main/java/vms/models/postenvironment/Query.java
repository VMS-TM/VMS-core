package vms.models.postenvironment;

import com.fasterxml.jackson.annotation.JsonProperty;
import vms.models.Role;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "query")
public class Query {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, mappedBy = "query")
	private List<Post> posts;

	private String word;

	private String fromwhere;

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getFromwhere() {
		return fromwhere;
	}

	public void setFromwhere(String fromwhere) {
		this.fromwhere = fromwhere;
	}

	public Query() {
	}

	public Query(String word) {
		this.word = word;
	}

	public Query(String word, String fromwhere) {
		this.word = word;
		this.fromwhere = fromwhere;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Query query = (Query) o;

		if (id != null ? !id.equals(query.id) : query.id != null) return false;
		if (word != null ? !word.equals(query.word) : query.word != null) return false;
		return fromwhere != null ? fromwhere.equals(query.fromwhere) : query.fromwhere == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (word != null ? word.hashCode() : 0);
		result = 31 * result + (fromwhere != null ? fromwhere.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Query{" +
				"id=" + id +
				", posts=" + posts +
				", word='" + word + '\'' +
				", fromwhere='" + fromwhere + '\'' +
				'}';
	}
}
