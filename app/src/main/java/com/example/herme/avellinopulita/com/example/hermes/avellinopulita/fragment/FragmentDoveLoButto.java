package com.example.herme.avellinopulita.com.example.hermes.avellinopulita.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.herme.avellinopulita.BarCodeDetectorActivity;
import com.example.herme.avellinopulita.R;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Carta;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Indifferenziato;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Multimateriale;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Organico;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Vetro;
import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture;
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONObject;

import java.util.List;

import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever;

import static android.support.constraint.Constraints.TAG;

public class FragmentDoveLoButto extends Fragment {
    private Button buttonInvia;
    private Carta carta;
    private Multimateriale multi;
    private Organico org;
    private Vetro vetro;
    private Indifferenziato indi;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle b = getArguments();
        carta = b.getParcelable("Carta");
        vetro = b.getParcelable("Vetro");
        multi = b.getParcelable("Multimateriale");
        org = b.getParcelable("Organico");
        indi = b.getParcelable("Indifferenziato");
        return inflater.inflate(R.layout.fragment_dove_lo_butto, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonInvia = (Button) getView().findViewById(R.id.button_invia);
        buttonInvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getContext(), BarCodeDetectorActivity.class);
                newIntent.putExtra("carta",carta);
                newIntent.putExtra("vetro",vetro);
                newIntent.putExtra("multi",multi);
                newIntent.putExtra("indi",indi);
                newIntent.putExtra("org",org);
                startActivity(newIntent);
            }
        });

    }
}
