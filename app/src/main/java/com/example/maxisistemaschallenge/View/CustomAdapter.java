package com.example.maxisistemaschallenge.View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.maxisistemaschallenge.R;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.Locale;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>{
    private List<String> photoList;
    private List<String> breedList;
    private Context context;

    public CustomAdapter(Context context, List<String> breedList, List<String>photoList){
        this.context = context;
        this.breedList=breedList;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.txtTitle.setText(breedList.get(position).toUpperCase(Locale.ROOT));
        try{
            Picasso.get().load(photoList.get(position)).into(holder.imgView);
        }catch (NullPointerException | IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }

        if(context.getClass().toString().equals("class com.example.maxisistemaschallenge.View.MainActivity")){
            holder.itemView.setOnClickListener(v->{
                Intent miIntent = new Intent(context, BreedsActivity.class);
                miIntent.putExtra("selectBreed", breedList.get(position));
                context.startActivity(miIntent);
            });
        }

    }

    @Override
    public int getItemCount() {
        return breedList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{
        public final View mView;

        ImageView imgView;
        TextView txtTitle;

        public CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            imgView=mView.findViewById(R.id.image_view);
            txtTitle = mView.findViewById(R.id.tv_title);
        }

    }
}
