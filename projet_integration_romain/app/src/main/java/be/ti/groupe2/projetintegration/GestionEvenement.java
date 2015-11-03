package be.ti.groupe2.projetintegration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GestionEvenement extends Activity implements View.OnClickListener {

    Button accueil;
    Button event;
    Button profil;
    Button creaevent;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestion_evenement);

        accueil = (Button) findViewById(R.id.bouton_accueil);
        event = (Button) findViewById(R.id.bouton_event);
        profil = (Button) findViewById(R.id.bouton_profil);
        creaevent = (Button) findViewById(R.id.bouton_creationEvent);

        accueil.setOnClickListener(this);
        event.setOnClickListener(this);
        profil.setOnClickListener(this);
        creaevent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bouton_accueil:
                Intent gestionEventFilActu = new Intent(this, filActu.class);
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
            case R.id.bouton_creationEvent:
                Intent gestionEventCreationEvent = new Intent(this, CreationEvenement.class);
                startActivity(gestionEventCreationEvent);
                break;
        }
    }
}
