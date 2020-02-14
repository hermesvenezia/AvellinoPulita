package com.example.herme.avellinopulita;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Carta;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Indifferenziato;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Multimateriale;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Organico;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.Materiali.Vetro;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.fragment.FragmentCosaButtoOggi;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.fragment.FragmentDoveLoButto;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.fragment.FragmentGiorniDellaDifferenziata;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.fragment.FragmentSegnalazioni;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.fragment.FragmentSmaltimentoRifiutoSpeciale;
import com.example.herme.avellinopulita.com.example.hermes.avellinopulita.fragment.HomeFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Organico organico;
    private Multimateriale multimateriale;
    private Carta carta;
    private Vetro vetro;
    private Indifferenziato indifferenziato;
    private Fragment home;
    private Fragment cosaButtoOggi;
    private Fragment doveLoButto;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        organico = new Organico();
        vetro = new Vetro();
        multimateriale = new Multimateriale();
        indifferenziato = new Indifferenziato();
        carta = new Carta();
        bundle = new Bundle();
        bundle.putParcelable("Carta",carta);
        bundle.putParcelable("Vetro",vetro);
        bundle.putParcelable("Multimateriale",multimateriale);
        bundle.putParcelable("Organico",organico);
        bundle.putParcelable("Indifferenziato",indifferenziato);
        cosaButtoOggi = new FragmentCosaButtoOggi();
        cosaButtoOggi.setArguments(bundle);
        doveLoButto = new FragmentDoveLoButto();
        doveLoButto.setArguments(bundle);
        home = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, home).commit();
        navigationView.setCheckedItem(R.id.fragment_container);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cosa_butto_oggi) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, cosaButtoOggi).commit();
        } else if (id == R.id.nav_giorni_della_differenziata) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentGiorniDellaDifferenziata()).commit();
        } else if (id == R.id.nav_dove_lo_butto) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, doveLoButto).commit();
        } else if (id == R.id.nav_smaltimento_rifiuto_speciale) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentSmaltimentoRifiutoSpeciale()).commit();
        } else if (id == R.id.nav_segnalazioni) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentSegnalazioni()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
