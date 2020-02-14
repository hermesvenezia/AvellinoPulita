package com.example.herme.avellinopulita.com.example.hermes.avellinopulita.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.herme.avellinopulita.R;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Carta;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Indifferenziato;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Multimateriale;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Organico;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Vetro;
import com.google.api.Distribution;

import java.text.DateFormat;
import java.util.Calendar;

import static android.support.constraint.Constraints.TAG;

public class FragmentCosaButtoOggi extends Fragment {
    private Vetro vetro;
    private Multimateriale multi;
    private Carta carta;
    private Organico org;
    private Indifferenziato inde;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle b = getArguments();
         vetro = b.getParcelable("Vetro");
         multi = b.getParcelable("Multimateriale");
         carta = b.getParcelable("Carta");
         org = b.getParcelable("Organico");
         inde = b.getParcelable("Indifferenziato");
        return inflater.inflate(R.layout.fragment_cosa_butto_oggi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        TextView text_oggi = (TextView) getView().findViewById(R.id.giornoOggi);
        text_oggi.setText(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH,1);
        String dateTommorow = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        TextView text_domani = (TextView) getView().findViewById(R.id.giornoDomani);
        text_domani.setText(dateTommorow);
        LinearLayout listaOggi = (LinearLayout) getView().findViewById(R.id.listaOggi);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        LinearLayout layout_vetro = (LinearLayout) inflater.inflate(R.layout.custom_vetro,null,false);
        LinearLayout layout_inde = (LinearLayout)inflater.inflate(R.layout.custom_indifferenziato,null,false);
        LinearLayout layout_multi = (LinearLayout)inflater.inflate(R.layout.custom_multi,null,false);
        LinearLayout layout_carta = (LinearLayout)inflater.inflate(R.layout.custom_carta,null,false);
        LinearLayout layout_organico = (LinearLayout) inflater.inflate(R.layout.custom_organico,null,false);
        String[] oggi = currentDate.split(" ");
        String oggi_current = oggi[0].substring(0,oggi[0].length()-1); //ELIMINO L'ULTIMA LETTERA PER PROBLEMA DI ACCENTO
        if(vetro.getGiorniDifferenziata().toLowerCase().contains(oggi_current)){
            listaOggi.addView(layout_vetro);
        }
        if(carta.getGiorniDifferenziata().toLowerCase().contains(oggi_current)){
            listaOggi.addView(layout_carta);
        }
        if(multi.getGiorniDifferenziata().toLowerCase().contains(oggi_current)){
            listaOggi.addView(layout_multi);
        }
        if(org.getGiorniDifferenziata().toLowerCase().contains(oggi_current)){
            listaOggi.addView(layout_organico);
        }
        if(inde.getGiorniDifferenziata().toLowerCase().contains(oggi_current)){
            listaOggi.addView(layout_inde);
        }
        String[] domani = dateTommorow.split(" ");
        String domani_current = domani[0].substring(0,domani[0].length()-1); //ELIMINO L'ULTIMA LETTERA PER PROBLEMA DI ACCENTO
        LinearLayout listaDomani = (LinearLayout) getView().findViewById(R.id.listaDomani);
        LinearLayout layout_vetro2 = (LinearLayout) inflater.inflate(R.layout.custom_vetro,null,false);
        LinearLayout layout_inde2 = (LinearLayout)inflater.inflate(R.layout.custom_indifferenziato,null,false);
        LinearLayout layout_multi2 = (LinearLayout)inflater.inflate(R.layout.custom_multi,null,false);
        LinearLayout layout_carta2 = (LinearLayout)inflater.inflate(R.layout.custom_carta,null,false);
        LinearLayout layout_organico2 = (LinearLayout) inflater.inflate(R.layout.custom_organico,null,false);
        //CONTROLLO LA DIFFERENZIATA DI DOMANI
        if(vetro.getGiorniDifferenziata().toLowerCase().contains(domani_current)){
            listaDomani.addView(layout_vetro2);
        }
        if(carta.getGiorniDifferenziata().toLowerCase().contains(domani_current)){
            listaDomani.addView(layout_carta2);
        }
        if(multi.getGiorniDifferenziata().toLowerCase().contains(domani_current)){
            listaDomani.addView(layout_multi2);
        }
        if(org.getGiorniDifferenziata().toLowerCase().contains(domani_current)){
            listaDomani.addView(layout_organico2);
        }
        if(inde.getGiorniDifferenziata().toLowerCase().contains(domani_current)){
            listaDomani.addView(layout_inde2);
        }

        super.onViewCreated(view, savedInstanceState);
    }
}
