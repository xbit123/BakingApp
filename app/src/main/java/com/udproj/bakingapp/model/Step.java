package com.udproj.bakingapp.model;

import org.parceler.Parcel;

@Parcel
public class Step {
    int id;
    String shortDescription;
    String description;
    String videoURL;
    String thumbnailURL;

    public Step() {}

    public Step(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    @Override
    public String toString() {
        return String.valueOf(id) + " - " + shortDescription;
    }
}
