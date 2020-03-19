package data;

import java.util.Date;

public class LostItem extends Item {
	
	String description;
	String additionalInfo;

	public LostItem(long id, long posterId, String category, Date date, double latitude, double longitude, 
			String around, String attachmentLoc, String description, String additionalInfo) {
		super(id, posterId, category, date, latitude, longitude, around, attachmentLoc);
		this.description = description;
		this.additionalInfo = additionalInfo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	
	public String toString() {
		String s = "id " + id + ", posterId " + posterId + ", category " + category + ", date " + date.toString() + 
				", latitiude " + latitude + ", longitude " + longitude + ", around " + 
				around + ", attachmentLoc " + attachmentLoc + ", description " + description + 
				", additionalInfo " + additionalInfo;
		return s;
	}

}
