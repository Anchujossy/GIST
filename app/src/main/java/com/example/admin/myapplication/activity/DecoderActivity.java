package com.example.admin.myapplication.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.myapplication.R;
import com.example.admin.myapplication.fragments.GistInfoFragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class DecoderActivity extends AppCompatActivity {
    GistInfoFragment fragobj;
    private IntentIntegrator qrScan;

    Bundle bundle;
    IntentResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decoder);
        bundle = new Bundle();
        if (bundle != null) {
            fragmentChange();
        }
        intiatingQrScan();


    }

    /**
     * for intiating qr scanning
     */
    private void intiatingQrScan() {
        qrScan = new IntentIntegrator(this);
        qrScan.setPrompt("Scan a QRCode");
        qrScan.initiateScan();
    }

    /**
     * this method is used for changing fragments
     */
    private void fragmentChange() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fragobj = new GistInfoFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.rl_decoder, fragobj);
        fragmentTransaction.commit();
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data        for get back data from camera activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            // invalid qr code so that qrscanner cannot capture press back btn then alertdialog will popup
            if (result.getContents() == null) {
                alertDialog();

                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();

            } else {
                //valid qr code is stored in preference manager
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("MYLABEL", result.getContents() + "").apply();
                Log.d("inside", "after" + bundle.getString("SCANNED_DATA"));


            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * alertdialog will pop up for invalid qrcode
     */
    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setMessage("This QR  code wants to take you to" + result.getContents() + " .Gist Commenter has no control on its content");

        builder.setPositiveButton("OK I UNDERSTAND", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                qrScan.initiateScan();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
                qrScan.initiateScan();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }


}
