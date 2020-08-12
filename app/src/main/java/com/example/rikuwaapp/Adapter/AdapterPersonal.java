package com.example.rikuwaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rikuwaapp.Entidad.Personal;
import com.example.rikuwaapp.R;

import java.util.List;

public class AdapterPersonal extends RecyclerView.Adapter<AdapterPersonal.ViewHolder> {

    private Context context;
    private List<Personal> listPersonal;

    private AdapterPersonal.EventoClick eventoClick;

    public interface EventoClick {
        void onItemClick(AdapterPersonal.ViewHolder holder, int posicion);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AppCompatTextView nombreCompleto;
        AppCompatTextView nombreUnidad;

        ViewHolder(View itemView) {
            super(itemView);
            nombreCompleto = itemView.findViewById(R.id.nombreCompleto);
            nombreUnidad = itemView.findViewById(R.id.nombreUnidad);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            eventoClick.onItemClick(this, getAdapterPosition());
        }
    }

    public AdapterPersonal(List<Personal> listPersonales, Context context, AdapterPersonal.EventoClick click) {
        this.listPersonal = listPersonales;
        this.context = context;
        this.eventoClick = click;
    }

    @Override
    public int getItemCount() {
        return listPersonal.size();
    }

    @Override
    public AdapterPersonal.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personal, parent, false);
        return new AdapterPersonal.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterPersonal.ViewHolder holder, int position) {
        Personal obj = listPersonal.get(position);
        holder.nombreCompleto.setText(obj.getNombreCompleto());
        holder.nombreUnidad.setText(obj.getUnidadNombre());
    }

}



