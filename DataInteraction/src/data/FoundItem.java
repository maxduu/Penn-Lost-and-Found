package data;

import java.util.Date;

public class FoundItem extends Item {

	public FoundItem(int id, String category, Date date, double latitude, double longitude, 
			String around, String attachmentLoc) {
		super(id, category, date, latitude, longitude, around, attachmentLoc);
	}
	
	public String toString() {
		String s = "" + id + ", " + category + ", " + date.toString() + ", " + latitude +
				", " + longitude + ", " + around + ", " + attachmentLoc;
		return s;
	}

}
