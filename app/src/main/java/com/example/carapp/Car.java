package com.example.carapp;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
public class Car implements Parcelable {
    private String name;
    private String price;
    private String category;
    private String description;
    private String image;
    private List<String> slideImages;

    public Car(String name, String price, String category, String description, String image, List<String> slideImages) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.image = image;
        this.slideImages = slideImages;
    }

    protected Car(Parcel in) {
        name = in.readString();
        price = in.readString();
        category = in.readString();
        description = in.readString();
        image = in.readString();
        slideImages = in.createStringArrayList();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getSlideImages() {
        return slideImages;
    }

    public void setSlideImages(List<String> slideImages) {
        this.slideImages = slideImages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(price);
        parcel.writeString(category);
        parcel.writeString(description);
        parcel.writeString(image);
        parcel.writeStringList(slideImages);
    }

    @Override
    public String toString() {
        return name;
    }
}
