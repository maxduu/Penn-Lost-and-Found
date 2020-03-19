package datamanagement;

import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import data.FoundItem;
import data.LostItem;

public class LostJSONReader {

	private String filename;
    
    private LostJSONReader(String fname) {
        this.filename = fname;
    }
    
    private static LostJSONReader instance = new LostJSONReader("../data/lost-items.json");
    
    public static LostJSONReader getInstance() {
    	return instance;
    }

    public Collection<LostItem> getAllLostItems() {
        
        Collection<LostItem> lostItems = new HashSet<LostItem>();
        JSONParser parser = new JSONParser();
        JSONArray items = null;
        
        try {
            items = (JSONArray)parser.parse(new FileReader(filename));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        
        Iterator iter = items.iterator();
        
        while (iter.hasNext()) {
                        
            JSONObject item = (JSONObject) iter.next();
            
            long id = (Long) item.get("id");
            long posterId = ((Long) item.get("posterId"));
        	String category = (String) item.get("category");
        	
        	String rawDate = (String) item.get("date");
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        	Date date = null;
        	try {
				date = dateFormat.parse(rawDate);
			} catch (ParseException e) {
				System.out.println("date parse error");
				continue;
			}
        	
        	double latitude = (double) item.get("latitude");
        	double longitude = (double) item.get("longitude");
        	String around = (String) item.get("around");
        	String attachmentLoc = (String) item.get("attachmentLoc");
        	String description = (String) item.get("description");
        	String additionalInfo = (String) item.get("additionalInfo");
                        
        	LostItem l = new LostItem(id, posterId, category, date, latitude, longitude, 
        			around, attachmentLoc, description, additionalInfo);
        	lostItems.add(l);

        }

        return lostItems;
        
    }
	
}
