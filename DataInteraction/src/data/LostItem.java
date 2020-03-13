package data;

import java.util.Date;

public class LostItem extends Item {
	
	String description;
	String additionalInfo;

	public LostItem(int id, String category, Date date, double latitude, double longitude, 
			String around, String attachmentLoc, String description, String additionalInfo) {
		super(id, category, date, latitude, longitude, around, attachmentLoc);
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
		String s = "" + id + ", " + category + ", " + ", " + date.toString() + ", " + latitude +
				", " + longitude + ", " + around + ", " + attachmentLoc + ", " + description + 
				", " + additionalInfo;
		return s;
	}

}
