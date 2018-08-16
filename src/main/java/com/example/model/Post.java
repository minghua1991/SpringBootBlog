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

@Entity(name = "M_Post")
public class Post {
	@Id
	@SequenceGenerator(name = "postIdSeq", sequenceName = "POST_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postIdSeq")
	@Column(name = "postId")
	private int postId;

	@Column(name = "subject")
	@Length(max = 50, message = "*Maximumn 50 characters")
	@NotEmpty(message = "*Please provide a subject")
	private String subject;

	@Column(name = "content")
	@Length(max = 255, message = "*Maximumn 255 characters")
	@NotEmpty(message = "*Please provide content")
	private String content;

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "userId", name = "userId")
	private User user;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdDateTime")
	private Calendar createdDateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modifiedDateTime")
	private Calendar modifiedDateTime;

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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
