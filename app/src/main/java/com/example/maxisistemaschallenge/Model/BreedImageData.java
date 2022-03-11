package com.example.maxisistemaschallenge.Model;

import com.google.gson.annotations.SerializedName;

public class BreedImageData {
    @SerializedName("message")
    String url;
    @SerializedName("status")
    String status;

    public BreedImageData(String url, String status) {
        this.url = url;
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "BreedImageData{" +
                "url='" + url + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
