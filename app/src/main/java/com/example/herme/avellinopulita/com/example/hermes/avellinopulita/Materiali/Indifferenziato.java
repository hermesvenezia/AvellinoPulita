package com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class Indifferenziato implements Parcelable {
    public static final Parcelable.Creator<Indifferenziato> CREATOR = new Parcelable.Creator<Indifferenziato>(){
        public Indifferenziato createFromParcel(Parcel in){
            return new Indifferenziato(in);
        }
        public Indifferenziato[] newArray(int size){
            return new Indifferenziato[size];
        }
    };
    private String name;
    private ImageView imageIcon;
    private String cosaPuoiButtare;
    private String cosaNonPuoiButtare;
    private String giorniDifferenziata;
    private int colore;
    private Indifferenziato(Parcel in){
        name = in.readString();
        cosaPuoiButtare = in.readString();
        cosaNonPuoiButtare = in.readString();
        giorniDifferenziata = in.readString();
    }
    public Indifferenziato(){
        name = "Indifferenziato";
        cosaPuoiButtare = "Pannolini e pannoloni, assorbenti, stracci sporchi, spugne, spazzolini, rasoi, siringhe con ago protetto da cappuccio, garze, oggetti di gomma, " +
                "posate monouso, cicche di sigarette, carte e cialde plastificate, lampadine ad incandescenza, piatti e cocci di ceramica, porcellana e terracotta";
        cosaNonPuoiButtare = "Tutti i materiali separabili e riciclabili (organico, plastica, metalli, carta, cartone e cartoncino, vetro)," +
                "rifiuti urbani (pile e farmaci), ingombranti";
        setGiorni();
        //colore = Color.parseColor("cdcdcf");
    }

    private void setGiorni(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("programmaRaccolta")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        String giorniCustom = "";
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> giorni = document.getData();
                                for (String giorno : giorni.keySet()){
                                    Object giornoObject = giorni.get(giorno);
                                    String giornoString = giornoObject.toString();
                                    if(giornoString.contains("Indifferenziato"))
                                        giorniCustom += giorno + " ";
                                }
                                Log.d("FireBASE/Indifferenziato","VAriabile giorni  custom => " + giorniCustom);
                                setGiorniDifferenziata(giorniCustom);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public String getCosaPuoiButtare() {
        return cosaPuoiButtare;
    }
    public String getCosaNonPuoiButtare(){
        return cosaNonPuoiButtare;
    }

    public String getGiorniDifferenziata() {
        return giorniDifferenziata;
    }

    public void setGiorniDifferenziata(String giorniDifferenziata) {
        this.giorniDifferenziata = giorniDifferenziata;
    }

    public int getColore() {
        return colore;
    }
    public String getName() {
        return name;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(cosaPuoiButtare);
        dest.writeString(cosaNonPuoiButtare);
        dest.writeString(giorniDifferenziata);
    }
}
