package com.example.herme.avellinopulita;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.*;
import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.barcode.Barcode;


import org.json.JSONObject;

import java.util.List;

import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

import static android.support.constraint.Constraints.TAG;

public class BarCodeDetectorActivity extends AppCompatActivity implements BarcodeRetriever {
    private EditText textCodice;
    private Button buttonInvia;
    private Carta carta;
    private Organico org;
    private Indifferenziato indi;
    private Vetro vetro;
    private Multimateriale multi;
    private BarcodeCapture barcodeCapture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code_detector);
        textCodice = (EditText) findViewById(R.id.text_codice);
        buttonInvia = (Button) findViewById(R.id.button_invia);
        buttonInvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager cm =
                        (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if (!isConnected) {
                    Toast.makeText(getApplicationContext(),"NESSUNA CONNESSIONE INTERNET. RIPROVARE", Toast.LENGTH_LONG).show();
                } else {
                    final String url = "https://world.openfoodfacts.org/api/v0/product/" + textCodice.getText() + ".json";
                    RequestQueue requestQueue = Volley.newRequestQueue(BarCodeDetectorActivity.this);
                    JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                JSONObject product = response.getJSONObject("product");
                                String codeBar = response.getString("code");
                                String product_name = product.optString("product_name_it");
                                String urlImage = product.optString("image_url");
                                String packaging = product.optString("packaging");
                                Intent newIntent2 = new Intent(getApplicationContext(), ProductActivity.class);
                                newIntent2.putExtra("code", codeBar);
                                newIntent2.putExtra("product_name", product_name);
                                newIntent2.putExtra("urlImage", urlImage);
                                newIntent2.putExtra("packaging", packaging);
                                startActivityForResult(newIntent2, 10001);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "ERROR REQUEST", Toast.LENGTH_LONG).show();
                        }
                    });
                    requestQueue.add(objectRequest);


                }
            }
        });
        barcodeCapture = (BarcodeCapture) getSupportFragmentManager().findFragmentById(R.id.barcode);
        barcodeCapture.setRetrieval(this);
    }

    @Override
    public void onPermissionRequestDenied() {

    }

    @Override
    public void onRetrieved(Barcode barcode) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        textCodice.setText(barcode.displayValue);
    }

    @Override
    public void onRetrievedMultiple(Barcode closetToClick, List<BarcodeGraphic> barcode) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onRetrievedFailed(String reason) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 10001) {
            textCodice.setText("");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
