package com.example.couponsapp.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class Control {
    private Context context;
    private DbHelper dbHelper;
    public SQLiteDatabase db;

    public Control(Context context) {
        this.context = context;
        this.dbHelper = new DbHelper(context);
    }

    public void abrir() throws SQLiteException{
        db = dbHelper.getWritableDatabase();
    }

    public void cerrar() throws SQLiteException{
        dbHelper.close();
    }

}
