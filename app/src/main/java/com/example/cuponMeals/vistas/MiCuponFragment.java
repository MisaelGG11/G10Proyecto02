package com.example.cuponMeals.vistas;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuponMeals.R;
import com.example.cuponMeals.controladores.RegistrarCuponControl;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MiCuponFragment extends Fragment {
    RegistrarCuponControl registrarCuponControl;
    TextView nCupon, dCupon, hCupon, pCupon, nRest;
    Button btnPDF, btnTTP;
    String cCupon, dRes, nRests, name, desc, horario, precio, uName, uEmail;
    int idUsuario, idCupon, id_registro;

    Bitmap bmp, scaleBitmap;
    private TextToSpeech tts;

    // constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        registrarCuponControl=new RegistrarCuponControl(getContext());
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_mi_cupon, container, false);
        Bundle data = getArguments();

        nCupon = root.findViewById(R.id.miName);
        dCupon = root.findViewById(R.id.miDesc);
        hCupon = root.findViewById(R.id.miHora);
        pCupon = root.findViewById(R.id.miPrice);
        nRest = root.findViewById(R.id.miRest);
        btnPDF = root.findViewById(R.id.btnPDF);
        btnTTP = root.findViewById(R.id.btnTTP);

        //asignando la info traida
        if(data != null){
            nCupon.setText(data.getString("nCupon"));
            dCupon.setText(data.getString("dCupon"));
            hCupon.setText(data.getString("hCupon"));
            pCupon.setText("$"+String.valueOf(data.getDouble("pCupon")));
            nRest.setText(data.getString("nRes"));

            //Strings a utilizar despues
            name = data.getString("nCupon");
            desc = data.getString("dCupon");
            horario = data.getString("hCupon");
            precio = "$"+String.valueOf(data.getDouble("pCupon"));
            cCupon = data.getString("cCupon");
            dRes = data.getString("dRes");
            nRests = data.getString("nRes");

            //ids usuario y cupon
            idCupon = data.getInt("idCupon");
            idUsuario = data.getInt("idUserD");
            uName = data.getString("nameUser");
            uEmail = data.getString("userEmail");
            id_registro=data.getInt("id_registro");
        }

        try {
            String content = "Cliente: "+uName+"\nEmail: "+uEmail+"\nCodigo: "+cCupon+"\nPromocion: "+name+"\nRestaurante: "+nRests+"\nDescripcion: "+desc+"\nHorario: "+horario+"\nPrecio: "+precio;
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bmp = barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 450, 450);
            scaleBitmap = Bitmap.createScaledBitmap(bmp, 210, 210, false);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        if (checkPermission(root)) {
            Toast.makeText(root.getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }

        crearPDF(root);
        TextoVoz(root);

        return root;
    }

    /*public void getPermisos(){
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
    }*/

    public void crearPDF(View v){
        btnPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verificando si ya existe
                String uri=registrarCuponControl.get_uri_pdf(id_registro);
                if(uri.equals("")){
                    PdfDocument miDocumento = new PdfDocument();
                    Paint paint = new Paint();

                    PdfDocument.PageInfo docPageInfo = new PdfDocument.PageInfo.Builder(250, 400, 1).create();
                    PdfDocument.Page docPage1 = miDocumento.startPage(docPageInfo);
                    Canvas canvas = docPage1.getCanvas();

                    paint.setTextAlign(Paint.Align.CENTER);
                    paint.setTextSize(12.0f);
                    canvas.drawText("CUPON #"+cCupon, docPageInfo.getPageWidth()/2, 30, paint);

                    paint.setTextSize(9.0f);
                    paint.setColor(Color.rgb(122,119,119));
                    canvas.drawText("Información en QR", docPageInfo.getPageWidth()/2, 70, paint);

                    canvas.drawBitmap(scaleBitmap, 25, 90, paint);
                    miDocumento.finishPage(docPage1);

                    //guardando pdf en files/coupons/
                    File dir= new File(getContext().getFilesDir(), "coupons");
                    if(!dir.exists()){
                        dir.mkdir();
                    }
                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(new Date());
                    String file_path="coupons/CUPON-"+cCupon+"-"+timeStamp+".pdf";
                    File file = new File(getContext().getFilesDir(), file_path);
                    try {
                        miDocumento.writeTo(new FileOutputStream(file));
                        int is_success=registrarCuponControl.set_uri_pdf(id_registro,file_path);
                        if(is_success>0){
                            //redirige a mostrar el pdf
                            Bundle args=new Bundle();
                            args.putString("uri_pdf",file_path);
                            Fragment fragment=new PDFViewerFragment();
                            fragment.setArguments(args);
                            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.content, fragment);
                            fragmentTransaction.commit();
                        }
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    miDocumento.close();
                }else{
                    //redirige a mostrar el pdf
                    Bundle args=new Bundle();
                    args.putString("uri_pdf",uri);
                    Fragment fragment=new PDFViewerFragment();
                    fragment.setArguments(args);
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, fragment);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    private boolean checkPermission(View v) {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(v.getContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(v.getContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(getContext(), "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Permission Denined.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void TextoVoz(View v){
        tts = new TextToSpeech(v.getContext(), ttsListener);
        btnTTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertirTextoVoz();
            }
        });
    }

    private TextToSpeech.OnInitListener ttsListener =
            new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    if(i == TextToSpeech.SUCCESS){
                        tts.setLanguage(Locale.getDefault());
                    }
                }
            };

    private void convertirTextoVoz(){
        if(dCupon == null || desc.equals("")){
            tts.speak("No hay que leer", TextToSpeech.QUEUE_FLUSH, null);
        }
        else{
            tts.speak(desc, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}