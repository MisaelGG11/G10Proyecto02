package com.example.cuponMeals.vistas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cuponMeals.R;
import com.example.cuponMeals.adapter.RolAdapter;
import com.example.cuponMeals.controladores.UsuarioControl;
import com.example.cuponMeals.modelos.Rol;
import com.example.cuponMeals.modelos.Usuario;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Este fragmento tiene por proposito registrar y editar usuario
 */
public class UsuarioFragment extends Fragment {
    int REQUEST_IMAGE_CAPTURE = 22;
    String currentPhotoPath;
    Uri photoURI = null;
    int id_usuario;
    UsuarioControl usuarioControl;
    RolAdapter rolAdapter;
    Usuario usuario;
    boolean isNew, isAdmin;
    EditText usernameEditText, passwordEditText, passwordEditText2, emailEditText, nombreEditText, apellidosEditText, telefono;
    EditText passwordConfirmation1, passwordConfirmation2;
    Button guardarBtn, cambiarContrasenaBtn;
    ImageButton tomarFotoBtn, subirFotoBtn;
    ImageView fotoPerfilImg;
    Spinner rolSpinner;
    int updatedCount = 0;

    public UsuarioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usuarioControl = new UsuarioControl(getContext());
        rolAdapter = new RolAdapter(getContext());
        if (getArguments() != null) {
            isNew = getArguments().getBoolean("is_new", false);
            isAdmin = getArguments().getBoolean("is_admin", false);
            if (isNew) {
                usuario = new Usuario();
            } else {
                id_usuario = getArguments().getInt("id_usuario");
                usuario = usuarioControl.readUsuario(id_usuario);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //inicializacion de botones de guardar y cambiar contrasena
        cambiarContrasenaBtn = view.findViewById(R.id.button_cambiar_contrasena);
        guardarBtn = view.findViewById(R.id.btn_guardar_usuario_configuracion);

        //recuperando los editText del view
        usernameEditText = view.findViewById(R.id.perfil_de_usuario_username);
        emailEditText = view.findViewById(R.id.perfil_de_usuario_email);
        passwordEditText = view.findViewById(R.id.perfil_de_usuario_contrasena);
        nombreEditText = view.findViewById(R.id.perfil_de_usuario_nombre);
        apellidosEditText = view.findViewById(R.id.perfil_de_usuario_apellidos);
        telefono = view.findViewById(R.id.perfil_de_usuario_telefono);
        rolSpinner = view.findViewById(R.id.perfil_spinner_rol);
        tomarFotoBtn = view.findViewById(R.id.tomar_foto_btn);
        subirFotoBtn = view.findViewById(R.id.upload_foto_btn);
        fotoPerfilImg = view.findViewById(R.id.foto_perfil);

        if (isNew) {
            cambiarContrasenaBtn.setVisibility(View.GONE);
            passwordEditText2 = view.findViewById(R.id.perfil_de_usuario_contrasena2);
            passwordEditText.setEnabled(true);
            usernameEditText.setEnabled(true);
            rolSpinner.setVisibility(View.VISIBLE);
            rolSpinner.setAdapter(rolAdapter);
            passwordEditText2.setVisibility(View.VISIBLE);


        } else {
            //asignando valores por defecto si se esta editando el perfil
            usernameEditText.setText(usuario.getUsername());
            emailEditText.setText(usuario.getEmail());
            passwordEditText.setText(usuario.getPassword());
            nombreEditText.setText(usuario.getNombre());
            apellidosEditText.setText(usuario.getApellido());
            telefono.setText(usuario.getTelefono());
            rolSpinner.setVisibility(View.VISIBLE);
            rolSpinner.setAdapter(rolAdapter);
            if (usuario.getUri_foto_perfil() != null) {
                if (!usuario.getUri_foto_perfil().equals("")) {
                    //recuperando la imagen de donde fue almacenada
                    String directory = getContext().getFilesDir().toString();
                    File image = new File(directory, usuario.getUri_foto_perfil());
                    //guardando temporalmente la uri de el archivo.jpg
                    photoURI = FileProvider.getUriForFile(getContext(),
                            "com.example.cuponMeals.fileprovider",
                            image);
                    //asginando el bitmap a el ImageView en UI
                    fotoPerfilImg.setImageURI(photoURI);
                }
            }
            if (isAdmin) {
                usernameEditText.setEnabled(true);
            }
        }


        //agregando un event listener para cuando da clic en guardar
        guardarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setNombre(nombreEditText.getText().toString());
                usuario.setApellido(apellidosEditText.getText().toString());
                usuario.setEmail(emailEditText.getText().toString());
                usuario.setTelefono(telefono.getText().toString());
                usuario.setUri_foto_perfil(currentPhotoPath);

                if (isNew) {
                    if (passwordEditText.getText().toString().equals(passwordEditText2.getText().toString())) {
                        Rol selectedRol = (Rol) rolSpinner.getSelectedItem();
                        usuario.setPassword(passwordEditText.getText().toString());
                        usuario.setUsername(usernameEditText.getText().toString());
                        usuario.setId_rol(selectedRol.getId_rol());


                        //aqui se guarda el usuario
                        updatedCount = (int) usuarioControl.insertUsuario(usuario);
                        if (updatedCount > 0) {
                            Toast.makeText(getContext(), R.string.guardado, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), R.string.invalido_username, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getContext(), R.string.contrasena_no_coincide, Toast.LENGTH_LONG).show();

                    }
                } else {
                    if (isAdmin) {
                        usuario.setUsername(usernameEditText.getText().toString());
                    }
                    updatedCount = usuarioControl.updateUsuario(usuario);
                    if (updatedCount == 1) {
                        Toast.makeText(getContext(), "Se Guardo", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        if (!isNew) {
            cambiarContrasenaBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = requireActivity().getLayoutInflater();
                    builder.setView(inflater.inflate(R.layout.cambiar_contrasena, null))
                            .setPositiveButton(R.string.guardar_usuario_configuracion, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    Dialog dialogView = (Dialog) dialog;
                                    passwordConfirmation1 = (EditText) dialogView.findViewById(R.id.cambiar_contrasena_confirmacion_1);
                                    passwordConfirmation2 = (EditText) dialogView.findViewById(R.id.cambiar_contrasena_confirmacion_2);
                                    Boolean isValid = cambiarContrasena();
                                    if (isValid) {
                                        Toast.makeText(getContext(), "Se Guardo", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getContext(), "Contraseñas no coinciden. Revise!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                    builder.setTitle(R.string.cambiar_contrasena);
                    builder.create();
                    builder.show();

                }
            });
        }

        //events listeners para tomar foto
        tomarFotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        //events listeners para subir foto

    }

    private Boolean cambiarContrasena() {
        String confirmacionClave1, confirmacionClave2;
        Boolean isValid = false;
        int updatedPasswordCount;
        confirmacionClave1 = passwordConfirmation1.getText().toString();
        confirmacionClave2 = passwordConfirmation2.getText().toString();
        if (confirmacionClave1.equals(confirmacionClave2)) {
            usuario.setPassword(confirmacionClave1);
            updatedPasswordCount = usuarioControl.updateUsuario(usuario);
            isValid = updatedPasswordCount == 1;
        }
        return isValid;
    }

    /**
     * metodo para iniciar un intent para abrir la camara del telefono
     * y esperar por el resultado
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    /**
     * metodo para esperar por el resultado de la camara intent
     * con el codigo REQUEST_IMAGE_CAPTURE definido en las declaraciones de variables
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            //extrayendo el bitmap que retorna action image capture intent
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            File photoFile = null;
            FileOutputStream fileOutputStream;
            try {
                //creando un archivo donde almacenar el bitmap, retorna un archivo jpg
                photoFile = createImageFile();
                //guardando temporalmente la uri de el archivo.jpg
                if (photoFile != null) {
                    //preparando el archivo para imprimir el bitmap
                    fileOutputStream = new FileOutputStream(photoFile);
                    //imprimiendo bitmap
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    photoURI = FileProvider.getUriForFile(getContext(),
                            "com.example.cuponMeals.fileprovider",
                            photoFile);

                    //asginando el bitmap a el ImageView en UI
                    fotoPerfilImg.setImageURI(photoURI);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * metodo para guardar un archivo jpg para la foto de perfil
     * y retornar un archivo del tipo jpg
     */
    private File createImageFile() throws IOException {
        //verificando que existe el directorio donde se guardaran existe
        File dir = new File(getContext().getFilesDir(), "pictures");
        if (!dir.exists()) {
            dir.mkdir();
        }
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(getContext().getFilesDir(), "pictures");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // path del archivo creado
        currentPhotoPath = "pictures/" + image.getName();
        return image;
    }
}