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

public class Organico implements Parcelable{
    public static final Parcelable.Creator<Organico> CREATOR = new Parcelable.Creator<Organico>(){
        public Organico createFromParcel(Parcel in){
            return new Organico(in);
        }
        public Organico[] newArray(int size){
            return new Organico[size];
        }
    };
    private String name;
    private ImageView imageIcon;
    private String cosaPuoiButtare;
    private String cosaNonPuoiButtare;
    private String giorniDifferenziata;
    private int colore;

    private Organico(Parcel in){
        name = in.readString();
        cosaPuoiButtare = in.readString();
        cosaNonPuoiButtare = in.readString();
        giorniDifferenziata = in.readString();
    }
    public Organico(){
        name = "Organico";
        cosaPuoiButtare = "Scarti di cucina, avanzi di cibo e frutta, avanzi di crostacei e molluschi, gusci di uova " +
                "e frutta secca, piccoli ossi, alimenti avariati, fondi di caffè e cialde non plastificate, filtri di tè, " +
                "tovaglioli di carta unti, ceneri spente, piccole potature di fiori, piante, sfalci d'erba, foglie.  ";
        cosaNonPuoiButtare = "Pannolini e pannoloni, assorbenti, stracci, spugne, gomme da masticare, cicche di sigarette " +
                "e tutti i matariali differenziabili";
        setGiorni();
       // colore = Color.parseColor("#c7b5a9");
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
                                    if(giornoString.contains("Organico"))
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

    public String getName() {
        return name;
    }
    public int getColore() {return colore;}

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
