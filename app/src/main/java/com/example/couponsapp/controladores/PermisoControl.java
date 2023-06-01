package com.example.couponsapp.controladores;

import android.content.ContentValues;
import android.content.Context;

import com.example.couponsapp.dbHelper.Control;
import com.example.couponsapp.modelos.Permiso;
import com.example.couponsapp.modelos.Rol;

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
