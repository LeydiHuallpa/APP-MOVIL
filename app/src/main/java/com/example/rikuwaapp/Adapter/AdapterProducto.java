package com.example.rikuwaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rikuwaapp.Entidad.Producto;
import com.example.rikuwaapp.R;

import java.util.List;

public class AdapterProducto extends RecyclerView.Adapter<AdapterProducto.ViewHolder> {

    private Context context;
    private List<Producto> listProducto;

    private EventoClick eventoClick;

    public interface EventoClick {
        void onItemClick(ViewHolder holder, int posicion);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ImageProducto;

        ViewHolder(View itemView) {
            super(itemView);
            ImageProducto = itemView.findViewById(R.id.ImageProducto);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            eventoClick.onItemClick(this, getAdapterPosition());
        }
    }

    public AdapterProducto(List<Producto> listProductoes, Context context, EventoClick click) {
        this.listProducto = listProductoes;
        this.context = context;
        this.eventoClick = click;
    }

    @Override
    public int getItemCount() {
        return listProducto.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Producto obj = listProducto.get(position);
        Glide.with(context)
                .load(obj.getImagenReferencial())
                .into(holder.ImageProducto);
//        holder.nombreProducto.setText(obj.getNombreProducto());
    }

}
