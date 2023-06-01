package com.example.couponsapp.controladores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.couponsapp.dbHelper.Control;
import com.example.couponsapp.modelos.Restaurante;

import java.security.spec.ECField;
import java.util.ArrayList;

public class RestauranteControl extends Control {
    public RestauranteControl(Context context) {
        super(context);
    }

    public long insertRestaurante(Restaurante restaurante){
        long id_res;
        ContentValues current = new ContentValues();
        current.put("ID_GOOGLE_MAPS", restaurante.getId_google_maps());
        current.put("NOMBRE_RESTAURANTE", restaurante.getNombre_restaurante());
        current.put("DIRECCION", restaurante.getDireccion());
        current.put("TELEFONO_RESTAURANTE", restaurante.getTelefono());
        current.put("LAT", restaurante.getLat());
        current.put("LONG",restaurante.getLng());
        current.put("URL_IMAGEN",restaurante.getUrl_imagen());
        try{
            this.abrir();
            id_res = db.insert("RESTAURANTE",null,current);
            this.cerrar();
        }catch (Exception e){
            Log.e("Insertar restaurante",e.getMessage());
            id_res=0;
        }
        return id_res;
    }
    public long updateRestaurante(Restaurante restaurante){
        long id_res;
        String []id={String.valueOf(restaurante.getId_restaurante())};
        ContentValues current = new ContentValues();
        current.put("ID_GOOGLE_MAPS", restaurante.getId_google_maps());
        current.put("NOMBRE_RESTAURANTE", restaurante.getNombre_restaurante());
        current.put("DIRECCION", restaurante.getDireccion());
        current.put("TELEFONO_RESTAURANTE", restaurante.getTelefono());
        current.put("LAT", restaurante.getLat());
        current.put("LONG",restaurante.getLng());
        current.put("URL_IMAGEN",restaurante.getUrl_imagen());
        try{
            this.abrir();
            id_res = db.update("RESTAURANTE",current,"ID_RESTAURANTE = ?",id);
            this.cerrar();
        }catch (Exception e){
            Log.e("Insertar restaurante",e.getMessage());
            id_res=0;
        }
        return id_res;
    }
    public Restaurante readRestaurante(int id_restaurante){
        Restaurante restaurante=new Restaurante();
        String []id={String.valueOf(id_restaurante)};
        try{
            this.abrir();
            Cursor results = db.rawQuery("select * from RESTAURANTE where ID_RESTAURANTE = ?", id);
            if(results.moveToFirst()){
                restaurante=new Restaurante(
                        results.getInt(0),
                        results.getString(1),
                        results.getString(2),
                        results.getString(3),
                        results.getString(4),
                        results.getDouble(5),
                        results.getDouble(6),
                        results.getString(7)
                );
            }
            this.cerrar();
        }catch (Exception e){
            Log.e("Error leer restaurante",e.getMessage());
        }
        return restaurante;
    }

    public ArrayList<Restaurante> traerRestaurante(String id_restaurante){
        this.abrir();
        String[] args = {id_restaurante};
        ArrayList<Restaurante> list = new ArrayList<>();
        Restaurante restaurante;
        //Cursor result = db.rawQuery("SELECT * FROM RESTAURANTE", null);
        Cursor results = db.rawQuery("SELECT RESTAURANTE.ID_RESTAURANTE, RESTAURANTE.DIRECCION, RESTAURANTE.NOMBRE_RESTAURANTE FROM\n" +
                "RESTAURANTE WHERE RESTAURANTE.ID_RESTAURANTE = ?", args);
        if(results.moveToFirst()){
            do {
                restaurante = new Restaurante(
                        results.getInt(0),
                        results.getString(2),
                        results.getString(1)
                );
                list.add(restaurante);
            }while (results.moveToNext());
        }
        results.close();
        this.cerrar();
        return list;
    }

    public ArrayList<Restaurante> all(){
        ArrayList<Restaurante> list = new ArrayList<>();
        try{
            this.abrir();
            Cursor results = db.rawQuery("select * from RESTAURANTE",null);
            while(results.moveToNext()){
                list.add(new Restaurante(
                        results.getInt(0),
                        results.getString(1),
                        results.getString(2),
                        results.getString(3),
                        results.getString(4),
                        results.getDouble(5),
                        results.getDouble(6),
                        results.getString(7)
                ));
            }
            results.close();
            this.cerrar();
        }catch (Exception e){
            Log.e("Error leer restaurantes",e.getMessage());
        }
        return list;
    }
    public ArrayList<Restaurante> filtrarRestaurante(String nombre){
        ArrayList<Restaurante> list = new ArrayList<>();
        String []target={"%"+nombre+"%"};
        try{
            this.abrir();
            Cursor results = db.rawQuery("select * from RESTAURANTE where NOMBRE_RESTAURANTE like ?",target);
            while(results.moveToNext()){
                list.add(new Restaurante(
                        results.getInt(0),
                        results.getString(1),
                        results.getString(2),
                        results.getString(3),
                        results.getString(4),
                        results.getDouble(5),
                        results.getDouble(6),
                        results.getString(7)
                ));
            }
            results.close();
            this.cerrar();
        }catch (Exception e){
            Log.e("Error leer restaurantes",e.getMessage());
        }
        return list;
    }
}
