package com.example.maxisistemaschallenge.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.maxisistemaschallenge.R;
import com.example.maxisistemaschallenge.ViewModel.BreedsViewModel;
import com.example.maxisistemaschallenge.ViewModel.BreedsViewModelFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    List<String> breeds, photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Cargando....");
        progressDoalog.show();

        recyclerView = findViewById(R.id.customRecyclerView);
        setupViewModel();
    }

    private void setupViewModel() {
        /*Con este método se pueden observar los datos en vivo para actualizar la pantalla de la
        interfaz de usuario cada vez que haya un cambio en los datos de caché.*/
        BreedsViewModelFactory factory = new BreedsViewModelFactory(this,recyclerView);
        BreedsViewModel viewModel = ViewModelProviders.of(this,factory).get(BreedsViewModel.class);

        viewModel.getBreeds().observe(this, breeds -> {
            System.out.println("Breeds tiene "+breeds.size()+" entradas. ");
            if(breeds.isEmpty()){
                progressDoalog.dismiss();
                Toast.makeText(MainActivity.this, "Algo saió mal, seguro puedes volver a intentarlo!", Toast.LENGTH_SHORT).show();
            }
            else{
                progressDoalog.dismiss();
                generateDataList(breeds);
                /*viewModel.getBreedPhoto(breeds).observe(this, photos->{
                    if(photos.isEmpty()){
                        progressDoalog.dismiss();
                        Toast.makeText(MainActivity.this, "Algo saió mal, seguro puedes volver a intentarlo2!", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDoalog.dismiss();
                        generateDataList(breeds, photos);
                    }
                });*/

            }
        });
    }

    private void generateDataList( List<String> breedsList/*, List<String>photoList*/) {
        breeds=breedsList;
        adapter = new CustomAdapter(this,breedsList/*,photoList*/);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

    }

}