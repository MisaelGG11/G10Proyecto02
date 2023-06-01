package com.example.couponsapp.vistas;

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

import com.example.couponsapp.R;
import com.example.couponsapp.adapter.ListAdapterMisCupones;
import com.example.couponsapp.controladores.RegistrarCuponControl;
import com.example.couponsapp.controladores.UsuarioControl;
import com.example.couponsapp.modelos.RegistrarCupon;
import com.example.couponsapp.modelos.Usuario;

import java.util.ArrayList;

public class MisCuponesFragment extends Fragment {

    RecyclerView recyclerView;

    int id_usuario;
    Usuario usuario;
    ArrayList<RegistrarCupon> misCuponesList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle data = getArguments();
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_mis_cupones, container, false);

        RegistrarCuponControl registrarCuponControl = new RegistrarCuponControl(root.getContext());
        UsuarioControl usuarioControl = new UsuarioControl(root.getContext());

        id_usuario = data.getInt("id_user");
        try {
            usuario = usuarioControl.traerUsuarioById(id_usuario);
            misCuponesList = registrarCuponControl.traerMisCupones(id_usuario);
            llenarLista(root, misCuponesList);
        }
        catch (SQLiteException sql){
            sql.printStackTrace();
        }

        return root;
    }

    public void llenarLista(View view, ArrayList<RegistrarCupon> lis){
        try {
            ListAdapterMisCupones listAdapterCat = new ListAdapterMisCupones(lis, view.getContext(), new ListAdapterMisCupones.OnItemClickListener() {
                @Override
                public void onItemClick(RegistrarCupon registrarCupon) {
                    moveToDescription(registrarCupon);
                }
            });
            recyclerView = view.findViewById(R.id.recycleMisCupones);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            recyclerView.setAdapter(listAdapterCat);
        }
        catch (SQLiteException sql){
            sql.printStackTrace();
        }
    }

    public void moveToDescription(RegistrarCupon registrarCupon){

        Bundle datos = new Bundle();
        datos.putInt("idCupon", registrarCupon.getCupon().getId_cupon());
        datos.putInt("idUserD", id_usuario);
        datos.putString("nameUser", usuario.getNombre());
        datos.putString("userEmail", usuario.getEmail());
        datos.putString("cCupon", registrarCupon.getCupon().getCodigo_cupon());
        datos.putString("nCupon", registrarCupon.getCupon().getNombre_cupon());
        datos.putString("dCupon", registrarCupon.getCupon().getDescripcion_cupon());
        datos.putDouble("pCupon", registrarCupon.getCupon().getPrecio());
        datos.putString("hCupon", registrarCupon.getCupon().getHorario_cupon());
        datos.putString("nRes", registrarCupon.getCupon().getRestaurante().getNombre_restaurante());
        datos.putString("dRes", registrarCupon.getCupon().getRestaurante().getDireccion());
        datos.putInt("id_registro",registrarCupon.getId_registro());

        Fragment miCuponFragment = new MiCuponFragment();
        miCuponFragment.setArguments(datos);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content, miCuponFragment);
        ft.addToBackStack(null);

        ft.commit();
    }
}