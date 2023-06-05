package com.example.cuponMeals.vistas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.cuponMeals.R;
import com.example.cuponMeals.adapter.RestauranteAdapter;

public class GestionarRestauranteFragment extends Fragment {
    private static final String PLACEHOLDER = "Restaurante";
    protected EditText buscarBar;
    protected ListView listView;
    protected String placeholder;
    private RestauranteAdapter adapter;

    public GestionarRestauranteFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter=new RestauranteAdapter(getContext());
        buscarBar.setHint(PLACEHOLDER);
        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurante, container, false);
        buscarBar = view.findViewById(R.id.edit_text_crud);
        listView = view.findViewById(R.id.list_view_crud);

        view.findViewById(R.id.btn_buscar_crud).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Busqueda realizada",Toast.LENGTH_SHORT).show();
                String targetItem=buscarBar.getText().toString();
                adapter.filtrar(targetItem);
            }
        });
        view.findViewById(R.id.btn_nuevo_crud).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.crear(v,getParentFragmentManager());
            }
        });
        buscarBar.setHint(placeholder);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewSelected, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setItems(R.array.restaurante_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                adapter.editar(view,getParentFragmentManager(),adapter.getItem(position));
                                break;
                            case 1:
                                adapter.ver_mapa(view,getParentFragmentManager(),adapter.getItem(position));
                                break;
                        }
                    }
                });
                builder.create();
                builder.show();
            }
        });
        return view;
    }


    public void onDestroy() {
        super.onDestroy();
    }
}
