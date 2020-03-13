package datamanagement;

import java.util.*;
import java.io.FileReader;
import data.FoundItem;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FoundJSONReader {
	
    private String filename;
    
    public FoundJSONReader(String fname) {
        this.filename = fname;
    }

    public Set<FoundItem> getAllFoundItems() {
        
        Set<FoundItem> foundItems = new HashSet<FoundItem>();
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
            
            int id = ((Long) item.get("id")).intValue();
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
                        
        	FoundItem f = new FoundItem(id, category, date, latitude, longitude, 
        			around, attachmentLoc);
        	foundItems.add(f);

        }

        return foundItems;
        
    }
    
}
