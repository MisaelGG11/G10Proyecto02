package com.example.couponsapp.controladores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.couponsapp.dbHelper.Control;
import com.example.couponsapp.modelos.Cupon;
import com.example.couponsapp.modelos.Restaurante;
import com.example.couponsapp.modelos.TipoCupon;

import java.util.ArrayList;

public class CuponControl extends Control {
    public CuponControl(Context context) {
        super(context);
    }

    public long insertCupon(Cupon cupon){
        this.abrir();
        long id_res;
        ContentValues current = new ContentValues();
        current.put("ID_RESTAURANTE", cupon.getRestaurante().getId_restaurante());
        current.put("ID_TIPO", cupon.getTipoCupon().getId_tipoCupon());
        current.put("CODIGO_CUPON", cupon.getCodigo_cupon());
        current.put("NOMBRE_CUPON", cupon.getNombre_cupon());
        current.put("DESCRIPCION_CUPON", cupon.getDescripcion_cupon());
        current.put("PRECIO_CUPON", cupon.getPrecio());
        current.put("HORARIO_CUPON", cupon.getHorario_cupon());
        current.put("DISPONIBLE", cupon.getDisponible());
        id_res = db.insert("CUPON", null, current);
        this.cerrar();
        return id_res;
    }

    public int updateCupon(Cupon cupon){
        this.abrir();
        String[] args = {String.valueOf(cupon.getId_cupon())};
        int id_res;
        ContentValues current = new ContentValues();
        current.put("ID_RESTAURANTE", cupon.getRestaurante().getId_restaurante());
        current.put("ID_TIPO", cupon.getTipoCupon().getId_tipoCupon());
        current.put("CODIGO_CUPON", cupon.getCodigo_cupon());
        current.put("NOMBRE_CUPON", cupon.getNombre_cupon());
        current.put("DESCRIPCION_CUPON", cupon.getDescripcion_cupon());
        current.put("PRECIO_CUPON", cupon.getPrecio());
        current.put("HORARIO_CUPON", cupon.getHorario_cupon());
        current.put("DISPONIBLE", cupon.getDisponible());
        id_res = db.update("CUPON", current, "ID_CUPON = ?", args);
        this.cerrar();
        return id_res;
    }

    public int deleteCupon(int id_cupon){
        this.abrir();
        int id_del = db.delete("CUPON", "ID_CUPON = ?", new String[]{String.valueOf(id_cupon)});
        this.cerrar();
        return id_del;
    }

    public ArrayList<Cupon> traerCupones(String tipo){
        this.abrir();
        ArrayList<Cupon> list = new ArrayList<>();
        String[] args = {"%"+tipo+"%"};
        Cupon cupon;
        Restaurante restaurante;
        TipoCupon tipoCupon;
        Cursor result;
        result = db.rawQuery("SELECT CUPON.ID_CUPON, CUPON.NOMBRE_CUPON, CUPON.DESCRIPCION_CUPON, CUPON.CODIGO_CUPON, TIPOCUPON.NOMBRE_TIPO, RESTAURANTE.NOMBRE_RESTAURANTE, DIRECCION, CUPON.HORARIO_CUPON, CUPON.DISPONIBLE, CUPON.PRECIO_CUPON FROM CUPON, TIPOCUPON, RESTAURANTE WHERE CUPON.ID_RESTAURANTE = RESTAURANTE.ID_RESTAURANTE AND CUPON.ID_TIPO = TIPOCUPON.ID_TIPO AND TIPOCUPON.NOMBRE_TIPO LIKE ?", args);


        if(result.moveToFirst()){
            do {
                restaurante = new Restaurante();
                restaurante.setNombre_restaurante(result.getString(5));
                restaurante.setDireccion(result.getString(6));
                tipoCupon = new TipoCupon();
                tipoCupon.setNombre_tipo(result.getString(4));
                cupon = new Cupon(
                        result.getInt(0),
                        restaurante,
                        tipoCupon,
                        result.getString(3),
                        result.getString(1),
                        result.getString(2),
                        result.getDouble(9),
                        result.getString(7),
                        result.getInt(8)
                );
                list.add(cupon);
            }while (result.moveToNext());
        }
        result.close();
        this.cerrar();
        return list;
    }

    public ArrayList<Cupon> all(){
        this.abrir();
        ArrayList<Cupon> list = new ArrayList<>();
        Cupon cupon;
        Restaurante restaurante;
        TipoCupon tipoCupon;
        Cursor result;
        result = db.rawQuery("SELECT CUPON.ID_CUPON, CUPON.NOMBRE_CUPON, CUPON.DESCRIPCION_CUPON, CUPON.CODIGO_CUPON, TIPOCUPON.NOMBRE_TIPO, RESTAURANTE.NOMBRE_RESTAURANTE, DIRECCION, CUPON.HORARIO_CUPON, CUPON.DISPONIBLE, CUPON.PRECIO_CUPON FROM CUPON, TIPOCUPON, RESTAURANTE WHERE CUPON.ID_RESTAURANTE = RESTAURANTE.ID_RESTAURANTE AND CUPON.ID_TIPO = TIPOCUPON.ID_TIPO ", null);
        if(result.moveToFirst()){
            do {

                restaurante = new Restaurante();
                restaurante.setNombre_restaurante(result.getString(5));
                restaurante.setDireccion(result.getString(6));
                tipoCupon = new TipoCupon();
                tipoCupon.setNombre_tipo(result.getString(4));
                cupon = new Cupon(
                        result.getInt(0),
                        restaurante,
                        tipoCupon,
                        result.getString(3),
                        result.getString(1),
                        result.getString(2),
                        result.getDouble(9),
                        result.getString(7),
                        result.getInt(8)
                );
                list.add(cupon);
            }while (result.moveToNext());
        }
        result.close();
        this.cerrar();
        return list;
    }
}
