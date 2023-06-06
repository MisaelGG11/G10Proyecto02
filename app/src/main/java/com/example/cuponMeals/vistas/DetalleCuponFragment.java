package com.example.cuponMeals.vistas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuponMeals.R;
import com.example.cuponMeals.controladores.RegistrarCuponControl;
import com.example.cuponMeals.modelos.Cupon;
import com.example.cuponMeals.modelos.RegistrarCupon;
import com.example.cuponMeals.modelos.Usuario;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DetalleCuponFragment extends Fragment {

    TextView cuponName, cuponDes, cuponHor, cuponDis, cuponPrec, rest;
    Button btnCanjear;

    String name, desc, horario, restName, restDir;
    int id_cupon, disponible, id_usuario;
    double precio;
    Date date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle dataRes = getArguments();
        View root = inflater.inflate(R.layout.fragment_detalle_cupon, container, false);

        RegistrarCuponControl registrarCuponControl = new RegistrarCuponControl(root.getContext());

        id_cupon = dataRes.getInt("id_cupon");
        id_usuario = dataRes.getInt("id_userD");
        name = dataRes.getString("nombre_cupon");
        desc = dataRes.getString("descripcion_cupon");
        horario = dataRes.getString("horario_cupon");
        precio = dataRes.getDouble("precio_cupon");
        disponible = dataRes.getInt("disponible");
        restName = dataRes.getString("nombre_restaurante");
        restDir = dataRes.getString("direccion");

        cuponName = root.findViewById(R.id.txtCuponName);
        cuponDes =  root.findViewById(R.id.txtCuponDes);
        cuponHor = root.findViewById(R.id.txtHorario);
        cuponDis = root.findViewById(R.id.txtCuponDis);
        cuponPrec = root.findViewById(R.id.txtCuponPrice);
        rest = root.findViewById(R.id.txtRestName);
        btnCanjear = root.findViewById(R.id.btnCanjear);

        //Asignar valor
        cuponPrec.setText("$ "+String.valueOf(precio));
        cuponName.setText(name);
        cuponDes.setText(desc);
        cuponHor.setText(horario);
        rest.setText(restName);
        if (disponible == 0){
            cuponDis.setText("No está disponible");
            btnCanjear.setEnabled(false);
        }
        else {
            cuponDis.setText("Disponible");
            btnCanjear.setEnabled(true);
        }

        if(ActivityCompat.checkSelfPermission(root.getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                root.getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(), new  String[]{Manifest.permission.SEND_SMS,},1000);
        }else{};

        btnCanjear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar calendar = Calendar.getInstance();
                date = calendar.getTime();
                String dateForm = sdf.format(date);

                try {
                    Cupon cupon = new Cupon();
                    cupon.setId_cupon(id_cupon);
                    Usuario usuario = new Usuario();
                    usuario.setId_usuario(id_usuario);
                    registrarCuponControl.insertRegistroCupon(new RegistrarCupon(
                            0,
                            cupon,
                            usuario,
                            dateForm
                    ));
                    Toast.makeText(root.getContext(), "Su cupón ha sido canjeado exitosamente", Toast.LENGTH_SHORT).show();
                    enviarMensaje(name, id_usuario,dateForm, root);
                }
                catch (SQLiteException sql){
                    sql.printStackTrace();
                }
            }
        });

        return root;
    }

    //Envio de registro ok
    public void enviarMensaje(String nombre, int idDeIsuario , String fecha, View view){
        String mensaje = "Se a canjeado el cupon "+nombre+", Fecha: "+fecha + "Por el usuario con id: " + idDeIsuario ;
        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage("79656302",null,mensaje,null,null);
            Toast.makeText(view.getContext(), "Se envio el MSM", Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Toast.makeText(view.getContext(), "El Mensaje no se envio, revise los datos.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}