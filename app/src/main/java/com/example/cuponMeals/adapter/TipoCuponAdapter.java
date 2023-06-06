package com.example.cuponMeals.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cuponMeals.R;
import com.example.cuponMeals.controladores.TipoCuponControl;
import com.example.cuponMeals.modelos.TipoCupon;

import java.util.ArrayList;

public class TipoCuponAdapter extends BaseAdapter {
    Context context;
    TipoCuponControl control;
    LayoutInflater layoutInflater;
    ArrayList<TipoCupon> items;

    //variables del custom dialog
    EditText nombre,codigo;

    public TipoCuponAdapter(Context context ) {
        this.control = new TipoCuponControl(context);
        this.items=control.trarTipoCupones();
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public TipoCupon getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View customView, ViewGroup parent) {
        TextView nombre,codigo;
        if(customView == null){
            customView = LayoutInflater.from(context).inflate(R.layout.item_tipo_cupon,parent,false);
        }else{
            customView=layoutInflater.inflate(R.layout.item_tipo_cupon,parent,false);
        }
        nombre=customView.findViewById(R.id.nombre_tipoCupon);
        nombre.setText(items.get(position).getNombre_tipo());
        return customView;
    }
}
