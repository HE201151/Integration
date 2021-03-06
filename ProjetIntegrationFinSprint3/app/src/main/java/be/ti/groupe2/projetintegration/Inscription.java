package be.ti.groupe2.projetintegration;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import be.ti.groupe2.projetintegration.Task.TaskInscription;

public class Inscription extends Activity implements TaskInscription.CustomInscription, View.OnClickListener {

    TextView defaut = null;
    EditText login = null;
    EditText mdp = null;
    EditText Cmdp = null;
    EditText mail = null;
    EditText nom = null;
    EditText prenom = null;
    Button enregistrer = null;
    String erreur = null;

    String sLogin = "";
    String sMail = "";
    String sMdp = "";
    String sCMdp = "";
    String sNom = "";
    String sPrenom = "";

    String mdpSafe;

    public static final String URL_INSCRIPTION= "http://projet_groupe2.hebfree.org/inscription3.php";
    @Override
    public void onCreate(Bundle savedInstanceState) {


        //afficher GUI
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);

        //récupération des vues qu'on a besoin
        defaut = (TextView)findViewById(R.id.defaut);
        login = (EditText) findViewById(R.id.login);
        mail = (EditText)findViewById(R.id.email);
        mdp = (EditText)findViewById(R.id.mdp);
        Cmdp = (EditText)findViewById(R.id.Cmdp);
        enregistrer = (Button)findViewById(R.id.enregistrer);
        nom = (EditText)findViewById(R.id.nom);
        prenom = (EditText)findViewById(R.id.prenom);

        enregistrer.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.enregistrer:{
                sLogin = login.getText().toString();
                sMdp = mdp.getText().toString();
                sCMdp = Cmdp.getText().toString();
                sMail = mail.getText().toString();
                sNom = nom.getText().toString();
                sPrenom = prenom.getText().toString();

                TaskInscription inscription = new TaskInscription(this);

                if((sLogin!=null && !sLogin.equals("")) && (sMdp!=null && !sMdp.equals(""))
                        && (sCMdp != null && !sCMdp.equals("")) && (sMail!=null && !sMail.equals(""))){

                    if(sMdp.equals(sCMdp)){

                        MessageDigest md = null;
                        try {
                            md = MessageDigest.getInstance("SHA-256");
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }

                        try {
                            md.update(sMdp.getBytes("UTF-8")); // Change this to "UTF-16" if needed
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        byte[] digestMdp = md.digest();

                        mdpSafe = new String(digestMdp);

                        inscription.execute(URL_INSCRIPTION, sLogin, mdpSafe, sCMdp, sMail, sNom, sPrenom);

                    }
                    else
                        Toast.makeText(this, "MDP pas égales", Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(this, "Les champs ne peuvent être vide", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void showProgressBarInscription() {

    }

    @Override
    public void hideProgressBarInscription() {

    }

    @Override
    public void showResultInscription(String s) {
        if(s.equals("-1")){
            Toast.makeText(this, "Utilisateur déjà présent", Toast.LENGTH_SHORT).show();
        }
        else if(s.equals("-2")){
            Toast.makeText(this, "Mail déjà enregistré", Toast.LENGTH_SHORT).show();
        }
        else if(s.equals("-3")){
            Toast.makeText(this, "Utilisateur ajouté", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

    }
}