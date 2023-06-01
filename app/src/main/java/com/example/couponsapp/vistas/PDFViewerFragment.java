package com.example.couponsapp.vistas;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;

import androidx.annotation.RawRes;
import androidx.fragment.app.Fragment;

import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.couponsapp.R;
import com.example.couponsapp.controladores.CuponControl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PDFViewerFragment extends Fragment {

    private PdfRenderer mPdfRenderer;
    private PdfRenderer.Page mPdfPage;
    private ImageView mImageView;
    private String pdf_path;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPdfPage != null) {
            mPdfPage.close();
        }
        if (mPdfRenderer != null) {
            mPdfRenderer.close();
        }
    }

    // Display a page from the PDF on an ImageView
    void openPdf(ImageView imageView, int pageNumber) throws IOException {
        // Copy sample.pdf from 'res/raw' folder into local cache so PdfRenderer can handle it
        File fileCopy = new File(getContext().getFilesDir(), pdf_path);

        // We will get a page from the PDF file by calling openPage
        ParcelFileDescriptor fileDescriptor =
                ParcelFileDescriptor.open(fileCopy,
                        ParcelFileDescriptor.MODE_READ_ONLY);
        mPdfRenderer = new PdfRenderer(fileDescriptor);
        mPdfPage = mPdfRenderer.openPage(pageNumber);

        // Create a new bitmap and render the page contents on to it
        Bitmap bitmap = Bitmap.createBitmap(mPdfPage.getWidth(),
                mPdfPage.getHeight(),
                Bitmap.Config.ARGB_8888);
        mPdfPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        // Set the bitmap in the ImageView so we can view it
        imageView.setImageBitmap(bitmap);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            pdf_path=getArguments().getString("uri_pdf");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_pdf_viewer, container, false);
        mImageView=view.findViewById(R.id.pdf_img_view);
        try {
            openPdf(mImageView,0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }
}