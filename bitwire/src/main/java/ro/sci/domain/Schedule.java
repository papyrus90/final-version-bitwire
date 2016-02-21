package ro.sci.domain;

import java.util.Date;

/**
 * This class is for holding fields needed for the schedule.
 * @author Sandor Naghi
 *
 */
public class Schedule extends User{
	
	private long userId;
	private String title;
	private String postingDate;
	private String postingTime;
	private Date timestamp;
	private String content;
	private String media;
	private String visibility;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPostingDate() {
		return postingDate;
	}
	public void setPostingDate(String postingDate) {
		this.postingDate = postingDate;
	}
	public String getPostingTime() {
		return postingTime;
	}
	public void setPostingTime(String postingTime) {
		this.postingTime = postingTime;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp){
		this.timestamp = timestamp;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + ((media == null) ? 0 : media.hashCode());
		result = prime * result + ((postingDate == null) ? 0 : postingDate.hashCode());
		result = prime * result + ((postingTime == null) ? 0 : postingTime.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		result = prime * result + ((visibility == null) ? 0 : visibility.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedule other = (Schedule) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (media == null) {
			if (other.media != null)
				return false;
		} else if (!media.equals(other.media))
			return false;
		if (postingDate == null) {
			if (other.postingDate != null)
				return false;
		} else if (!postingDate.equals(other.postingDate))
			return false;
		if (postingTime == null) {
			if (other.postingTime != null)
				return false;
		} else if (!postingTime.equals(other.postingTime))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (userId != other.userId)
			return false;
		if (visibility == null) {
			if (other.visibility != null)
				return false;
		} else if (!visibility.equals(other.visibility))
			return false;
		return true;
	}
}
