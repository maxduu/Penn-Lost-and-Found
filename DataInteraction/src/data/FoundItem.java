package data;

import java.util.Date;

public class FoundItem extends Item {

	public FoundItem(long id, long posterId, String category, Date date, double latitude, double longitude, 
			String around, String attachmentLoc) {
		super(id, posterId, category, date, latitude, longitude, around, attachmentLoc);
	}
	
	public String toString() {
		String s = "id " + id + ", posterId " + posterId + ", category " + category + ", date " + date.toString() + 
				", latitiude " + latitude + ", longitude " + longitude + ", around " + 
				around + ", attachmentLoc " + attachmentLoc;
		return s;
	}

}
