package data;

import java.util.*;

public abstract class Item {

	int id;
	String category;
	Date date;
	double latitude;
	double longitude;
	String around;
	String attachmentLoc;
	
	public Item(int id, String category, Date date, double latitude, double longitude, String around,
			String attachmentLoc) {
		this.id = id;
		this.category = category;
		this.date = date;
		this.latitude = latitude;
		this.longitude = longitude;
		this.around = around;
		this.attachmentLoc = attachmentLoc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getAround() {
		return around;
	}

	public void setAround(String around) {
		this.around = around;
	}

	public String getAttachmentLoc() {
		return attachmentLoc;
	}

	public void setAttachmentLoc(String attachmentLoc) {
		this.attachmentLoc = attachmentLoc;
	}
	
}
