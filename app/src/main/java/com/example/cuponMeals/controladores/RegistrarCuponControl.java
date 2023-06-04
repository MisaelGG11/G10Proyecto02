package com.example.cuponMeals.controladores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.cuponMeals.dbHelper.Control;
import com.example.cuponMeals.modelos.Cupon;
import com.example.cuponMeals.modelos.RegistrarCupon;
import com.example.cuponMeals.modelos.Restaurante;
import com.example.cuponMeals.modelos.TipoCupon;
import com.example.cuponMeals.modelos.Usuario;

import java.util.ArrayList;

public class RegistrarCuponControl extends Control {
    public RegistrarCuponControl(Context context) {
        super(context);
    }

    public long insertRegistroCupon(RegistrarCupon registrarCupon){
        this.abrir();
        long id_res;
        ContentValues current = new ContentValues();
        current.put("ID_CUPON", registrarCupon.getCupon().getId_cupon());
        current.put("ID_USUARIO", registrarCupon.getUsuario().getId_usuario());
        current.put("FECHA_CANJEO", registrarCupon.getFecha_registro());
        current.put("URI_PDF","");
        id_res = db.insert("REGISTROCUPON", null, current);
        this.cerrar();
        return  id_res;
    }

    public String get_uri_pdf(int id_registro){
        String uri="";
        String []args={String.valueOf(id_registro)};
        this.abrir();
        Cursor result=db.rawQuery("Select URI_PDF from REGISTROCUPON where ID_REGISTRO = ?",args);
        if(result.moveToFirst()){
            uri=result.getString(0);
        }
        result.close();
        this.cerrar();
        return uri;
    }

    public int set_uri_pdf(int id_registro,String uri){
        String []args={String.valueOf(id_registro)};
        int id_res;
        ContentValues current = new ContentValues();
        current.put("URI_PDF",uri);
        this.abrir();
        id_res = db.update("REGISTROCUPON", current, "ID_REGISTRO = ?", args);
        this.cerrar();
        return id_res;
    }
    public ArrayList<RegistrarCupon> traerMisCupones(int id_user){
        this.abrir();
        ArrayList<RegistrarCupon> list = new ArrayList<>();
        Cupon cupon;
        Restaurante restaurante;
        TipoCupon tipoCupon;
        RegistrarCupon registrarCupon;
        Usuario usuario;
        Cursor result = db.rawQuery("SELECT REGISTROCUPON.ID_REGISTRO, CUPON.ID_CUPON, CUPON.NOMBRE_CUPON, CUPON.DESCRIPCION_CUPON, CUPON.CODIGO_CUPON, TIPOCUPON.NOMBRE_TIPO, RESTAURANTE.NOMBRE_RESTAURANTE, RESTAURANTE.DIRECCION, CUPON.HORARIO_CUPON, CUPON.DISPONIBLE, CUPON.PRECIO_CUPON, REGISTROCUPON.FECHA_CANJEO FROM CUPON, TIPOCUPON, RESTAURANTE, REGISTROCUPON, USUARIO WHERE CUPON.ID_RESTAURANTE = RESTAURANTE.ID_RESTAURANTE AND CUPON.ID_TIPO = TIPOCUPON.ID_TIPO  AND CUPON.ID_CUPON = REGISTROCUPON.ID_CUPON AND USUARIO.ID_USUARIO = REGISTROCUPON.ID_USUARIO AND USUARIO.ID_USUARIO = ?", new String[]{String.valueOf(id_user)});

        if(result.moveToFirst()){
            do {
                restaurante = new Restaurante();
                restaurante.setNombre_restaurante(result.getString(6));
                restaurante.setDireccion(result.getString(7));
                tipoCupon = new TipoCupon();
                tipoCupon.setNombre_tipo(result.getString(5));
                cupon = new Cupon(
                        result.getInt(1),
                        restaurante,
                        tipoCupon,
                        result.getString(4),
                        result.getString(2),
                        result.getString(3),
                        result.getDouble(10),
                        result.getString(8),
                        result.getInt(9)
                );
                usuario = new Usuario();
                usuario.setId_usuario(id_user);
                registrarCupon = new RegistrarCupon(result.getInt(0), cupon, usuario, result.getString(11));
                list.add(registrarCupon);
            }while (result.moveToNext());
        }
        result.close();
        this.cerrar();
        return list;
    }
}
