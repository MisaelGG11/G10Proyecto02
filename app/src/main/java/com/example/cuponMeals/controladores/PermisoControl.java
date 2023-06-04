package com.example.cuponMeals.controladores;

import android.content.ContentValues;
import android.content.Context;

import com.example.cuponMeals.dbHelper.Control;
import com.example.cuponMeals.modelos.Permiso;

public class PermisoControl extends Control {
    public PermisoControl(Context context) {
        super(context);
    }

    public long insertPermiso(Permiso permiso){
        this.abrir();
        long id_res;
        ContentValues current = new ContentValues();
        current.put("NOMBRE_PERMISO", permiso.getNombre_permiso());
        id_res = db.insert("PERMISO", null, current);
        this.cerrar();
        return  id_res;
    }
}
