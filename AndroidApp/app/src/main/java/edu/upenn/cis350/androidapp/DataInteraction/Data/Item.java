package edu.upenn.cis350.androidapp.DataInteraction.Data;


import java.util.*;

public abstract class Item {

    long id;
    long posterId;
    String category;
    Date date;
    double latitude;
    double longitude;
    String around;
    String attachmentLoc;

    public Item(long id, long posterId, String category, Date date, double latitude, double longitude, String around,
                String attachmentLoc) {
        this.id = id;
        this.posterId = posterId;
        this.category = category;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.around = around;
        this.attachmentLoc = attachmentLoc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPosterId() {
        return posterId;
    }

    public void setPosterId(long posterId) {
        this.posterId = posterId;
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

    public String getLocation() {
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
