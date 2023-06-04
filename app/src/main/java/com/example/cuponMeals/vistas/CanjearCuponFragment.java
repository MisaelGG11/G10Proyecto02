package com.example.cuponMeals.vistas;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cuponMeals.R;
import com.example.cuponMeals.adapter.ListAdapterCupon;
import com.example.cuponMeals.controladores.CuponControl;
import com.example.cuponMeals.modelos.Cupon;
import java.util.ArrayList;

public class CanjearCuponFragment extends Fragment {

    ArrayList<Cupon> listaCupones;
    Button des, alm, cen, sna;
    RecyclerView recyclerView;
    String tipoCurrent;

    int id_usuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle data = getArguments();
        View root = inflater.inflate(R.layout.fragment_canjear_cupon, container, false);

        CuponControl cuponControl = new CuponControl(root.getContext());

        des = (Button) root.findViewById(R.id.btnDes);
        alm = (Button) root.findViewById(R.id.btnAlm);
        cen = (Button) root.findViewById(R.id.btnCen);
        sna = (Button) root.findViewById(R.id.btnSna);

        id_usuario = data.getInt("id_user");
        listaCupones = cuponControl.traerCupones("");
        llenarLista(root, listaCupones);

        des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipoCurrent = "Desayuno";
                listaCupones = cuponControl.traerCupones(tipoCurrent);
                llenarLista(root, listaCupones);
            }
        });

        alm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipoCurrent = "Almuerzo";
                listaCupones = cuponControl.traerCupones(tipoCurrent);
                llenarLista(root, listaCupones);
            }
        });

        cen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipoCurrent = "Cena";
                listaCupones = cuponControl.traerCupones(tipoCurrent);
                llenarLista(root, listaCupones);
            }
        });

        sna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipoCurrent = "Snacks";
                listaCupones = cuponControl.traerCupones(tipoCurrent);
                llenarLista(root, listaCupones);
            }
        });


        return root;
    }

    public void llenarLista(View view, ArrayList<Cupon> lis){
        try {
            ListAdapterCupon listAdapterCat = new ListAdapterCupon(lis, view.getContext(), new ListAdapterCupon.OnItemClickListener() {
                @Override
                public void onItemClick(Cupon cupon) {
                    moveToDescription(cupon);
                }
            });
            recyclerView = view.findViewById(R.id.listCatRecycleView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerView.setAdapter(listAdapterCat);
        }
        catch (SQLiteException sql){
            sql.printStackTrace();
        }
    }

    public void moveToDescription(Cupon cupon){

        Bundle datos = new Bundle();
        datos.putInt("id_cupon", cupon.getId_cupon());
        datos.putInt("id_userD", id_usuario);
        datos.putString("nombre_cupon", cupon.getNombre_cupon());
        datos.putString("descripcion_cupon", cupon.getDescripcion_cupon());
        datos.putDouble("precio_cupon", cupon.getPrecio());
        datos.putString("horario_cupon", cupon.getHorario_cupon());
        datos.putInt("disponible", cupon.getDisponible());
        datos.putString("nombre_restaurante", cupon.getRestaurante().getNombre_restaurante());
        datos.putString("direccion", cupon.getRestaurante().getDireccion());

        Fragment detalleFragment = new DetalleCuponFragment();
        detalleFragment.setArguments(datos);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content, detalleFragment);
        ft.addToBackStack(null);

        ft.commit();
    }
}