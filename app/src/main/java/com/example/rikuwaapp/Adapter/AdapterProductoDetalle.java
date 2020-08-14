package com.example.rikuwaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rikuwaapp.Entidad.Producto;
import com.example.rikuwaapp.R;

import java.util.List;

public class AdapterProductoDetalle extends RecyclerView.Adapter<AdapterProductoDetalle.ViewHolder> {

    private Context context;
    private List<Producto> listProducto;

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ImageProducto;
        TextView txtPrecioProducto;

        ViewHolder(View itemView) {
            super(itemView);
            ImageProducto = itemView.findViewById(R.id.ImageProducto);
            txtPrecioProducto = itemView.findViewById(R.id.txtPrecioProducto);
        }
    }

    public AdapterProductoDetalle(List<Producto> listProductoes, Context context) {
        this.listProducto = listProductoes;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return listProducto.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_productos, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Producto obj = listProducto.get(position);
        Glide.with(context)
                .load(obj.getImagenReferencial())
                .into(holder.ImageProducto);
        holder.txtPrecioProducto.setText(obj.getNombreProducto());
    }

}

