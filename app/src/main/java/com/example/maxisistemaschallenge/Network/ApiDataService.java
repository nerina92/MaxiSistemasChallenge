package com.example.maxisistemaschallenge.Network;

import com.example.maxisistemaschallenge.Model.BreedImageData;
import com.example.maxisistemaschallenge.Model.ListBreedsData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ApiDataService {
    @GET("/api/breeds/list")
    Call<ListBreedsData> getAllBreeds();

    @GET("/api/breed/{breed}/images/random")
    Call<BreedImageData> getBreedPhoto(@Path("breed") String breed);

    @GET("/api/breed/{breed}/list")
    Call<ListBreedsData> getSubBreeds(@Path("breed") String breed);

    @GET("/api/breed/{breed}/{sub_breed}/images/random")
    Call<BreedImageData> getSubBreedPhoto(@Path("breed") String breed,@Path("sub_breed") String sub_breed);
}
