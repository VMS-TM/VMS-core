package vms.models.posts.attachment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Thumb implements Serializable {
	private int id;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private int album_id;

	public int getAlbumId() {
		return this.album_id;
	}

	public void setAlbumId(int album_id) {
		this.album_id = album_id;
	}

	private int owner_id;

	public int getOwnerId() {
		return this.owner_id;
	}

	public void setOwnerId(int owner_id) {
		this.owner_id = owner_id;
	}

	private int user_id;

	public int getUserId() {
		return this.user_id;
	}

	public void setUserId(int user_id) {
		this.user_id = user_id;
	}

	private String photo_75;

	public String getPhoto75() {
		return this.photo_75;
	}

	public void setPhoto75(String photo_75) {
		this.photo_75 = photo_75;
	}

	private String photo_130;

	public String getPhoto130() {
		return this.photo_130;
	}

	public void setPhoto130(String photo_130) {
		this.photo_130 = photo_130;
	}

	private String photo_604;

	public String getPhoto604() {
		return this.photo_604;
	}

	public void setPhoto604(String photo_604) {
		this.photo_604 = photo_604;
	}

	private String photo_807;

	public String getPhoto807() {
		return this.photo_807;
	}

	public void setPhoto807(String photo_807) {
		this.photo_807 = photo_807;
	}

	private String photo_1280;

	public String getPhoto1280() {
		return this.photo_1280;
	}

	public void setPhoto1280(String photo_1280) {
		this.photo_1280 = photo_1280;
	}

	private String photo_2560;

	public String getPhoto2560() {
		return this.photo_2560;
	}

	public void setPhoto2560(String photo_2560) {
		this.photo_2560 = photo_2560;
	}

	private int width;

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	private int height;

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	private String text;

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	private int date;

	public int getDate() {
		return this.date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	private String access_key;

	public String getAccessKey() {
		return this.access_key;
	}

	public void setAccessKey(String access_key) {
		this.access_key = access_key;
	}
}