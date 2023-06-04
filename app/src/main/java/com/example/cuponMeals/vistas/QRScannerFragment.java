package com.example.cuponMeals.vistas;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cuponMeals.R;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class QRScannerFragment extends Fragment {

    Button btnScannerQr;
    TextView descriptionQr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_q_r_scanner, container, false);

        btnScannerQr = root.findViewById(R.id.btnScannerQr);
        descriptionQr = root.findViewById(R.id.descriptionQr);

        btnScannerQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScanOptions options = new ScanOptions();
                options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
                options.setPrompt("Escaner codigo QR");
                options.setCameraId(0);  // Use a specific camera of the device
                options.setBeepEnabled(true);
                options.setBarcodeImageEnabled(true);
                options.setOrientationLocked(false);
                barcodeLauncher.launch(options);
            }
        });

        return root;
    }



    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    descriptionQr.setVisibility(getView().VISIBLE);
                    descriptionQr.setText(result.getContents());
                }
            });

}