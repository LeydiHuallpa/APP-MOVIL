package com.example.rikuwaapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rikuwaapp.Entidad.Horario;
import com.example.rikuwaapp.R;

import java.util.List;

//public class AdapterHorario {
//}
public class AdapterHorario extends RecyclerView.Adapter<AdapterHorario.ViewHolder> {

    private Context context;
    private List<Horario> listHorario;

    private EventoClick eventoClick;

    public interface EventoClick {
        void onItemClick(ViewHolder holder, int posicion);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        AppCompatTextView nombreHorario;
        AppCompatImageView estadoPersonas;

        ViewHolder(View itemView) {
            super(itemView);
            nombreHorario = itemView.findViewById(R.id.nombreHorario);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            eventoClick.onItemClick(this, getAdapterPosition());
        }
    }

    public AdapterHorario(List<Horario> listHorarioes, Context context,EventoClick click) {
        this.listHorario = listHorarioes;
        this.context = context;
        this.eventoClick = click;
    }

    @Override
    public int getItemCount() {
        return listHorario.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horario, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Horario obj = listHorario.get(position);
        holder.nombreHorario.setText(obj.getNombreUnidad());
    }

}



