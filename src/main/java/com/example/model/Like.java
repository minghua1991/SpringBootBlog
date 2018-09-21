package com.example.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "M_Like")
public class Like {
	@Id
	@SequenceGenerator(name = "likeIdSeq", sequenceName = "LIKE_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "likeIdSeq")
	@Column(name = "likeId")
	private int likeId;

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "userId", name = "userId")
	private User user;

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "postId", name = "postId")
	private Post post;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdDateTime")
	private Calendar createdDateTime;

	public int getLikeId() {
		return likeId;
	}

	public void setLikeId(int likeId) {
		this.likeId = likeId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Calendar getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Calendar createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getCreatedDateTimeInStringFormate() {
		String output = null;
		if (createdDateTime != null) {
			SimpleDateFormat dateTimeFormate = new SimpleDateFormat("yyyy-MM-dd '|' HH:mm:ss");
			output = dateTimeFormate.format(createdDateTime.getTime());
		}
		return output;
	}
}
