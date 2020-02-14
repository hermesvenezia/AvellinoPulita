package com.example.herme.avellinopulita;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.vision.text.Line;

public class ProductActivity extends AppCompatActivity {
    private ImageView image;
    private TextView name_product;
    private boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        flag = false;
        Intent i = getIntent();
        String name = i.getStringExtra("product_name");
        name_product = (TextView) findViewById(R.id.name_product);
        name_product.setText(name);
        String url = i.getStringExtra("urlImage");
        new DownloadImageTask((ImageView)findViewById(R.id.image_url)).execute(url);
        String pack = i.getStringExtra("packaging");
        pack = pack.toLowerCase();
        LinearLayout lista = (LinearLayout) findViewById(R.id.lista);
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        LinearLayout layout_vetro = (LinearLayout) inflater.inflate(R.layout.custom_vetro,null,false);
        LinearLayout layout_multi = (LinearLayout)inflater.inflate(R.layout.custom_multi,null,false);
        LinearLayout layout_carta = (LinearLayout)inflater.inflate(R.layout.custom_carta,null,false);

        if(pack.contains("carton") || pack.contains("papier") || pack.contains("karton") || pack.contains("cartone")){
            //CARTA E CARTONE
            lista.addView(layout_carta);
            flag = true;
        }
        if(pack.contains("conserve") || pack.contains("buatta") || pack.contains("tetra pack") || pack.contains("tetrapack")
        || pack.contains("pe") || pack.contains("pp") || pack.contains("pet") || pack.contains("ps") || pack.contains("canette") || pack.contains("alluminio")
        || pack.contains("alu") || pack.contains("aluminiowa") || pack.contains("métal") || pack.contains("plastica") || pack.contains("plastic")
        || pack.contains("tray")){
            //MULTIMATERIALE PE, PP, PET, PS
            lista.addView(layout_multi);
            flag = true;
        }
        if(pack.contains("bouteille") || pack.contains("verre") || pack.contains("vetro") || pack.contains("bottiglia") || pack.contains("glas")
        || pack.contains("glass")){
            lista.addView(layout_vetro);
            flag = true;
        }
        if(!flag){
            TextView text = new TextView(this);
            text.setText("NESSUNA CORRISPONDENZA TROVATA");
            lista.addView(text);
        }
    }
}
