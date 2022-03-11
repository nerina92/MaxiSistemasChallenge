package com.example.maxisistemaschallenge.Repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maxisistemaschallenge.Model.BreedImageData;
import com.example.maxisistemaschallenge.Model.ListBreedsData;
import com.example.maxisistemaschallenge.Network.ApiDataService;
import com.example.maxisistemaschallenge.Network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    Context context;
    RecyclerView reciclerView;

    public Repository(Context context, RecyclerView reciclerView) {
        this.context = context;
        this.reciclerView=reciclerView;
    }
    public void getAllBreeds2(){
        ApiDataService apiDataService = RetrofitClient.getRetrofitInstance().create(ApiDataService.class);
        Call<ListBreedsData> call = apiDataService.getAllBreeds();
        call.enqueue(new Callback<ListBreedsData>() {
            @Override
            public void onResponse(Call<ListBreedsData> call, Response<ListBreedsData> response) {
                if (response.isSuccessful()) {

                    ListBreedsData modal = response.body();
                    System.out.println(modal.toString());
                }
            }

            @Override
            public void onFailure(Call<ListBreedsData> call, Throwable t) {
                // displaying an error message in toast
                System.out.println("ERROR CALL RETROFIT"+t.fillInStackTrace());
            }
        });


    }

    public MutableLiveData<List<String>> getAllBreeds(){
        ApiDataService apiDataService = RetrofitClient.getRetrofitInstance().create(ApiDataService.class);
        Call<ListBreedsData> call= apiDataService.getAllBreeds();
        final MutableLiveData<List<String>> api_response = new MutableLiveData<>();
        //call.enqueque hace que la llamada no se realice en el proceso o hilo principal, sino en uno secundario.
        call.enqueue(new Callback<ListBreedsData>() {
            @Override
            public void onResponse(Call<ListBreedsData> call, Response<ListBreedsData> response) {
                //Llamada exitosa
                ListBreedsData respuesta = response.body();
                System.out.println("RESPUESTA: "+respuesta.toString());
                api_response.setValue(respuesta.getMessage());

            }

            @Override
            public void onFailure(Call<ListBreedsData> call, Throwable t) {
                //Resultado erroneo
                System.out.println("ERROR CALL RETROFIT"+t.fillInStackTrace());
                api_response.setValue(null);
            }
        });
        return api_response;
    }
    public MutableLiveData<List<String>> getBreedPhoto(List<String> breeds){
        ApiDataService apiDataService = RetrofitClient.getRetrofitInstance().create(ApiDataService.class);
        final MutableLiveData<List<String>> api_response = new MutableLiveData<>();
        ArrayList<String>urls=new ArrayList<>();
        for(int i=0;i<breeds.size();i++){
            Call<BreedImageData> call= apiDataService.getBreedPhoto(breeds.get(i));
            //call.enqueque hace que la llamada no se realice en el proceso o hilo principal, sino en uno secundario.
            call.enqueue(new Callback<BreedImageData>() {
                @Override
                public void onResponse(Call<BreedImageData> call, Response<BreedImageData> response) {
                    //Llamada exitosa
                    BreedImageData imageData=response.body();
                    System.out.println(imageData.toString());
                    urls.add(imageData.getUrl());
                   // api_response.setValue(response.body().getUrl());

                }
                @Override
                public void onFailure(Call<BreedImageData> call, Throwable t) {
                    //Resultado erroneo
                   System.out.println("ERROR: "+t.getMessage());
                    urls.add("");
                }
            });
        }
        api_response.setValue(urls);
        System.out.println("Size urls="+urls.size());
        return api_response;
    }

}
