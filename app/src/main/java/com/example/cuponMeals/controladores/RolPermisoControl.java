package com.example.cuponMeals.controladores;

import android.content.ContentValues;
import android.content.Context;

import com.example.cuponMeals.dbHelper.Control;
import com.example.cuponMeals.modelos.RolPermiso;

public class RolPermisoControl extends Control {
    public RolPermisoControl(Context context) {
        super(context);
    }

    public long insertRolPermiso(RolPermiso rolPermiso){
        this.abrir();
        long id_res;
        ContentValues current = new ContentValues();
        current.put("ID_ROL", rolPermiso.getId_rol());
        current.put("ID_PERMISO", rolPermiso.getId_permiso());
        id_res = db.insert("ROLPERMISO", null, current);
        this.cerrar();
        return  id_res;
    }
}
