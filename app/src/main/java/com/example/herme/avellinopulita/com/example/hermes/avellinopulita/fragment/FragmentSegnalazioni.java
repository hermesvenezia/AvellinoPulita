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

public class FragmentSegnalazioni extends Fragment {
    private EditText descrizione;
    private EditText via;
    private boolean bottonClicked;
    private boolean photoTaked;
    private ImageView imageCamera;
    private String imageUri;
    private Bitmap photo;
    private static final int CAMERA_REQUEST = 1889;
    private Button invia;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_segnalazioni, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        via = (EditText) getView().findViewById(R.id.via);
        descrizione = (EditText) getView().findViewById(R.id.descrizione);
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
                if(TextUtils.isEmpty(via.getText()) || TextUtils.isEmpty(descrizione.getText())){
                    Toast.makeText(getActivity(),"Compila tutti i campi", Toast.LENGTH_LONG).show();
                }else if(!photoTaked){
                    Toast.makeText(getActivity(),"Scattare la foto al prodotto cliccando l'icona della camera", Toast.LENGTH_LONG).show();
                }else{
                    uploadFirebase();
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
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
    private void uploadFirebase() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://avellino-pulita.appspot.com/");
        final StorageReference imageRef = storageRef.child(via.getText().toString());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imageRef.putBytes(data);
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            Toast.makeText(getContext(),"NESSUNA CONNESSIONE INTERNET. RIPROVARE", Toast.LENGTH_LONG).show();
        } else {
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            setImageURI(uri.toString());
                            Log.d("LOOGG:", "URL : " + getImageURI());
                            String urlImage = uri.toString();
                            Map<String, Object> doc = new HashMap<>();
                            doc.put("via", via.getText().toString());
                            doc.put("descrizione", descrizione.getText().toString());
                            doc.put("foto", urlImage);
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("segnalazioni").add(doc).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    Toast.makeText(getContext(), "SEGNALAZIONE INVIATA", Toast.LENGTH_SHORT).show();
                                    via.setText("");
                                    descrizione.setText("");
                                    imageCamera.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_camera));
                                }
                            });
                        }
                    });
                }
            });
        }
    }
    public void setImageURI(String imageURI) {
        this.imageUri = imageURI;
    }
    public String getImageURI(){
        return this.imageUri;
    }
}
