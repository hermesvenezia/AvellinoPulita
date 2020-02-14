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

public class Vetro implements Parcelable {
        public static final Parcelable.Creator<Vetro> CREATOR = new Parcelable.Creator<Vetro>(){
            public Vetro createFromParcel(Parcel in){
                return new Vetro(in);
            }
            public Vetro[] newArray(int size){
                return new Vetro[size];
            }
        };
    private String name;
    private ImageView imageIcon;
    private String cosaPuoiButtare;
    private String cosaNonPuoiButtare;
    private String giorniDifferenziata;
    private int colore;
        private Vetro(Parcel in){
            name = in.readString();
            cosaPuoiButtare = in.readString();
            cosaNonPuoiButtare = in.readString();
            giorniDifferenziata = in.readString();
        }
    public Vetro(){
        name = "Vetro";
        cosaPuoiButtare = "Bottiglie, barattoli, flaconi, vasetti (vuoti, sciacquati e senza tapi).";
        cosaNonPuoiButtare = "Lampadine e lampade, lastre di vetro, oggetti in cristallo, oggetti in ceramica " +
                ", porcellana, terracotta";
        setGiorni();
        //colore = Color.parseColor("fbe3c1");

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
                                    if(giornoString.contains("Vetro"))
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

    public int getColore() {
        return colore;
    }

        public String getGiorniDifferenziata() {
            return giorniDifferenziata;
        }

        public void setGiorniDifferenziata(String giorniDifferenziata) {
            this.giorniDifferenziata = giorniDifferenziata;
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

