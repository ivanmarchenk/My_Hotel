package com.example.my_hotel.adapter.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Photo implements Parcelable {
    private String mUrl;
    private String mTitle;

    public Photo(String url, String title) {
        mUrl = url;
        mTitle = title;
    }

    protected Photo(Parcel in) {
        mUrl = in.readString();
        mTitle = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public static  Photo[] getPhotos() {

        return new Photo[]{
                new Photo("https://i.ibb.co/1LzgMbP/Bedroom1.jpg", "Bedroom1"),
                new Photo("https://i.ibb.co/HKLwQVQ/Hall.jpg", "Hall"),
                new Photo("https://i.ibb.co/Rz3VV3Y/Reseption.jpg", "Reseption"),
                new Photo("https://i.ibb.co/W6mZTnW/Restaurant.jpg", "Restaurant"),
                new Photo("https://i.ibb.co/pRp1z70/Bedroom2.jpg", "Bedroom2"),
                new Photo("https://i.ibb.co/myRqGgK/Bedroom3.jpg", "Bedroom3"),
        };

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mUrl);
        parcel.writeString(mTitle);
    }

}
