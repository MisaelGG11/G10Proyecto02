package com.example.couponsapp.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASENAME = "cupones.s3db";
    private static final int DATABASEVERSION = 2;

    public DbHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS RESTAURANTE(" +
                "ID_RESTAURANTE INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "ID_GOOGLE_MAPS text, \n" +
                "NOMBRE_RESTAURANTE VARCHAR(40) NOT NULL, \n" +
                "DIRECCION TEXT not null, \n" +
                "TELEFONO_RESTAURANTE VARCHAR(14), \n"+
                "LAT DECIMAL(10,7) , \n" +
                "LONG DECIMAL(10,7), \n" +
                "URL_IMAGEN   text, \n" +
                "UNIQUE(ID_RESTAURANTE))");

        db.execSQL("CREATE TABLE IF NOT EXISTS TIPOCUPON(" +
                "ID_TIPO INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "NOMBRE_TIPO VARCHAR(40) NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS CUPON(" +
                "ID_CUPON INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "ID_RESTAURANTE INTEGER NOT NULL, \n" +
                "ID_TIPO INTEGER NOT NULL, \n" +
                "CODIGO_CUPON VARCHAR(5) NOT NULL UNIQUE, \n" +
                "NOMBRE_CUPON VARCHAR(35) NOT NULL, \n" +
                "DESCRIPCION_CUPON VARCHAR(50) NOT NULL, \n" +
                "PRECIO_CUPON DECIMAL(5,2) NOT NULL," +
                "HORARIO_CUPON VARCHAR(35) NOT NULL, \n" +
                "DISPONIBLE INTEGER NOT NULL," +
                "UNIQUE(ID_CUPON, ID_RESTAURANTE, ID_TIPO))");

        db.execSQL("CREATE TABLE IF NOT EXISTS ROL(" +
                "ID_ROL INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "NOMBRE_ROL VARCHAR(40) NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS PERMISO(" +
                "ID_PERMISO INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "NOMBRE_PERMISO VARCHAR(40) NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS ROLPERMISO(" +
                "ID_ROL_PERMISO INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "ID_ROL INTEGER NOT NULL, \n" +
                "ID_PERMISO INTEGER NOT NULL," +
                "UNIQUE(ID_ROL_PERMISO, ID_ROL, ID_PERMISO))");

        db.execSQL("CREATE TABLE IF NOT EXISTS USUARIO(" +
                "ID_USUARIO INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "ID_ROL INTEGER NOT NULL, \n" +
                "ID_RESTAURANTE INTEGER, \n" +
                "USERNAME VARCHAR(40) NOT NULL UNIQUE, \n" +
                "PASSWORD VARCHAR(40) NOT NULL, \n" +
                "EMAIL VARCHAR(100) NOT NULL UNIQUE, \n" +
                "NOMBRE VARCHAR(40) NOT NULL, \n" +
                "APELLIDO VARCHAR(40) NOT NULL, \n" +
                "TELEFONO VARCHAR(8) NOT NULL, \n" +
                "GOOGLE_USUARIO INTEGER NOT NULL, \n" +
                "URI_FOTO_PERFIL VARCHAR(45)     , \n" +
                "UNIQUE(ID_USUARIO, ID_ROL))");


        db.execSQL("CREATE TABLE IF NOT EXISTS REGISTROCUPON(" +
                "ID_REGISTRO INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "ID_CUPON INTEGER NOT NULL, \n" +
                "ID_USUARIO INTEGER NOT NULL, \n" +
                "FECHA_CANJEO VARCHAR(35) NOT NULL, \n" +
                "URI_PDF TEXT, \n" +
                "UNIQUE(ID_REGISTRO, ID_CUPON, ID_USUARIO))");


        /*              TRIGGERS              */

        db.execSQL("CREATE TRIGGER FK_CUPON_RESTAURANTE BEFORE INSERT " +
                "ON CUPON " +
                "FOR EACH ROW " +
                "BEGIN " +
                "SELECT CASE " +
                "WHEN ((SELECT ID_RESTAURANTE FROM RESTAURANTE WHERE ID_RESTAURANTE = NEW.ID_RESTAURANTE) IS NULL) " +
                "THEN RAISE(ABORT, 'No existe el restaurante!') " +
                "END; " +
                "END; ");

        db.execSQL("CREATE TRIGGER FK_CUPON_TIPO BEFORE INSERT " +
                "ON CUPON " +
                "FOR EACH ROW " +
                "BEGIN " +
                "SELECT CASE " +
                "WHEN ((SELECT ID_TIPO FROM TIPOCUPON WHERE ID_TIPO = NEW.ID_TIPO) IS NULL) " +
                "THEN RAISE(ABORT, 'No existe el tipo de cupon!') " +
                "END; " +
                "END; ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS RESTAURANTE");
        db.execSQL("DROP TABLE IF EXISTS TIPOCUPON");
        db.execSQL("DROP TABLE IF EXISTS CUPON");
        db.execSQL("DROP TABLE IF EXISTS USUARIO");
        db.execSQL("DROP TABLE IF EXISTS REGISTROCUPON");
    }
}
