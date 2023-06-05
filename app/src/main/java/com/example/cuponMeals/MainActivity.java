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
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail() .build();
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
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Ocurrio un error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
        //ROLES
        long rolAdmin = rolControl.insertRol(new Rol(1, "Administrador")); //1
        long rolEncargado = rolControl.insertRol(new Rol(2, "Encargado")); //2
        long rolCliente = rolControl.insertRol(new Rol(3, "Cliente"));  //3

        //PERMISOS
        long permisoAdmin = permisoControl.insertPermiso(new Permiso(1, "Gestionar Usuario"));
        long permisoEncargado = permisoControl.insertPermiso(new Permiso(2, "Gestionar Cupon"));
        long permisoCliente = permisoControl.insertPermiso(new Permiso(3, "Canjear Cupon"));

        //ASIGNACION DE PERMISOS SEGUN ROL
        long rolPermiso1 = rolPermisoControl.insertRolPermiso(new RolPermiso(1,(int)rolAdmin, (int)permisoAdmin));
        long rolPermiso2 = rolPermisoControl.insertRolPermiso(new RolPermiso(2,(int)rolEncargado, (int)permisoEncargado));
        long rolPermiso3 = rolPermisoControl.insertRolPermiso(new RolPermiso(3,(int)rolCliente, (int)permisoCliente));

        //INGRESO DE USUARIOS
        long usuarioAdmin = usuarioControl.insertUsuario(new Usuario(1, (int)rolAdmin, 0,"admin", "admin123", "GG20031@ues.edu.sv", "Misael Antonio", "Gómez García", "77354200", 0,""));
        long usuarioEncargado = usuarioControl.insertUsuario(new Usuario(2, (int)rolEncargado, 1,"encargado", "encargado123", "HS19011@ues.edu.sv", "Edwin Alexander", "Hernández Sánchez", "75234510", 0,""));
        long usuarioCliente1 = usuarioControl.insertUsuario(new Usuario(3, (int)rolCliente, 0,"cliente", "cliente123", "FM19038@ues.edu.sv", "Fabio Ernesto", "Flores Mendoza", "78453298", 0,""));

        /*
        long usuarioAdmin = usuarioControl.insertUsuario(new Usuario(1, (int)rolAdmin, 0,"AdministradorEjm", "grupo10", "GG20031@ues.edu.sv", "Misael Antonio", "Gómez García", "77354200", 0,""));
        long usuarioEncargado = usuarioControl.insertUsuario(new Usuario(2, (int)rolEncargado, 1,"EncargadoEjm", "grupo10", "HS19011@ues.edu.sv", "Edwin Alexander", "Hernández Sánchez", "75234510", 0,""));
        long usuarioCliente1 = usuarioControl.insertUsuario(new Usuario(3, (int)rolCliente, 0,"ClienteEjm", "grupo10", "FM19038@ues.edu.sv", "Fabio Ernesto", "Flores Mendoza", "78453298", 0,""));
       */

        long usuarioEncargado2 = usuarioControl.insertUsuario(new Usuario(4, (int)rolEncargado, 3,"Encargado2", "grupo10", "EL19004@ues.edu.sv", "Leonardo Alfredo", "Efigenio Landaverde", "79451299", 0,""));
        long usuarioCliente2 = usuarioControl.insertUsuario(new Usuario(5, (int)rolCliente, 0,"Cliente2", "grupo10", "AC17033@ues.edu.sv", "Claudia Maria", "Argueta Caneza", "72443180", 0,""));
    }

    public void llenarBD(){

        //INGRESO DE RESTAURANTES
        //Pollo Campero
        Restaurante restaurante1 = new Restaurante(
                "RMqzwJH839LUQhea7?coh=178573&entry=tt",
                "Pollo Campero, Cojutepeque",
                "Fte a super selectos, Av. José María Rivas y, Calle Dr Jose Matias Delgado, Cojutepeque",
                "2273600",
                13.7222099, -88.9436833,
                "https://maps.googleapis.com/maps/api/place/photo?maxwidth=500&photo_reference=AZose0kDRpxXAWXsNkoZEmS9Im5s6umjD9n3Spwhj78bCsS4t6V8In4-Xx7za72PQ3OL1J7ulNO51OBmRl4bOxwNmv-xYXc0N4S-Yccba-t9gLD9iPM9TJRFzkg_O_Af2CQd3_SOQJx2id7dDwflm38yt9DWMagHWbokhYs9oDI7q0deqNor&key=AIzaSyDZQd1IGNPiUYaWaoee0cBdIdRlj-npYzQ");
        long rest1 = restauranteControl.insertRestaurante(restaurante1);
        restaurante1.setId_restaurante((int)rest1);

        //Restaurante EL FARO
        Restaurante restaurante2 = new Restaurante(
                "KQ28sz6YyxRZ1yBz7?coh=178573&entry=tt",
                "El Faro Clareño, San Vicente",
                "Laguna de Apastepeque, San Vicente, El Salvador",
                "23163680",
                13.6903701,-88.7426673,
                "https://maps.googleapis.com/maps/api/place/photo?maxwidth=500&photo_reference=AZose0k_mn2CRpwNplG1PB8Es7LOBKeDvStvdRv37AKiVXVSH--HSjxYEscOKt3K0M6Jt-y1TNVUmtIAnmW2O2znqKBRX8uHtJMA3OGLxdQZ7KScKUgSnNd8PH9_s15LlAzfhvmO_EitZchmBJEXCbegNbIXor-mxe60MZ4-FbIcDN1iG-vH&key=AIzaSyDZQd1IGNPiUYaWaoee0cBdIdRlj-npYzQ");
        long rest2 = restauranteControl.insertRestaurante(restaurante2);
        restaurante2.setId_restaurante((int)rest2);

        //Mister Donut
        Restaurante restaurante3 = new Restaurante(
                "f19MD2jokSjYNNqM6?coh=178573&entry=tt",
                "Mister Donut, San Luis",
                "PQ8Q+459, Calle, San Salvador",
                "22264198",
                13.7152924,-89.2145798,
                "https://maps.googleapis.com/maps/api/place/photo?maxwidth=500&photo_reference=AZose0mejMWklV6YyszKYF1sDjMJKvhHsCDVzypv6L4OUABADQQL9fz8oioETqVs9qaxcH_omcLX0vW6ghwAJiqNM_TK6uL0qyY37hdZSxv6hZz5c5MR-F59P7msHb7meCe5y4RLvKU06-br-sxN1WacFeJwjlHJm88pAzup-PMKwaxx5WsX&key=AIzaSyDZQd1IGNPiUYaWaoee0cBdIdRlj-npYzQ");
        long rest3 = restauranteControl.insertRestaurante(restaurante3);
        restaurante3.setId_restaurante((int)rest3);

        //INGRESO DE LOS TIPOS DE CUPONES
        long tipoDesayuno = tipoCuponControl.insertTipoCupon(new TipoCupon(1, "Desayuno"));
        TipoCupon tip1 = new TipoCupon(1, "Desayuno");
        long tipoAlmuerzo = tipoCuponControl.insertTipoCupon(new TipoCupon(2, "Almuerzo"));
        TipoCupon tip2 = new TipoCupon(2, "Almuerzo");
        long tipoCena = tipoCuponControl.insertTipoCupon(new TipoCupon(3, "Cena"));
        TipoCupon tip3 = new TipoCupon(3, "Cena");
        long tipoSnacks = tipoCuponControl.insertTipoCupon(new TipoCupon(4, "Snacks"));
        TipoCupon tip4 = new TipoCupon(4, "Snacks");

        //Cupones
        //POLLO CAMPERO
        long cup1 = cuponControl.insertCupon(new Cupon(
                1,
                restaurante1,
                tip3,
                "C001",
                "Pizza de 1 ingrediente",
                "En la compra de una pizza Super Campero, obten pizza de 1 ingrediente a $5",
                5.00,
                "03:00pm - 07:59pm",
                1));

        long cup2 = cuponControl.insertCupon(new Cupon(
                2,
                restaurante1,
                tip2,
                "C002",
                "3 menús de burrito Campero por $15.00",
                "Disfruta tres deliciosos menú de burrito Campero por $15.00",
                15.00,
                "6:00am - 11:30pm",
                1));

        long cup3 = cuponControl.insertCupon(new Cupon(
                3,
                restaurante1,
                tip4,
                "C003",
                "3 Flanes por $3.00",
                "Agrega 3 flanes en tu orden por $3.00 adicionales",
                3.00,
                "12:00pm - 8:30pm",
                1));

        //EL FARO
        long cup4 = cuponControl.insertCupon(new Cupon(
                4,
                restaurante2,
                tip2,
                "C004",
                "Orden de Nachos",
                "Agrega una orden de nachos en tu orden por $2.00 adicionales",
                2.00,
                "1:30am - 8:30pm",
                1));

        //MISTER DONUT
        long cup5 = cuponControl.insertCupon(new Cupon(
                5,
                restaurante3,
                tip4,
                "C005",
                "Donas 2x1",
                "Recibe 2 donas por el precio de una",
                1.10,
                "8:30am - 10:30pm",
                1));
        long cup6 = cuponControl.insertCupon(new Cupon(
                6,
                restaurante3,
                tip1,
                "C006",
                "Tamales de Elote por $1.99",
                "Agrega una tamal de Elote en tu desayuno por $1.99 adicionales",
                1.99,
                "7:30am - 10:30am",
                1));
        long cup7 = cuponControl.insertCupon(new Cupon(
                7,
                restaurante3,
                tip3,
                "C007",
                "Café Gratis",
                "En la compra de tu cena Mister Donut, obten un café gratis",
                0.00,
                "8:30am - 10:30pm",
                1));
    }
}