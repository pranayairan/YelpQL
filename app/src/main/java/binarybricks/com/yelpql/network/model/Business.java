package binarybricks.com.yelpql.network.model;

import java.util.List;

/**
 * Created by pairan on 7/31/17.
 */

public class Business {

    private String id;
    private String name;
    private List<String> photos;
    private String price;
    private List<String> categories;
    private String distanceFromCurrent;
    private String rating;

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

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
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

    public String getDistanceFromCurrent() {
        return distanceFromCurrent;
    }

    public void setDistanceFromCurrent(String distanceFromCurrent) {
        this.distanceFromCurrent = distanceFromCurrent;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
