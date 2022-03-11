package com.example.maxisistemaschallenge.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListBreedsData {
    @SerializedName("message")
    ArrayList<String> message;
    @SerializedName("status")
    String status;

    public ListBreedsData(ArrayList<String> message, String status) {
        this.message = message;
        this.status = status;
    }

    public ArrayList<String> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<String> message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setState(String state) {
        this.status = state;
    }

    @Override
    public String toString() {
        String texto;
        texto="RetroFitData{ message=[\n";
        for (int i=0;i<message.size();i++){
            texto+=message.get(i)+",\n";
        }
        texto+="], state="+status+"}";
        return texto;
    }
}
