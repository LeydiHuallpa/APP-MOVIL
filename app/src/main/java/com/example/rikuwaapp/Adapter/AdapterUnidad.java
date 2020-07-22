package com.example.rikuwaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rikuwaapp.Entidad.Unidad;
import com.example.rikuwaapp.R;

import java.util.List;

public class AdapterUnidad extends RecyclerView.Adapter<AdapterUnidad.ViewHolder> {

    private Context context;
    private List<Unidad> listUnidad;

    private EventoClick eventoClick;

    public interface EventoClick {
        void onItemClick(ViewHolder holder, int posicion);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        AppCompatTextView nombreUnidad;
        AppCompatImageView estadoPersonas;

        ViewHolder(View itemView) {
            super(itemView);
            nombreUnidad = itemView.findViewById(R.id.nombreUnidad);
            estadoPersonas = itemView.findViewById(R.id.estadoPersonas);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            eventoClick.onItemClick(this, getAdapterPosition());
        }
    }

    public AdapterUnidad(List<Unidad> listUnidades, Context context,EventoClick click) {
        this.listUnidad = listUnidades;
        this.context = context;
        this.eventoClick = click;
    }

    @Override
    public int getItemCount() {
        return listUnidad.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_unidad, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Unidad obj = listUnidad.get(position);
        holder.nombreUnidad.setText(obj.getNombreUnidad());
        switch (obj.getEstadoPersonas()) {
            case "Verde":
                holder.estadoPersonas.setColorFilter(ContextCompat.getColor(context, R.color.estadoUnidad1), android.graphics.PorterDuff.Mode.MULTIPLY);
                break;
            case "Amarillo":
                holder.estadoPersonas.setColorFilter(ContextCompat.getColor(context, R.color.estadoUnidad2), android.graphics.PorterDuff.Mode.MULTIPLY);
                break;
            case "Rojo":
                holder.estadoPersonas.setColorFilter(ContextCompat.getColor(context, R.color.estadoUnidad3), android.graphics.PorterDuff.Mode.MULTIPLY);
                break;
        }
    }

}


