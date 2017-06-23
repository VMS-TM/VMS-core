package vms.models.postenvironment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import vms.models.postenvironment.attachmentenv.AttachmentContainer;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "posts")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post implements Serializable {

	@Id
	@Column(name = "id")
	@JsonProperty("id")
	public Long id;

	//id of users
	@Column(name = "from_id")
	@JsonProperty("from_id")
	private int from_id;

	public int getFrom_id() {
		return from_id;
	}

	public void setFrom_id(int from_id) {
		this.from_id = from_id;
	}

	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, mappedBy = "post")
	private List<Photo> photos = new ArrayList<>();

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Query.class)
	@JoinTable(name = "query_post",
			joinColumns = {@JoinColumn(name = "post_id")},
			inverseJoinColumns = {@JoinColumn(name = "query_id")})
	private Set<Query> queries;

	public Set<Query> getQueries() {
		return queries;
	}

	public void setQueries(Set<Query> queries) {
		this.queries = queries;
	}

	//id of groups(source)
	@Column(name = "owner_id")
	@JsonProperty("owner_id")
	private int owner_id;

	@Column(name = "date", length = 1024)
	@JsonProperty("date")
	private Date date;

	@Column(name = "post_id")
	@JsonProperty("post_id")
	private int post_id;

	@Column(name = "text", length = 25000)
	@JsonProperty("text")
	private String text;

	@JsonProperty("attachments")
	@Transient
	private ArrayList<AttachmentContainer> attachmentContainers;

	@JsonProperty("marked_as_ads")
	private int marked_as_ads;

	@JsonProperty("signer_id")
	private int signer_id;

	private boolean blackList;

	public boolean isBlackList() {
		return blackList;
	}

	public void setBlackList(boolean blackList) {
		this.blackList = blackList;
	}

	private String fromWhere;

	public String getFromWhere() {
		return fromWhere;
	}

	public void setFromWhere(String fromWhere) {
		this.fromWhere = fromWhere;
	}

	public int getSignerId() {
		return this.signer_id;
	}

	public void setSignerId(int signer_id) {
		this.signer_id = signer_id;
	}

	private boolean savedInDb;

	public boolean isSavedInDb() {
		return savedInDb;
	}

	public void setSavedInDb(boolean savedInDb) {
		this.savedInDb = savedInDb;
	}

	private boolean havePhoto;

	public boolean isHavePhoto() {
		return havePhoto;
	}

	public void setHavePhoto(boolean havePhoto) {
		this.havePhoto = havePhoto;
	}

	private boolean postedToGroup;

	public boolean isPostedToGroup() {
		return postedToGroup;
	}

	public void setPostedToGroup(boolean postedToGroup) {
		this.postedToGroup = postedToGroup;
	}

	private String phoneNumber;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	private String nameOfPerson;

	public String getNameOfPerson() {
		return nameOfPerson;
	}

	public void setNameOfPerson(String nameOfPerson) {
		this.nameOfPerson = nameOfPerson;
	}

	private String priceOfFlat;

	public String getPriceOfFlat() {
		return priceOfFlat;
	}

	public void setPriceOfFlat(String priceOfFlat) {
		this.priceOfFlat = priceOfFlat;
	}

	@Column(length = 16000)
	private String metroAndAddress;

	public String getMetroAndAddress() {
		return metroAndAddress;
	}

	public void setMetroAndAddress(String metroAndAddress) {
		this.metroAndAddress = metroAndAddress;
	}

	private String headling;

	public String getHeadling() {
		return headling;
	}

	public void setHeadling(String headling) {
		this.headling = headling;
	}

	private String area;

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	private String textOnView;

	public String getTextOnView() {
		return textOnView;
	}

	public void setTextOnView(String textOnView) {
		this.textOnView = textOnView;
	}

	private String authorOfpost;

	public String getAuthorOfpost() {
		return authorOfpost;
	}

	public void setAuthorOfpost(String authorOfpost) {
		this.authorOfpost = authorOfpost;
	}

	public int getMarkedAsAds() {
		return this.marked_as_ads;
	}

	public void setMarkedAsAds(int marked_as_ads) {
		this.marked_as_ads = marked_as_ads;
	}

	public int getFromId() {
		return this.from_id;
	}

	public void setFromId(int from_id) {
		this.from_id = from_id;
	}

	public int getOwnerId() {
		return this.owner_id;
	}

	public void setOwnerId(int owner_id) {
		this.owner_id = owner_id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(int date) {
		this.date = Date.from(Instant.ofEpochSecond((date)));
	}

	public int getPostId() {
		return this.post_id;
	}

	public void setPostId(int post_id) {
		this.post_id = post_id;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ArrayList<AttachmentContainer> getAttachmentContainers() {
		return this.attachmentContainers;
	}

	public void setAttachmentContainers(ArrayList<AttachmentContainer> attachmentContainers) {
		this.attachmentContainers = attachmentContainers;
	}


	public Post() {
	}

	public Post(Long id, String title, String owner, String district, String price, String textOnView, String adress, String contact, String info, String from, Date date) {
		this.id = id;
		this.headling = title;
		this.nameOfPerson = owner;
		this.area = district;
		this.priceOfFlat = price;
		this.textOnView = textOnView;
		this.phoneNumber = contact;
		this.metroAndAddress = adress;
		this.text = info;
		this.fromWhere = from;
		this.date = date;
	}

	public Post(String title, String owner, String district, String price, String textOnView, String adress, String contact, String info, String from) {
		this.headling = title;
		this.nameOfPerson = owner;
		this.area = district;
		this.priceOfFlat = price;
		this.textOnView = textOnView;
		this.phoneNumber = contact;
		this.metroAndAddress = adress;
		this.text = info;
		this.fromWhere = from;
	}

	public Post(int from_id, int owner_id, Date date, int post_id, String text) {
		this.from_id = from_id;
		this.owner_id = owner_id;
		this.date = date;
		this.post_id = post_id;
		this.text = text;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Post post = (Post) o;

		if (from_id != post.from_id) return false;
		if (owner_id != post.owner_id) return false;
		if (post_id != post.post_id) return false;
		if (marked_as_ads != post.marked_as_ads) return false;
		if (signer_id != post.signer_id) return false;
		if (id != null ? !id.equals(post.id) : post.id != null) return false;
		if (date != null ? !date.equals(post.date) : post.date != null) return false;
		if (text != null ? !text.equals(post.text) : post.text != null) return false;
		if (attachmentContainers != null ? !attachmentContainers.equals(post.attachmentContainers) : post.attachmentContainers != null)
			return false;
		if (fromWhere != null ? !fromWhere.equals(post.fromWhere) : post.fromWhere != null) return false;
		if (phoneNumber != null ? !phoneNumber.equals(post.phoneNumber) : post.phoneNumber != null) return false;
		if (nameOfPerson != null ? !nameOfPerson.equals(post.nameOfPerson) : post.nameOfPerson != null) return false;
		if (priceOfFlat != null ? !priceOfFlat.equals(post.priceOfFlat) : post.priceOfFlat != null) return false;
		if (metroAndAddress != null ? !metroAndAddress.equals(post.metroAndAddress) : post.metroAndAddress != null)
			return false;
		if (headling != null ? !headling.equals(post.headling) : post.headling != null) return false;
		if (area != null ? !area.equals(post.area) : post.area != null) return false;
		if (textOnView != null ? !textOnView.equals(post.textOnView) : post.textOnView != null) return false;
		return authorOfpost != null ? authorOfpost.equals(post.authorOfpost) : post.authorOfpost == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + from_id;
		result = 31 * result + owner_id;
		result = 31 * result + (date != null ? date.hashCode() : 0);
		result = 31 * result + post_id;
		result = 31 * result + (text != null ? text.hashCode() : 0);
		result = 31 * result + (attachmentContainers != null ? attachmentContainers.hashCode() : 0);
		result = 31 * result + marked_as_ads;
		result = 31 * result + signer_id;
		result = 31 * result + (fromWhere != null ? fromWhere.hashCode() : 0);
		result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
		result = 31 * result + (nameOfPerson != null ? nameOfPerson.hashCode() : 0);
		result = 31 * result + (priceOfFlat != null ? priceOfFlat.hashCode() : 0);
		result = 31 * result + (metroAndAddress != null ? metroAndAddress.hashCode() : 0);
		result = 31 * result + (headling != null ? headling.hashCode() : 0);
		result = 31 * result + (area != null ? area.hashCode() : 0);
		result = 31 * result + (textOnView != null ? textOnView.hashCode() : 0);
		result = 31 * result + (authorOfpost != null ? authorOfpost.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Post{" +
				"id=" + id +
				", from_id=" + from_id +
				", owner_id=" + owner_id +
				", date=" + date +
				", post_id=" + post_id +
				", text='" + text + '\'' +
				", attachmentContainers=" + attachmentContainers +
				", marked_as_ads=" + marked_as_ads +
				", signer_id=" + signer_id +
				", savedInDb=" + savedInDb +
				", havePhoto=" + havePhoto +
				", postedToGroup=" + postedToGroup +
				", phoneNumber='" + phoneNumber + '\'' +
				", nameOfPerson='" + nameOfPerson + '\'' +
				", priceOfFlat='" + priceOfFlat + '\'' +
				", metroAndAddress='" + metroAndAddress + '\'' +
				", headling='" + headling + '\'' +
				", area='" + area + '\'' +
				", textOnView='" + textOnView + '\'' +
				", authorOfpost='" + authorOfpost + '\'' +
				'}';
	}
}
