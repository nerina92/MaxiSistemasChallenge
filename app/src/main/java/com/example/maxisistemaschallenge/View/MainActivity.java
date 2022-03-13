package com.example.maxisistemaschallenge.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.maxisistemaschallenge.R;
import com.example.maxisistemaschallenge.ViewModel.BreedsViewModel;
import com.example.maxisistemaschallenge.ViewModel.BreedsViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    List<String> breeds, filterbreeds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Cargando razas...");
        progressDoalog.show();

        recyclerView = findViewById(R.id.customRecyclerView);
        setupViewModel();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItem);
        //Log.d("SEARCH VIEW",searchView.toString());
        searchView.setOnQueryTextListener(this);
        return true;
    }

    private void setupViewModel() {
        /*Con este método se pueden observar los datos en vivo para actualizar la pantalla de la
        interfaz de usuario cada vez que haya un cambio en los datos de caché.*/
        BreedsViewModelFactory factory = new BreedsViewModelFactory(this,recyclerView);
        BreedsViewModel viewModel = ViewModelProviders.of(this,factory).get(BreedsViewModel.class);

        LifecycleOwner context = this;

        viewModel.getBreeds().observe(context, breeds -> {
            System.out.println("Breeds tiene "+breeds.size()+" entradas. ");
            if(breeds.isEmpty()){
                progressDoalog.dismiss();
                Toast.makeText(MainActivity.this, "Algo saió mal, seguro puedes volver a intentarlo!", Toast.LENGTH_SHORT).show();
            }
            else{
               //progressDoalog.dismiss();
                //generateDataList(breeds);
                viewModel.getBreedPhoto(breeds).observe(context, photos->{
                    if(photos.isEmpty()){
                        progressDoalog.dismiss();
                        Toast.makeText(MainActivity.this, "Algo saió mal, seguro puedes volver a intentarlo2!", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDoalog.dismiss();
                       // Toast.makeText(MainActivity.this,"Imagenes listas para mostrar",Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        filterbreeds=filter(breeds,query);
        adapter.setFilter(filterbreeds);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filterbreeds=filter(breeds,newText);
        adapter.setFilter(filterbreeds);
        return true;
    }
    private List<String>filter(List<String>breeds,String text){
        //System.out.println("TEXTO A BUSCAR: "+text);
        filterbreeds=new ArrayList<>();

        for (int i=0; i<breeds.size();i++){
            if (breeds.get(i).toUpperCase(Locale.ROOT).contains(text.toUpperCase(Locale.ROOT))) {
                filterbreeds.add(breeds.get(i));
            }
        }
        return filterbreeds;
    }
}