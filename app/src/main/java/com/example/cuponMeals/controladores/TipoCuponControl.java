package com.example.cuponMeals.controladores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.example.cuponMeals.dbHelper.Control;
import com.example.cuponMeals.modelos.TipoCupon;

import java.util.ArrayList;

public class TipoCuponControl extends Control {
    public TipoCuponControl(Context context) {
        super(context);
    }

    public long insertTipoCupon(TipoCupon tipoCupon){
        this.abrir();
        long id_ret;
        ContentValues current = new ContentValues();
        current.put("NOMBRE_TIPO", tipoCupon.getNombre_tipo());
        id_ret = db.insert("TIPOCUPON", null, current);
        this.cerrar();
        return id_ret;
    }

    public ArrayList<TipoCupon> trarTipoCupones(){
        this.abrir();
        ArrayList<TipoCupon> list = new ArrayList<>();
        TipoCupon tipoCupon;
        Cursor result = db.rawQuery("SELECT * FROM TIPOCUPON", null);
        if(result.moveToFirst()){
            do {
                tipoCupon = new TipoCupon(
                        result.getInt(0),
                        result.getString(1)
                );


                list.add(tipoCupon);
            }while (result.moveToNext());
        }
        result.close();
        this.cerrar();
        return list;
    }
}
