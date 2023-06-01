package com.example.couponsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.couponsapp.R;
import com.example.couponsapp.modelos.Cupon;

import java.util.ArrayList;

public class ListAdapterCupon extends RecyclerView.Adapter<ListAdapterCupon.ViewHolder>{

    private ArrayList<Cupon> listaCupones;
    private LayoutInflater mInflater;
    private Context context;
    final ListAdapterCupon.OnItemClickListener listener;

    public ListAdapterCupon(ArrayList<Cupon> listaTipoCupon, Context context, ListAdapterCupon.OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.listaCupones = listaTipoCupon;
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(Cupon cupon);
    }

    @Override
    public int getItemCount() {
        return listaCupones.size();
    }

    @Override
    public ListAdapterCupon.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.lista_cupones, null);
        return new ListAdapterCupon.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterCupon.ViewHolder holder, final int position){
        holder.bindData(listaCupones.get(position));
    }

    public void setItems(ArrayList<Cupon> items){listaCupones = items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView catImage;
        TextView nombreCup, resCup, horarioCup;

        ViewHolder(View itemView){
            super(itemView);
            nombreCup = itemView.findViewById(R.id.cuponName);
            resCup = itemView.findViewById(R.id.restName);
            horarioCup = itemView.findViewById(R.id.cuponHorario);
        }

        void bindData(final Cupon item){
            nombreCup.setText(item.getNombre_cupon());
            resCup.setText(item.getRestaurante().getNombre_restaurante());
            horarioCup.setText(item.getHorario_cupon());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
