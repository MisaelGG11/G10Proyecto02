package com.example.couponsapp.vistas;

import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.couponsapp.R;
import com.example.couponsapp.adapter.CuponAdapter;
import com.example.couponsapp.adapter.UsuarioAdapter;

import java.util.ArrayList;
import java.util.Locale;


public class GestionarCuponFragment extends Fragment {

    private static final String PLACEHOLDER = "Tipo cupón";
    protected EditText buscarBar;
    protected ListView listView;
    protected String placeholder;
    private CuponAdapter adapter;
    private ImageButton btnHablar;

    public GestionarCuponFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new CuponAdapter(getContext());
        buscarBar.setHint(PLACEHOLDER);
        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crud, container, false);
        buscarBar = view.findViewById(R.id.edit_text_crud);
        listView = view.findViewById(R.id.list_view_crud);


        view.findViewById(R.id.btn_buscar_audio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hable ahora");

                try {
                    startActivityForResult(intent, 1111);
                } catch (Exception e) {
                    Toast
                            .makeText(getContext(), " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        view.findViewById(R.id.btn_buscar_crud).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Buscando...", Toast.LENGTH_SHORT).show();
                String targetItem = buscarBar.getText().toString();
                if (targetItem != "") {
                    adapter.filtrar(targetItem);
                }
            }
        });
        view.findViewById(R.id.btn_nuevo_crud).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.crear();
            }
        });
        buscarBar.setHint(placeholder);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setItems(R.array.crud_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                adapter.editar(position);
                                break;
                            case 1:
                                adapter.eliminar(position);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1111 && resultCode == RESULT_OK) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String strSpeech = results.get(0);
            buscarBar.setText(strSpeech);
            Toast.makeText(getContext(), "Buscando...", Toast.LENGTH_SHORT).show();
            String targetItem = buscarBar.getText().toString();
            if (targetItem != "") {
                adapter.filtrar(targetItem);
            }
            //lv.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, results));
        }
        buscarBar.setText("");
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onDestroy() {
        super.onDestroy();
    }
}