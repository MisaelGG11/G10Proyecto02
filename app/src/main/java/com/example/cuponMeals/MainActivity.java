package com.example.cuponMeals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cuponMeals.controladores.CuponControl;
import com.example.cuponMeals.controladores.PermisoControl;
import com.example.cuponMeals.controladores.RestauranteControl;
import com.example.cuponMeals.controladores.RolControl;
import com.example.cuponMeals.controladores.RolPermisoControl;
import com.example.cuponMeals.controladores.TipoCuponControl;
import com.example.cuponMeals.controladores.UsuarioControl;
import com.example.cuponMeals.dbHelper.Control;
import com.example.cuponMeals.modelos.Cupon;
import com.example.cuponMeals.modelos.Permiso;
import com.example.cuponMeals.modelos.Restaurante;
import com.example.cuponMeals.modelos.Rol;
import com.example.cuponMeals.modelos.RolPermiso;
import com.example.cuponMeals.modelos.TipoCupon;
import com.example.cuponMeals.modelos.Usuario;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    Control ctr = new Control(this);
    RolControl rolControl = new RolControl(this);
    PermisoControl permisoControl = new PermisoControl(this);
    RolPermisoControl rolPermisoControl = new RolPermisoControl(this);
    UsuarioControl usuarioControl = new UsuarioControl(this);
    TipoCuponControl tipoCuponControl = new TipoCuponControl(this);
    RestauranteControl restauranteControl = new RestauranteControl(this);
    CuponControl cuponControl = new CuponControl(this);

    /*
     *
     * Elementos de la UI
     *
     * */
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView google_btn;

    EditText username, password;
    Button loginbtn;
    String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crearTablas();

        if(usuarioControl.adminExist(1) == 0){
            llenarUsuarios();
            llenarBD();
        }

        //obtener datos de inicio de sesión sin google
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginbtn = (Button) findViewById(R.id.loginbtn);

        //Obtener datos inciales
        google_btn = findViewById(R.id.google_btn);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);
        GoogleSignInAccount cuenta = GoogleSignIn.getLastSignedInAccount(this);

        //iniciar sesión sin google
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = username.getText().toString();
                pass = password.getText().toString();
                if(user.equals("") || pass.equals("")){
                    Toast.makeText(MainActivity.this, "Ingresar los campos", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(usuarioControl.validarUsuario(user, pass) != 0){
                        Intent intent = new Intent(MainActivity.this, InicioActivity.class);
                        intent.putExtra("username", user);
                        intent.putExtra("password", pass);
                        MainActivity.this.startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Ha ingresado mal usuario o contraseña", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Iniciar sesión
        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inicioSesion();
            }
        });
    }
    void inicioSesion(){
        gsc.signOut();
        Intent inicioSesionIntent = gsc.getSignInIntent();
        startActivityForResult(inicioSesionIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToInicioActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Ocurrio un error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void navigateToInicioActivity(){
        finish();
        Intent inte = new Intent(this, InicioActivity.class);
        startActivity(inte);
    }

    public void crearTablas(){
        ctr.abrir();
        ctr.cerrar();
    }

    public void llenarUsuarios(){
        long rolAdmin = rolControl.insertRol(new Rol(1, "Admin")); //1
        long rolEncargado = rolControl.insertRol(new Rol(2, "Encargado")); //2
        long rolCliente = rolControl.insertRol(new Rol(3, "Cliente"));  //3

        long permisoAdmin = permisoControl.insertPermiso(new Permiso(1, "Gestionar usuario"));
        long permisoEncargado = permisoControl.insertPermiso(new Permiso(2, "Gestionar cupon"));
        long permisoCliente = permisoControl.insertPermiso(new Permiso(3, "Canjear cupon"));

        long rolPermiso1 = rolPermisoControl.insertRolPermiso(new RolPermiso(1,(int)rolAdmin, (int)permisoAdmin));
        long rolPermiso2 = rolPermisoControl.insertRolPermiso(new RolPermiso(2,(int)rolEncargado, (int)permisoEncargado));
        long rolPermiso3 = rolPermisoControl.insertRolPermiso(new RolPermiso(3,(int)rolCliente, (int)permisoCliente));

        long usuarioAdmin = usuarioControl.insertUsuario(new Usuario(1, (int)rolAdmin, 0,"admin", "admin123", "admin@gmail.com", "Eduardo", "Orellana", "78787878", 0,""));
        long usuarioEncargado = usuarioControl.insertUsuario(new Usuario(2, (int)rolEncargado, 1,"encargado", "encargado123", "encargado@gmail.com", "Julia", "Lopez", "79235499", 0,""));
        long usuarioCliente = usuarioControl.insertUsuario(new Usuario(3, (int)rolCliente, 0,"cliente", "cliente123", "encargado@gmail.com", "Julia", "Lopez", "75757575", 0,""));
    }

    public void llenarBD(){

        //Restaurante
        Restaurante restaurante = new Restaurante("ChIJhawemWEwY48RW99ILymtG0I", "Pizza Hut Los Heroes", "Sobre Blvd. De los Heroes, a un costado de mundo feliz RR, Blvr. De Los Heroes, San Salvador, El Salvador","",13.7090082, -89.210617, "https://maps.googleapis.com/maps/api/place/photo?maxwidth=500&photo_reference=Aap_uEDyn5nMJVMpBgTxmRZfwLhcZ8DIRf1qPaW9r4feWvd6sVf7Bck1xr0rjIlPHLpcqvvtyYvtqRMneRu5RDHFu6SS1629CZOE13O6NsKgEbau0lVLq3YQlLQekwGmm7OQe23hS-88hR3g8Wrkg24uiqpJRG0IZw51fW4XygvMnLnFdqmL&key=AIzaSyDfzM6e_uHjNlq-_6rk6nM-PEYWHOnLE20");
        long res1 = restauranteControl.insertRestaurante(restaurante);
        restaurante.setId_restaurante((int)res1);


        //TipoCupon
        long tipoDesayuno = tipoCuponControl.insertTipoCupon(new TipoCupon(1, "Desayuno"));
        TipoCupon tip1 = new TipoCupon(1, "Desayuno");
        long tipoAlmuerzo = tipoCuponControl.insertTipoCupon(new TipoCupon(2, "Almuerzo"));
        TipoCupon tip2 = new TipoCupon(2, "Almuerzo");
        long tipoCena = tipoCuponControl.insertTipoCupon(new TipoCupon(3, "Cena"));
        TipoCupon tip3 = new TipoCupon(3, "Cena");
        long tipoSnacks = tipoCuponControl.insertTipoCupon(new TipoCupon(4, "Snacks"));
        TipoCupon tip4 = new TipoCupon(4, "Snacks");

        //Cupones
        long cup1 = cuponControl.insertCupon(new Cupon(
                1,
                restaurante,
                tip2,
                "CP001",
                "Pizza gigante + Pan con ajo",
                "Una pizza gigante de 12 porciones y 4 panes con ajo",
                12.75,
                "12:00pm - 23:59pm",
                1));

        long cup2 = cuponControl.insertCupon(new Cupon(
                2,
                restaurante,
                tip1,
                "CP002",
                "Combo típico",
                "3 desayunos típicos + refil de café",
                7.55,
                "6:00am - 11:30pm",
                1));

        long cup3 = cuponControl.insertCupon(new Cupon(
                3,
                restaurante,
                tip3,
                "CP003",
                "Pasta Calssone Club",
                "2 Pastas Calssone + 1 Pepsi de 1.25L",
                5.65,
                "5:45pm - 9:30pm",
                1));

        long cup4 = cuponControl.insertCupon(new Cupon(
                4,
                restaurante,
                tip4,
                "CP004",
                "Conos Putch",
                "4 conos de helado de vainilla + topin de elección",
                2.99,
                "10:30am - 9:30pm",
                1));
    }
}