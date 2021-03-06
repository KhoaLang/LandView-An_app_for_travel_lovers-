package com.example.landview.Restaurant;

import java.io.Serializable;
import java.util.ArrayList;

public class Restaurant implements Serializable {

    private String address;
    private String areaId;
    private String geohash;
    private double latitude;
    private double longitude;
    private String id;
    private ArrayList<String> images;
    private ArrayList<String> likesList;
    private String name;
    private String type;

    private float rating;
    private int totalRate;
    private ArrayList<String> menu;

    public Restaurant() {
    }

    public Restaurant(String address, String areaId, String geohash, double latitude, double longitude,
                      String id, ArrayList<String> images, ArrayList<String> likesList, String name,
                      String type, float rating, int totalRate, ArrayList<String> menu) {
        this.address = address;
        this.areaId = areaId;
        this.geohash = geohash;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.images = images;
        this.likesList = likesList;
        this.name = name;
        this.type = type;
        this.rating = rating;
        this.totalRate = totalRate;
        this.menu = menu;
    }

    /******************************************************************************************************************/

    // getter

    public String getAddress() {
        return address;
    }

    public String getAreaId() {
        return areaId;
    }

    public String getGeohash() {
        return geohash;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getId() {
        return id;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public ArrayList<String> getLikesList() {
        return likesList;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public float getRating() {
        return rating;
    }

    public int getTotalRate() {
        return totalRate;
    }

    public ArrayList<String> getMenu() {
        return menu;
    }

    /******************************************************************************************************************/

    // setter
    public void setAddress(String address) {
        this.address = address;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public void setLikesList(ArrayList<String> likesList) {
        this.likesList = likesList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setTotalRate(int totalRate) {
        this.totalRate = totalRate;
    }

    public void setMenu(ArrayList<String> menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "address='" + address + '\'' +
                ", areaId='" + areaId + '\'' +
                ", geohash='" + geohash + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", id='" + id + '\'' +
                ", images=" + images +
                ", likesList=" + likesList +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", rating=" + rating +
                ", totalRate=" + totalRate +
                '}';
    }
}
