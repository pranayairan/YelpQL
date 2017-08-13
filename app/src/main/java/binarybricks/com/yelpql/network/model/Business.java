package binarybricks.com.yelpql.network.model;

import java.util.List;

import yelp.BusinessDetails;

/**
 * Created by pairan on 7/31/17.
 */

public class Business {

    private String id;
    private String name;
    private String url;
    private String phone;
    private String displayPhone;
    private Integer reviewCount;
    private String rating;
    private String price;
    private List<String> categories;
    private String formattedAddress;
    private double latitude;
    private double longitude;
    private List<String> photos;
    private boolean isOpenNow;
    private List<BusinessDetails.Open> hourList;
    private List<BusinessDetails.Review> reviewList;
    private String distanceFromCurrent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDisplayPhone() {
        return displayPhone;
    }

    public void setDisplayPhone(String displayPhone) {
        this.displayPhone = displayPhone;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
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

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public boolean isOpenNow() {
        return isOpenNow;
    }

    public void setOpenNow(boolean openNow) {
        isOpenNow = openNow;
    }

    public List<BusinessDetails.Open> getHourList() {
        return hourList;
    }

    public void setHourList(List<BusinessDetails.Open> hourList) {
        this.hourList = hourList;
    }

    public List<BusinessDetails.Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<BusinessDetails.Review> reviewList) {
        this.reviewList = reviewList;
    }

    public String getDistanceFromCurrent() {
        return distanceFromCurrent;
    }

    public void setDistanceFromCurrent(String distanceFromCurrent) {
        this.distanceFromCurrent = distanceFromCurrent;
    }
}
