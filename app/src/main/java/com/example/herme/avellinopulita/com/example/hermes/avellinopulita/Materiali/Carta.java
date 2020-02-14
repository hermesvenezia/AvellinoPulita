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

public class Carta implements Parcelable {
    public static final Parcelable.Creator<Carta> CREATOR = new Parcelable.Creator<Carta>(){
        public Carta createFromParcel(Parcel in){
            return new Carta(in);
        }
        public Carta[] newArray(int size){
            return new Carta[size];
        }
    };
    private String name;
    private String cosaPuoiButtare;
    private String cosaNonPuoiButtare;
    private String giorniDifferenziata;
    private Carta(Parcel in){
        name = in.readString();
        cosaPuoiButtare = in.readString();
        cosaNonPuoiButtare = in.readString();
        giorniDifferenziata = in.readString();
    }
    public Carta(){
        name = "Carta";
        cosaPuoiButtare = "Giornali, riviste, imaballagi e scatole di carta e cartoncino, fotocopie e fogli vari";
        cosaNonPuoiButtare = "Carta Plastificata, carta forno, ogni tipo di carta che sia stata sporcata con vernici o altri prodotti tossici";
        setGiorni();
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
                                    if(giornoString.contains("Carta"))
                                        giorniCustom += giorno + " ";
                                }
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

    public String getName() {
        return name;
    }


    public String getGiorniDifferenziata() {
        return giorniDifferenziata;
    }

    public void setGiorniDifferenziata(String giorniDifferenziata) {
        this.giorniDifferenziata = giorniDifferenziata;
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
