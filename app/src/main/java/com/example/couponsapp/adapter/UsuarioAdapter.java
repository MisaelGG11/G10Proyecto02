package com.example.couponsapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.example.couponsapp.controladores.UsuarioControl;
import com.example.couponsapp.modelos.Usuario;
import com.example.couponsapp.R;
import com.example.couponsapp.vistas.UsuarioFragment;

import java.util.ArrayList;

public class UsuarioAdapter extends BaseAdapter {
    Context context;
    UsuarioControl control;
    LayoutInflater layoutInflater;
    ArrayList<Usuario> items;


    public UsuarioAdapter(Context context ) {
        this.control = new UsuarioControl(context);
        this.items=control.readAllUsuario();
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
    }

    public void crear(View view,FragmentManager fragmentManager) {
        Bundle args=new Bundle();
        args.putBoolean("is_new",true);
        args.putBoolean("is_admin",true);
        Fragment fragment=new UsuarioFragment();
        fragment.setArguments(args);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    public  void editar(View view,FragmentManager fragmentManager,Usuario usuarioTarget){
        Bundle args=new Bundle();
        args.putBoolean("is_new",false);
        args.putBoolean("is_admin",true);
        args.putInt("id_usuario",usuarioTarget.getId_usuario());
        Fragment fragment=new UsuarioFragment();
        fragment.setArguments(args);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }
    public void eliminar(int position){

        Usuario usuario=getItem(position);
        int result=control.deleteUsuario(usuario.getId_usuario());
        boolean isDeleted=result>0;
        if (isDeleted){
            removeItem(position);
            notifyDataSetChanged();
            Toast.makeText(context,R.string.eliminado,Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,R.string.no_eliminado,Toast.LENGTH_LONG).show();

        }
    }
    public void filtrar(String busqueda) {
        items=control.readMany(busqueda);
        notifyDataSetChanged();

    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Usuario getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void removeItem(int i){
        items.remove(i);
    }
    public void addItem(Usuario item){
        items.add(item);
    }

    @Override
    public View getView(int position, View customView, ViewGroup parent) {
        TextView nombre,username,email, apellido, password, telefono, rol;
        if(customView == null){
            customView = LayoutInflater.from(context).inflate(R.layout.item_usuario,parent,false);
        }else{
            customView=layoutInflater.inflate(R.layout.item_usuario,parent,false);
        }
        nombre=customView.findViewById(R.id.nombre_usuario_crud);
        username=customView.findViewById(R.id.username_crud);
        apellido=customView.findViewById(R.id.apellid_usuario_crud);
        rol=customView.findViewById(R.id.rol_crud);
        nombre.setText(items.get(position).getNombre());
        username.setText(items.get(position).getUsername());
        apellido.setText(items.get(position).getApellido());
        rol.setText(String.valueOf(items.get(position).getId_rol()));
        return customView;
    }
}
