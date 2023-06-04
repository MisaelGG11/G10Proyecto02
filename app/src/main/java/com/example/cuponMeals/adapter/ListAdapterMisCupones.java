package com.example.cuponMeals.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cuponMeals.R;
import com.example.cuponMeals.modelos.RegistrarCupon;

import java.util.ArrayList;

public class ListAdapterMisCupones extends RecyclerView.Adapter<ListAdapterMisCupones.ViewHolder>{

    private ArrayList<RegistrarCupon> listaRegistros;
    private LayoutInflater mInflater;
    private Context context;
    final ListAdapterMisCupones.OnItemClickListener listener;

    public ListAdapterMisCupones(ArrayList<RegistrarCupon> listaRegistros, Context context, ListAdapterMisCupones.OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listaRegistros = listaRegistros;
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(RegistrarCupon registrarCupon);
    }

    @Override
    public int getItemCount() {
        return listaRegistros.size();
    }

    @Override
    public ListAdapterMisCupones.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.lista_mi_cupon, null);
        return new ListAdapterMisCupones.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterMisCupones.ViewHolder holder, final int position){
        holder.bindData(listaRegistros.get(position));
    }

    public void setItems(ArrayList<RegistrarCupon> items){listaRegistros = items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombreCup, resCup, fecha, precio;

        ViewHolder(View itemView){
            super(itemView);
            nombreCup = itemView.findViewById(R.id.miCuponName);
            resCup = itemView.findViewById(R.id.miRestName);
            fecha = itemView.findViewById(R.id.miCuponRegistro);
            precio = itemView.findViewById(R.id.miCuponPrecio);
        }

        void bindData(final RegistrarCupon item){
            nombreCup.setText(item.getCupon().getNombre_cupon());
            resCup.setText(item.getCupon().getRestaurante().getNombre_restaurante());
            fecha.setText(item.getFecha_registro());
            precio.setText("$ "+item.getCupon().getPrecio().toString());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}
