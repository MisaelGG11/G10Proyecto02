package com.example.couponsapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.couponsapp.controladores.RestauranteControl;
import com.example.couponsapp.modelos.Restaurante;
import com.example.couponsapp.R;
import com.example.couponsapp.modelos.Usuario;
import com.example.couponsapp.vistas.MapsRestaurantes;
import com.example.couponsapp.vistas.RestauranteFragment;
import com.example.couponsapp.vistas.UsuarioFragment;

import java.util.ArrayList;

public class RestauranteAdapter extends BaseAdapter {
    Context context;
    RestauranteControl control;
    LayoutInflater layoutInflater;
    ArrayList<Restaurante> items;

    //variables del custom dialog
    EditText nombre,codigo;

    public RestauranteAdapter(Context context ) {
        this.control = new RestauranteControl(context);
        this.items=control.all();
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
    }

    public void crear(View view,FragmentManager fragmentManager) {
        Bundle args=new Bundle();
        args.putBoolean("is_new",true);
        args.putBoolean("is_admin",true);
        Fragment fragment=new RestauranteFragment();
        fragment.setArguments(args);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    public  void editar(View view, FragmentManager fragmentManager, Restaurante restaurante){
        Bundle args=new Bundle();
        args.putBoolean("is_new",false);
        args.putBoolean("is_admin",true);
        args.putInt("id_restaurante",restaurante.getId_restaurante());
        Fragment fragment=new RestauranteFragment();
        fragment.setArguments(args);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void eliminar(int position){

       /* Municipio Municipio=getItem(position);
        int result=control.delete(Municipio.getId_municipio());
        boolean isDeleted=result>0;
        if (isDeleted){
            removeItem(position);
            notifyDataSetChanged();
            Toast.makeText(context,R.string.eliminado,Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,R.string.no_eliminado,Toast.LENGTH_LONG).show();

        }*/
    }
    public void filtrar(String busqueda) {
        items= control.filtrarRestaurante(busqueda);
        notifyDataSetChanged();

    }
    //Para abrir mapa
    public  void ver_mapa(View view, FragmentManager fragmentManager, Restaurante restaurante){
        Bundle args=new Bundle();
        args.putBoolean("is_new",false);
        args.putBoolean("is_admin",true);
        args.putInt("id_restaurante",restaurante.getId_restaurante());
        Fragment fragment=new MapsRestaurantes();
        fragment.setArguments(args);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Restaurante getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void removeItem(int i){
        items.remove(i);
    }
    public void addItem(Restaurante item){
        items.add(item);
    }
    public void replaceItem(int position,Restaurante item){
        items.set(position,item);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View customView, ViewGroup parent) {
        TextView nombre,telefono,direccion,codigo;
        Restaurante restaurante=items.get(position);
        if(customView == null){
            customView = LayoutInflater.from(context).inflate(R.layout.item_restaurante,parent,false);
        }else{
            customView=layoutInflater.inflate(R.layout.item_restaurante,parent,false);
        }
        nombre=customView.findViewById(R.id.nombre);
        codigo=customView.findViewById(R.id.codigo);
        telefono=customView.findViewById(R.id.telefono);
        direccion=customView.findViewById(R.id.direccion);
        nombre.setText(restaurante.getNombre_restaurante());
        telefono.setText(restaurante.getTelefono());
        direccion.setText(restaurante.getDireccion());
        codigo.setText(String.valueOf(restaurante.getId_restaurante()));
        return customView;
    }
}
