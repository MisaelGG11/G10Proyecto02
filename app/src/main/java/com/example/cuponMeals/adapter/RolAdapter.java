package com.example.cuponMeals.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import com.example.cuponMeals.controladores.RolControl;
import com.example.cuponMeals.modelos.Rol;
import java.util.ArrayList;
import com.example.cuponMeals.R;

public class RolAdapter extends BaseAdapter {
    Context context;
    RolControl control;
    LayoutInflater layoutInflater;
    ArrayList <Rol> items;

    //variables del custom dialog
    EditText nombre;

    public RolAdapter(Context context ) {
        this.control = new RolControl(context);
        this.items=control.readMany();
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Rol getItem(int i) {
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
        TextView descripcion,codigo;
        if(customView == null){
            customView = LayoutInflater.from(context).inflate(R.layout.item_rol,parent,false);
        }else{
            customView=layoutInflater.inflate(R.layout.item_rol,parent,false);
        }
        descripcion=customView.findViewById(R.id.nombre_rol);
        descripcion.setText(items.get(position).getNombre_rol());
        return customView;
    }
}
