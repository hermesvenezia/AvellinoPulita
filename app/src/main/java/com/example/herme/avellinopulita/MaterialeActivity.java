package com.example.herme.avellinopulita;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Carta;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Indifferenziato;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Multimateriale;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Organico;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Vetro;

public class MaterialeActivity extends AppCompatActivity {
    private TextView title;
    private ImageView icon;
    private TextView cosaInserire;
    private TextView cosaNonInserire;
    private TextView giorni;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materiale);
        title = (TextView) findViewById(R.id.title);
        icon = (ImageView) findViewById(R.id.iconMateriale);
        cosaInserire = (TextView) findViewById(R.id.cosaInserire);
        cosaNonInserire = (TextView) findViewById(R.id.cosaNonInserire);
        giorni = (TextView) findViewById(R.id.giorni);
        Intent i = getIntent();
        String materiale = i.getStringExtra("Materiale");
        switch (materiale){
            case "organico":
                Organico org = i.getParcelableExtra("MaterialeObj");
                title.setText(org.getName());
                cosaInserire.setText(org.getCosaPuoiButtare());
                cosaNonInserire.setText(org.getCosaNonPuoiButtare());
                icon.setImageDrawable(getResources().getDrawable(R.drawable.organico));
                giorni.setText(org.getGiorniDifferenziata());
                break;
            case "multimateriale":
                Multimateriale mult = i.getParcelableExtra("MaterialeObj");
                title.setText(mult.getName());
                cosaInserire.setText(mult.getCosaPuoiButtare());
                cosaNonInserire.setText(mult.getCosaNonPuoiButtare());
                icon.setImageDrawable(getResources().getDrawable(R.drawable.multimateriale));
                giorni.setText(mult.getGiorniDifferenziata());
                break;
            case "vetro":
                Vetro vet = i.getParcelableExtra("MaterialeObj");
                title.setText(vet.getName());
                cosaInserire.setText(vet.getCosaPuoiButtare());
                cosaNonInserire.setText(vet.getCosaNonPuoiButtare());
                icon.setImageDrawable(getResources().getDrawable(R.drawable.vetro));
                giorni.setText(vet.getGiorniDifferenziata());
                break;
            case "indifferenziato":
                Indifferenziato ind = i.getParcelableExtra("MaterialeObj");
                title.setText(ind.getName());
                cosaInserire.setText(ind.getCosaPuoiButtare());
                cosaNonInserire.setText(ind.getCosaNonPuoiButtare());
                icon.setImageDrawable(getResources().getDrawable(R.drawable.indiferenziato));
                giorni.setText(ind.getGiorniDifferenziata());
                Log.d("MaterialActivity/INDIFFERENZIATO","giorni inde =>" + ind.getGiorniDifferenziata());
                break;
            case "carta":
                Carta carta = i.getParcelableExtra("MaterialeObj");
                title.setText(carta.getName());
                cosaInserire.setText(carta.getCosaPuoiButtare());
                cosaNonInserire.setText(carta.getCosaNonPuoiButtare());
                icon.setImageDrawable(getResources().getDrawable(R.drawable.carta));
                giorni.setText(carta.getGiorniDifferenziata());
                break;
        }

    }
}
