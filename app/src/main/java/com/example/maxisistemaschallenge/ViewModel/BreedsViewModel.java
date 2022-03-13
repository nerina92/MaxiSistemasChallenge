package com.example.maxisistemaschallenge.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maxisistemaschallenge.Repository.Repository;

import java.util.List;

public class BreedsViewModel extends ViewModel {
    Repository repository;


    public BreedsViewModel(Context context, RecyclerView recyclerView) {
        this.repository = new Repository(context,recyclerView);
    }

    /*Los métodos siguientes llaman al repositorio desde el ModelView*/
    public MutableLiveData<List<String>> getBreeds() {
        //repository.getAllBreeds2();
        return repository.getAllBreeds();
    }
    public MutableLiveData<List<String>> getBreedPhoto(List<String> breeds) {
        return repository.getBreedPhoto(breeds);
    }
    public MutableLiveData<List<String>> getSubbreeds(String breed) {
        return repository.getSubbreeds(breed);
    }
    public MutableLiveData<List<String>>getSubbreedPhoto(String breed, List<String>subbreeds){
        return repository.getSubbreedPhoto(breed,subbreeds);
    }
}
