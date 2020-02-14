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

public class Multimateriale implements Parcelable {
    public static final Parcelable.Creator<Multimateriale> CREATOR = new Parcelable.Creator<Multimateriale>(){
        public Multimateriale createFromParcel(Parcel in){
            return new Multimateriale(in);
        }
        public Multimateriale[] newArray(int size){
            return new Multimateriale[size];
        }
    };
    private String name;
    private ImageView imageIcon;
    private String cosaPuoiButtare;
    private String cosaNonPuoiButtare;
    private String giorniDifferenziata;
    private int colore;
    private Multimateriale(Parcel in){
        name = in.readString();
        cosaPuoiButtare = in.readString();
        cosaNonPuoiButtare = in.readString();
        giorniDifferenziata = in.readString();
    }
    public Multimateriale(){
        name = "Multimateriale";
        cosaPuoiButtare = "Imballaggi di plastica (vuoti o sciacquati): bottiglie, flaconi per detersivi, piatti e bicchieri monouso, buste, vaschette, polistirolo, pellicole,. La plastica riciclabile è contrassegnata con le sigle: PE, PP, PET, PS (polistirolo)" +
                "Imballaggi di metallo (vuoti e sciacquati): scatolame di metallo per alimenti, lattine per bevande, fogli di alluminio, vaschette in alluminio. Confenzioni Tetra Pak";
        cosaNonPuoiButtare = "Giocattoli rotti, contenitori per liquidi tossici e infiammabili, posate monouso, oggetti gomma, penne, tubi di plastica e metallo";
        setGiorni();
       // colore = Color.parseColor("bdc9e3");
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
                                    if(giornoString.contains("Multimateriale"))
                                        giorniCustom += giorno + " ";
                                }
                                Log.d("FireBASE","VAriabile giorni  custom => " + giorniCustom);
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

