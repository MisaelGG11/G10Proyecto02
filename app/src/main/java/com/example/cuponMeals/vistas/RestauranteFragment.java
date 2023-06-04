package com.example.cuponMeals.vistas;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cuponMeals.R;
import com.example.cuponMeals.controladores.RestauranteControl;
import com.example.cuponMeals.modelos.Restaurante;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

/**
 * Este fragment tiene por proposito registrar y editar restaurantes
 */
public class RestauranteFragment extends Fragment {
    private int id_restaurante;
    private boolean is_new,is_admin;
    private RestauranteControl restauranteControl;
    private Restaurante restaurante;
    //Elementos
    private Button btn_buscar,guardar;
    private EditText edit_nombre;
    private EditText edit_direccion;
    private EditText edit_longitud;
    private EditText edit_latitud;
    private EditText edit_id_maps;
    private EditText edit_telefono;
    private ImageView img_negocio;


    private static int AUTOCOMPLETE_REQUEST_CODE = 1;

    public RestauranteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restauranteControl=new RestauranteControl(getContext());
        /*
         *si es admin, entonces puede crear o editar un restaurante
         * si no es admin, entonces solo puede editar el restaurante
         */
        if (getArguments() != null) {
            is_admin=getArguments().getBoolean("is_admin",true);
            is_new=getArguments().getBoolean("is_new",true);
            if(is_new){
                restaurante=new Restaurante();
            }else{
                id_restaurante= getArguments().getInt("id_restaurante",0);
                restaurante=restauranteControl.readRestaurante(id_restaurante);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_restaurante_dialog, container, false);

        btn_buscar=view.findViewById(R.id.btn_buscar_direccion);
        guardar=view.findViewById(R.id.guardar_restaurante);
        Places.initialize(getContext(), getString(R.string.api_maps_key));
        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(getContext());

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAutocomplete(getContext(), AUTOCOMPLETE_REQUEST_CODE);
            }
        });
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int is_success;
                if(is_new){
                    is_success=(int)restauranteControl.insertRestaurante(restaurante);
                }else{
                    is_success=(int)restauranteControl.updateRestaurante(restaurante);
                }
                if(is_success>0){
                    Toast.makeText(getContext(),R.string.guardado,Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),R.string.invalido_username,Toast.LENGTH_LONG).show();
                }
            }
        });
        if(!is_new){
            //Inicializando elementos
            edit_direccion=view.findViewById(R.id.edit_direccion_negocio);
            edit_nombre=view.findViewById(R.id.edit_nombre_negocio);
            edit_telefono=view.findViewById(R.id.edit_tell_negocio);
            img_negocio=view.findViewById(R.id.imageView_negocio);

            //Recuperando datos de la Api
            edit_nombre.setText(restaurante.getNombre_restaurante());
            edit_direccion.setText(restaurante.getDireccion());
            edit_telefono.setText(restaurante.getTelefono());
            if(restaurante.getUrl_imagen().equals("")){
                Drawable logo=getResources().getDrawable(R.drawable.logo);
                img_negocio.setImageDrawable(logo );
            }else{
                Picasso.with(getContext()).load(restaurante.getUrl_imagen()).into(img_negocio);
            }
        }

        return view;
    }
    public void startAutocomplete(Context context, int requestCode){
        // Set the fields to specify which types of place data to
        // return after the user has made a selection.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.PHOTO_METADATAS, Place.Field.ADDRESS, Place.Field.PHONE_NUMBER);

        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .build(context);
        startActivityForResult(intent, requestCode);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Si recibe datos del autocomplete
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                String url="";
                //Inicializando elementos
                guardar=getActivity().findViewById(R.id.guardar_restaurante);
                edit_direccion=getActivity().findViewById(R.id.edit_direccion_negocio);
                edit_nombre=getActivity().findViewById(R.id.edit_nombre_negocio);
                edit_telefono=getActivity().findViewById(R.id.edit_tell_negocio);
                img_negocio=getActivity().findViewById(R.id.imageView_negocio);

                //Recuperando datos de la Api
                edit_nombre.setText(place.getName());
                edit_direccion.setText(place.getAddress());
                edit_telefono.setText(place.getPhoneNumber());

                //URL de la imagen
                if(place.getPhotoMetadatas().size()>0){
                    url="https://maps.googleapis.com/maps/api/place/photo?maxwidth=500&photo_reference=";
                    String photo_reference=place.getPhotoMetadatas().get(0).zza();
                    url=url+photo_reference+"&key="+getString(R.string.api_maps_key);
                    Picasso.with(getContext()).load(url).into(img_negocio);
                }else{
                    Drawable logo=getResources().getDrawable(R.drawable.logo);
                    img_negocio.setImageDrawable(logo );
                }

                //Imagen

                //asignando valores al restaurante
                restaurante.setId_google_maps(place.getId());
                restaurante.setLat(place.getLatLng().latitude);
                restaurante.setLng(place.getLatLng().longitude);
                restaurante.setTelefono(place.getPhoneNumber());
                restaurante.setDireccion(place.getAddress());
                restaurante.setNombre_restaurante(place.getName());
                restaurante.setUrl_imagen(url);

                //guardando habilitado
                guardar.setEnabled(true);

//                Log.w("Resultado", url);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("Resultado", status.getStatusMessage());
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}