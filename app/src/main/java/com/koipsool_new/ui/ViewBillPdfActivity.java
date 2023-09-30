package com.koipsool_new.ui;

import static com.koipsool_new.RetofitSetUp.BaseUrls.PDF_BASEURL;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;

import com.koipsool_new.databinding.ActivityViewBillPdfBinding;
import com.rajat.pdfviewer.PdfViewerActivity;

import java.util.Random;

public class ViewBillPdfActivity extends AppCompatActivity {

    private ActivityViewBillPdfBinding binding;
    private ViewBillPdfActivity activity;
    private String orderId = "", customerName = "";
    String mainUrl = "";
    String url = "";
    DownloadManager manager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewBillPdfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = this;

        orderId = getIntent().getStringExtra("order_id");
        customerName = getIntent().getStringExtra("client_name");

        int num = new Random().nextInt(1000000);

        startActivity(
                PdfViewerActivity.Companion.launchPdfFromUrl(
                        this,
                        PDF_BASEURL + "?order_id=" + orderId,                                // PDF URL in String format
                        customerName+"invoice"+num,                        // PDF Name/Title in String format
                        "downloads/kapsool",                  // If nothing specific, Put "" it will save to Downloads
                        true                    // This param is true by defualt.
                )
        );
//        mainUrl = "https://drive.google.com/viewerng/viewer?embedded=true&url=" + PDF_BASEURL + "?order_id=" + orderId;
//        binding.pdfWebview.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + PDF_BASEURL + "?order_id=" + orderId);

//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(PDF_BASEURL + "?order_id=" + orderId));
//        startActivity(browserIntent);

        Log.e("TAG", "onCreate() called with: Url = [" + PDF_BASEURL + "?order_id=" + orderId + "]");

    }


}