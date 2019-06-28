package pe.edu.idat.appbarberia.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pe.edu.idat.appbarberia.API.Sesion;
import pe.edu.idat.appbarberia.Modelos.Parcel.BarberoModel;
import pe.edu.idat.appbarberia.R;
import pe.edu.idat.appbarberia.Servicios;

public class BarberoAdapter extends RecyclerView.Adapter<BarberoAdapter.ViewHolder>{

    private Context context;
    private ArrayList<BarberoModel> lista;

    Sesion sesionAd = new Sesion();

    public BarberoAdapter(Context context) {
        this.context = context;
        lista = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.barbero_detalle,parent,false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ViewHolder viewHolder = holder;
        final BarberoModel item = lista.get(position);
        viewHolder.nombre.setText(item.getNombreBarbero());
        final String mensaje = String.valueOf(item.getIdBarbero());
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sesionAd.setIdBarbero(item.getIdBarbero());
                Toast.makeText(context,"id del barbero "+mensaje,Toast.LENGTH_LONG).show();
                Intent intServicio = new Intent(context, Servicios.class);
                intServicio.putExtra("sesion",sesionAd);
                context.startActivity(intServicio);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre;
        Button button;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.barb_edt_name);
            cardView = itemView.findViewById(R.id.cardBarbero);
            button = itemView.findViewById(R.id.barb_btnServicio);
        }
    }

    public void agregarElemento(ArrayList<BarberoModel> data, Sesion sesion){

        sesionAd = sesion;
        lista.clear();
        lista.addAll(data);
        notifyDataSetChanged();

    }

}
