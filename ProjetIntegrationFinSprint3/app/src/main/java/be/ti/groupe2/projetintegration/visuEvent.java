package be.ti.groupe2.projetintegration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class VisuEvent extends Activity implements View.OnClickListener {

    VariableGlobale context;

    Button accueil;
    Button event;
    Button profil;

    TextView nom;
    TextView nb;
    TextView autor;
    TextView descrip;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);

        accueil = (Button) findViewById(R.id.bouton_accueil);
        event = (Button) findViewById(R.id.bouton_event);
        profil = (Button) findViewById(R.id.bouton_profil);
        nom = (TextView) findViewById(R.id.nomEv);
        nb = (TextView) findViewById(R.id.etapeEv);
        autor = (TextView) findViewById(R.id.autorEv);
        descrip = (TextView) findViewById(R.id.desEv);

        context = (VariableGlobale) this.getApplicationContext();

        Event e = context.getEvent();

        System.out.println("nom : "+e.getNom());
        nom.setText(e.getNom());
       /* nb.setText(e.getNb());
        autor.setText(e.getAutor());
        descrip.setText(e.getDescription()); */

        accueil.setOnClickListener(this);
        event.setOnClickListener(this);
        profil.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bouton_accueil:
                Intent gestionEventFilActu = new Intent(this, FilActu.class);
                startActivity(gestionEventFilActu);
                break;
            case R.id.bouton_event:
                Intent gestionEventGestionEvenement = new Intent(this, GestionEvenement.class);
                startActivity(gestionEventGestionEvenement);
                break;
            case R.id.bouton_profil:
                Intent gestionEventGestionDuProfil = new Intent(this, GestionDuProfil.class);
                startActivity(gestionEventGestionDuProfil);
                break;
        }
    }
}
