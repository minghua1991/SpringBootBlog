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

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity(name = "M_Comment")
public class Comment {
	@Id
	@SequenceGenerator(name = "commentIdSeq", sequenceName = "COMMENT_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commentIdSeq")
	@Column(name = "commentId")
	private int commentId;

	@Column(name = "content")
	@Length(max = 255, message = "*Maximumn 255 characters")
	@NotEmpty(message = "*Please provide content")
	private String content;

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "userId", name = "userId")
	private User user;

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "postId", name = "postId")
	private Post post;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdDateTime")
	private Calendar createdDateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modifiedDateTime")
	private Calendar modifiedDateTime;

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Calendar getModifiedDateTime() {
		return modifiedDateTime;
	}

	public void setModifiedDateTime(Calendar modifiedDateTime) {
		this.modifiedDateTime = modifiedDateTime;
	}

	public String getCreatedDateTimeInStringFormate() {
		String output = null;
		if (createdDateTime != null) {
			SimpleDateFormat dateTimeFormate = new SimpleDateFormat("yyyy-MM-dd '|' HH:mm:ss");
			output = dateTimeFormate.format(createdDateTime.getTime());
		}
		return output;
	}

	public String getModifiedDateTimeInStringFormate() {
		String output = null;
		if (modifiedDateTime != null) {
			SimpleDateFormat dateTimeFormate = new SimpleDateFormat("yyyy-MM-dd '|' HH:mm:ss");
			output = dateTimeFormate.format(modifiedDateTime.getTime());
		}
		return output;
	}
}
