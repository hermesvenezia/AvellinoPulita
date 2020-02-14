package com.example.herme.avellinopulita.com.example.hermes.avellinopulita.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.herme.avellinopulita.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class FragmentSmaltimentoRifiutoSpeciale extends Fragment {
    private boolean bottonClicked;
    private EditText nome;
    private EditText cognome;
    private EditText indirizzo;
    private EditText email;
    private EditText email_confirmed;
    private EditText numero_telefono;
    private Button invia;
    private static final int CAMERA_REQUEST = 1888;
    private boolean photoTaked;
    private ImageView imageCamera;
    private String  imageURI;
    private Bitmap photo;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_smaltimento_rifiuto_speciale, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        nome = (EditText) getView().findViewById(R.id.nome);
        cognome = (EditText) getView().findViewById(R.id.cognome);
        indirizzo = (EditText) getView().findViewById(R.id.indirizzo);
        email = (EditText) getView().findViewById(R.id.email);
        email_confirmed = (EditText) getView().findViewById(R.id.email_conferma);
        numero_telefono = (EditText) getView().findViewById(R.id.numerotelefono);
        imageCamera = (ImageView) getView().findViewById(R.id.foto);
        imageCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        });
        invia = (Button) getView().findViewById(R.id.button);
        invia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(nome.getText()) || TextUtils.isEmpty(cognome.getText()) || TextUtils.isEmpty(indirizzo.getText())
                || TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(email_confirmed.getText()) || TextUtils.isEmpty(numero_telefono.getText())){
                    Toast.makeText(getActivity(),"Compila tutti i campi", Toast.LENGTH_LONG).show();
                }else if(!photoTaked){
                    Toast.makeText(getActivity(),"Scattare la foto al prodotto cliccando l'icona della camera", Toast.LENGTH_LONG).show();
                }else if(!email.getText().toString().equals(email_confirmed.getText().toString())){
                    Toast.makeText(getActivity(),"Email non uguali",Toast.LENGTH_SHORT).show();
                }else{
                    uploadFirebase();
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            photo = (Bitmap) data.getExtras().get("data");
            ImageView imageCamera2 = (ImageView) getView().findViewById(R.id.foto);
            imageCamera2.setImageDrawable(getResources().getDrawable(R.drawable.ic_done_black_24dp));
            photoTaked = true;
        }
    }
    private void uploadFirebase(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://avellino-pulita.appspot.com/");
        final StorageReference imageRef = storageRef.child(indirizzo.getText().toString());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            Toast.makeText(getContext(),"NESSUNA CONNESSIONE INTERNET. RIPROVARE", Toast.LENGTH_LONG).show();
        } else {
            UploadTask uploadTask = imageRef.putBytes(data);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            setImageURI(uri.toString());
                            Log.d("LOOGG:", "URL : " + getImageURI());
                            EditText mnome = (EditText) getView().findViewById(R.id.nome);
                            EditText mcognome = (EditText) getView().findViewById(R.id.cognome);
                            EditText mindirizzo = (EditText) getView().findViewById(R.id.indirizzo);
                            EditText memail = (EditText) getView().findViewById(R.id.email);
                            EditText mnumero_telefono = (EditText) getView().findViewById(R.id.numerotelefono);
                            String nome = mnome.getText().toString();
                            String cognome = mcognome.getText().toString();
                            String indirizzo = mindirizzo.getText().toString();
                            String email = memail.getText().toString();
                            String numero = mnumero_telefono.getText().toString();
                            String urlImage = uri.toString();
                            Map<String, Object> doc = new HashMap<>();
                            doc.put("numero",numero);
                            doc.put("nome", nome);
                            doc.put("cognome", cognome);
                            doc.put("foto", urlImage);
                            doc.put("via", indirizzo);
                            doc.put("email", email);
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("richiestaRifiutiSpeciali").add(doc).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    EditText mnome = (EditText) getView().findViewById(R.id.nome);
                                    EditText mcognome = (EditText) getView().findViewById(R.id.cognome);
                                    EditText mindirizzo = (EditText) getView().findViewById(R.id.indirizzo);
                                    EditText memail = (EditText) getView().findViewById(R.id.email);
                                    EditText memail_conf = (EditText) getView().findViewById(R.id.email_conferma);
                                    EditText mnumero_telefono = (EditText) getView().findViewById(R.id.numerotelefono);
                                    ImageView imageCamera2 = (ImageView) getView().findViewById(R.id.foto);
                                    imageCamera2.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_camera));
                                    mnome.setText("");
                                    mcognome.setText("");
                                    mindirizzo.setText("");
                                    memail.setText("");
                                    mnumero_telefono.setText("");
                                    memail_conf.setText("");
                                    Toast.makeText(getContext(), "RICHIESTA INVIATA", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }
    public String getImageURI(){
        return this.imageURI;
    }
}
