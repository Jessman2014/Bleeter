package bleeter.bleets;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document
public class Bleet {
	@Id
	private String id;
	private String bleet;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd,HH:00", timezone="CT")
	private Date timestamp;
	private boolean blocked;
	private String sentiment;
	private float confidence;
	private boolean privateComment;
	private String uid;
	private String username;
	
	@PersistenceConstructor
	public Bleet(String id, String bleet, Date timestamp, 
			boolean blocked,String sentiment, 
			float confidence,
			boolean privateComment, String uid, String username
			) {
		super();
		this.id = id;
		this.bleet = bleet;
		this.timestamp = timestamp;
		this.blocked = blocked;
		this.sentiment = sentiment;
		this.confidence = confidence;
		this.privateComment = privateComment;
		this.uid = uid;
		this.username = username;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBleet() {
		return bleet;
	}
	public void setBleet(String bleet) {
		this.bleet = bleet;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public String getSentiment() {
		return sentiment;
	}
	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}
	public float getConfidence() {
		return confidence;
	}
	public void setConfidence(float confidence) {
		this.confidence = confidence;
	}

	public boolean isPrivateComment() {
		return privateComment;
	}

	public void setPrivateComment(boolean privateComment) {
		this.privateComment = privateComment;
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	private Bleet (Builder b) {
		this.id = b.id;
		this.bleet = b.bleet;
		this.timestamp = b.timestamp;
		this.blocked = b.blocked;
		this.sentiment = b.sentiment;
		this.confidence = b.confidence;
		this.privateComment = b.privateComment;
		this.uid = b.uid;
		this.username = b.username;
	}

	public static class Builder {
		private String id;
		private String bleet;
		private Date timestamp;
		private boolean blocked;
		private String sentiment;
		private float confidence;
		private boolean privateComment;
		private String uid;
		private String username;
		
		public Builder id(String id) {
			this.id = id;
			return this;
		}
		
		public Builder bleet(String bleet) {
			this.bleet = bleet;
			return this;
		}
		
		public Builder timestamp(Date timestamp) {
			this.timestamp = timestamp;
			return this;
		}
		
		public Builder blocked(boolean blocked) {
			this.blocked = blocked;
			return this;
		}
		
		public Builder sentiment(String sentiment) {
			this.sentiment = sentiment;
			return this;
		}
		
		public Builder confidence(float confidence) {
			this.confidence = confidence;
			return this;
		}
		
		public Builder privateComment(boolean privateComment) {
			this.privateComment = privateComment;
			return this;
		}
		
		public Builder uid(String uid) {
			this.uid = uid;
			return this;
		}
		
		public Builder username(String username){
			this.username = username;
			return this;
		}
		
		public Bleet build() {
			return new Bleet(this);
		}
	}
}
