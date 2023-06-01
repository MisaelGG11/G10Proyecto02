package com.example.couponsapp.controladores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.couponsapp.dbHelper.Control;
import com.example.couponsapp.modelos.Rol;

import java.util.ArrayList;

public class RolControl extends Control {
    public RolControl(Context context) {
        super(context);
    }

    public long insertRol(Rol rol){
        this.abrir();
        long id_res;
        ContentValues current = new ContentValues();
        current.put("NOMBRE_ROL", rol.getNombre_rol());
        id_res = db.insert("ROL", null, current);
        this.cerrar();
        return  id_res;
    }
    public int getRolID(String nombre_Rol) {

        String[] args = new String[]{nombre_Rol};
        String[] columns = {"ID_ROL"};
        int rol;
        this.abrir();
        Cursor results = db.query("ROL", columns, "NOMBRE_ROL = ?", args, null, null, null);
        if (results.moveToFirst()) {
            rol=results.getInt(0);
        } else {
            rol=0;
        }
        this.cerrar();
        return rol;
    }
    public String getRol(int id) {

        String[] args = new String[]{String.valueOf(id)};
        String[] columns = {"NOMBRE_ROL"};
        String rol;
        this.abrir();
        Cursor results = db.query("ROL", columns, "ID_ROL = ?", args, null, null, null);
        if (results.moveToFirst()) {
            rol=results.getString(0);
        } else {
            rol="";
        }
        this.cerrar();
        return rol;
    }
    public ArrayList<Rol> readMany() {

        ArrayList <Rol> roles=new ArrayList<>();
        String[] columns = {"ID_ROL","NOMBRE_ROL"};
        this.abrir();
        Cursor results = db.query("ROL", columns,null ,null, null, null, null);
        while (results.moveToNext()) {
            roles.add(new Rol(
                    results.getInt(0),
                    results.getString(1)
            ));
        }
        this.cerrar();
        return roles;
    }

}
