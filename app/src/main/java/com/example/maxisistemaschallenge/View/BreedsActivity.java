package com.example.maxisistemaschallenge.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.maxisistemaschallenge.R;
import com.example.maxisistemaschallenge.ViewModel.BreedsViewModel;
import com.example.maxisistemaschallenge.ViewModel.BreedsViewModelFactory;

import java.util.List;

public class BreedsActivity extends AppCompatActivity {
    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    List<String> breeds, photos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeds);
        progressDoalog = new ProgressDialog(BreedsActivity.this);
        progressDoalog.setMessage("Cargando sub-razas...");
        progressDoalog.show();

        recyclerView = findViewById(R.id.customRecyclerView);
        String breed="";
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                breed= "";
            } else {
                breed= extras.getString("selectBreed");
            }
        } else {
            breed= (String) savedInstanceState.getSerializable("selectBreed");
        }
        System.out.println("La raza seleccionada es "+breed);
        setupViewModel(breed);
    }

    private void setupViewModel(String breed) {
        /*Con este método se pueden observar los datos en vivo para actualizar la pantalla de la
        interfaz de usuario cada vez que haya un cambio en los datos de caché.*/
        BreedsViewModelFactory factory = new BreedsViewModelFactory(this,recyclerView);
        BreedsViewModel viewModel = ViewModelProviders.of(this,factory).get(BreedsViewModel.class);

        LifecycleOwner context = this;

        viewModel.getSubbreeds(breed).observe(context, breeds -> {
           // System.out.println("Breeds tiene "+breeds.size()+" entradas. ");
            if(breeds.isEmpty()){
                progressDoalog.dismiss();
                Toast.makeText(BreedsActivity.this, "Algo saió mal, seguro puedes volver a intentarlo!", Toast.LENGTH_SHORT).show();
            }
            else{
                //progressDoalog.dismiss();
                //generateDataList(breeds);
                viewModel.getSubbreedPhoto(breed, breeds).observe(context, photos->{
                    if(photos.isEmpty()){
                        progressDoalog.dismiss();
                        Toast.makeText(BreedsActivity.this, "Algo saió mal, seguro puedes volver a intentarlo2!", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDoalog.dismiss();
                        generateDataList(breeds, photos);
                    }
                });

            }
        });
    }

    private void generateDataList( List<String> breedsList, List<String>photoList) {
        breeds=breedsList;
        adapter = new CustomAdapter(this,breedsList,photoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
    }
}