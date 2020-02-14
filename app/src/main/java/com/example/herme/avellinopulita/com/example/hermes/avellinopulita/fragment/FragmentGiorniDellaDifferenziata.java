package com.example.herme.avellinopulita.com.example.hermes.avellinopulita.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.herme.avellinopulita.MaterialeActivity;
import com.example.herme.avellinopulita.R;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Carta;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Indifferenziato;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Multimateriale;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Organico;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Vetro;

public class FragmentGiorniDellaDifferenziata extends Fragment {
    private LinearLayout organico;
    private LinearLayout vetro;
    private LinearLayout multimateriale;
    private LinearLayout indifferenziato;
    private LinearLayout carta;
    private Organico org;
    private Carta cart;
    private Multimateriale multi;
    private Vetro vet;
    private Indifferenziato inde;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_giorni_della_differenziata, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        org = new Organico();
        cart = new Carta();
        multi = new Multimateriale();
        vet = new Vetro();
        inde = new Indifferenziato();
        organico = (LinearLayout) getView().findViewById(R.id.organico);
        vetro = (LinearLayout) getView().findViewById(R.id.vetro);
        multimateriale = (LinearLayout) getView().findViewById(R.id.multimateriale);
        indifferenziato = (LinearLayout) getView().findViewById(R.id.indifferenziato);
        carta = (LinearLayout) getView().findViewById(R.id.carta);
        organico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MaterialeActivity.class);
                i.putExtra("MaterialeObj",org);
                i.putExtra("Materiale","organico");
                startActivity(i);
            }
        });
        vetro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),MaterialeActivity.class);
                i.putExtra("MaterialeObj",vet);
                i.putExtra("Materiale","vetro");
                startActivity(i);
            }
        });
        multimateriale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),MaterialeActivity.class);
                i.putExtra("MaterialeObj",multi);
                i.putExtra("Materiale","multimateriale");
                startActivity(i);
            }
        });
        indifferenziato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),MaterialeActivity.class);
                i.putExtra("MaterialeObj",inde);
                i.putExtra("Materiale","indifferenziato");
                startActivity(i);
            }
        });
        carta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),MaterialeActivity.class);
                i.putExtra("MaterialeObj",cart);
                i.putExtra("Materiale","carta");
                startActivity(i);
            }
        });
    }
}
