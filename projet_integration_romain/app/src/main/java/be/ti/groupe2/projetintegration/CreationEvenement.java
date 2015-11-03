package be.ti.groupe2.projetintegration;

/**
 * Created by martin on 30-09-15.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;




public class CreationEvenement  extends Activity implements View.OnClickListener {
    // La chaîne de caractères de champVide
    private final String champVide = "Vous devez compléter tous les champs";

    Button btn_suivant = null;
    Button btn_raz = null;
    Button btn_versAccueil = null;
    Button btn_versEvent = null;
    Button btn_versProfil= null;

    TextView messageError =null;

    EditText nomEvent = null;
    EditText motDePasse = null;
    EditText nombreEtape = null;
    EditText localite = null;

    RadioButton radio_public =null;
    RadioButton radio_prive =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creation_evenement);

        // On récupère toutes les vues dont on a besoin
        btn_suivant = (Button)findViewById(R.id.bouton_suivant);
        btn_raz = (Button)findViewById(R.id.bouton_raz);
        btn_versAccueil = (Button)findViewById(R.id.bouton_accueil);
        btn_versEvent = (Button)findViewById(R.id.bouton_event);
        btn_versProfil = (Button)findViewById(R.id.bouton_profil);

        nomEvent = (EditText)findViewById(R.id.EditText_nomEvent);
        motDePasse = (EditText)findViewById(R.id.EditText_motDePasse);
        nombreEtape = (EditText)findViewById(R.id.EditText_nombreEtape);
        localite = (EditText)findViewById(R.id.EditText_localite);

        messageError =(TextView)findViewById(R.id.EditText_messageError);

        radio_public = (RadioButton)findViewById(R.id.radio_public);
        radio_prive = (RadioButton)findViewById(R.id.radio_prive);

        btn_suivant.setOnClickListener(this);
        btn_raz.setOnClickListener(this);
        btn_versEvent.setOnClickListener(this);
        btn_versProfil.setOnClickListener(this);
        radio_public.setOnClickListener(this);
        radio_prive.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bouton_suivant:
                Intent nextActivite = new Intent(CreationEvenement.this, CreationEvenementP2.class);
                startActivity(nextActivite);
                /*
                if(nomEvent.getText().length() == 0){
                    messageError.setText("Vous devez mettre un nom d'évènement.");
                }
                else if(nombreEtape.getText().length() == 0){
                    messageError.setText("Vous devez mettre un nombre d'étapes");
                }
                else{
                    Intent nextActivite = new Intent(CreationEvenement.this, CreationEvenementP2.class);
                    startActivity(nextActivite);
                }*/
                break;
            case R.id.bouton_raz:
                nomEvent.getText().clear();
                motDePasse.getText().clear();
                nombreEtape.getText().clear();
                break;
            case R.id.radio_public:
                motDePasse.setVisibility(View.INVISIBLE);
                break;
            case R.id.radio_prive:
                motDePasse.setVisibility(View.VISIBLE);
                break;
            case R.id.bouton_accueil:
                Intent eventFilActu = new Intent(this, filActu.class);
                startActivity(eventFilActu);
                break;
            case R.id.bouton_event:
                Intent eventGestionEvent = new Intent(this, GestionEvenement.class);
                startActivity(eventGestionEvent);
                break;
            case R.id.bouton_profil:
                Intent eventGestionDuProfil = new Intent(this, GestionDuProfil.class);
                startActivity(eventGestionDuProfil);
                break;
        }
    }
}