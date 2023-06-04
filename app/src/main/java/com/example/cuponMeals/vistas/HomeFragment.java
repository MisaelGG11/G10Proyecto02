package com.example.cuponMeals.vistas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cuponMeals.R;

public class HomeFragment extends Fragment {

    TextView nombre;
    String usuario;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments()!=null){
            String username=getArguments().getString("username");
            usuario = username;
        }else{
            usuario="";
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nombre.setText(String.valueOf(usuario));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        nombre = (TextView) root.findViewById(R.id.text_usuario);

        return root;
    }
}