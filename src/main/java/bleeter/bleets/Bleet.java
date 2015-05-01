package bleeter.bleets;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Bleet {
	@Id
	private String id;
	private String bleet;
	private Date timestamp;
	private boolean blocked;
	private String sentiment;
	private float confidence;
	
	public Bleet(String id, String bleet, Date timestamp, boolean blocked,
			String sentiment, float confidence) {
		super();
		this.id = id;
		this.bleet = bleet;
		this.timestamp = timestamp;
		this.blocked = blocked;
		this.sentiment = sentiment;
		this.confidence = confidence;
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
}
