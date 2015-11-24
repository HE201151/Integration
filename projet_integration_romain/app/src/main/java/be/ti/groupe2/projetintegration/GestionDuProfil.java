package be.ti.groupe2.projetintegration;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import be.ti.groupe2.projetintegration.Task.SearchUser;
import be.ti.groupe2.projetintegration.Task.UpdateProfil;



public class GestionDuProfil extends Activity implements View.OnClickListener,SearchUser.CustomInterface,UpdateProfil.CustomInterface{

    Button accueil;
    Button event;
    Button profil;
    Button confirm;

    EditText name;
    EditText firstname;
    EditText pseudo;
    EditText pass;
    EditText new_pass;
    EditText conf_pass;
    EditText mail;

    User u;

    int id;

    public static final String JSON_URL = "http://projet_groupe2.hebfree.org/searchUser.php";

    public static final String JSON_URL2 = "http://projet_groupe2.hebfree.org/update_pseudo.php";

    VariableGlobale context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_du_profil);

        context = (VariableGlobale) this.getApplicationContext();

        accueil = (Button) findViewById(R.id.bouton_accueil);
        event = (Button) findViewById(R.id.bouton_event);
        profil = (Button) findViewById(R.id.bouton_profil);
        confirm = (Button) findViewById(R.id.confirmProfil);

        name = (EditText) findViewById(R.id.nameProfil);
        firstname = (EditText) findViewById(R.id.first_nameProfil);
        pseudo = (EditText) findViewById(R.id.pseudoProfil);
        pass = (EditText) findViewById(R.id.passwordProfil);
        new_pass = (EditText) findViewById(R.id.new_passProfil);
        conf_pass = (EditText) findViewById(R.id.confirm_passProfil);
        mail = (EditText) findViewById(R.id.mailProfil);



        id = context.getiDUser();
        System.out.println("id : " + id);


        Toast.makeText(this,""+ id, Toast.LENGTH_SHORT).show();

        SearchUser searchUserTask = new SearchUser(this);

        searchUserTask.execute(JSON_URL, String.valueOf(id));


        accueil.setOnClickListener(this);
        event.setOnClickListener(this);
        profil.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bouton_accueil:
                Intent profilFilActu = new Intent(this, FilActu.class);
                startActivity(profilFilActu);
                break;
            case R.id.bouton_event:
                Intent profilGestionEvenement = new Intent(this, GestionEvenement.class);
                startActivity(profilGestionEvenement);
                break;
            case R.id.bouton_profil:
                Intent profilGestionDuProfil = new Intent(this, GestionDuProfil.class);
                startActivity(profilGestionDuProfil);
                break;
            case R.id.confirmProfil:
                Toast.makeText(this, "Vérification", Toast.LENGTH_SHORT).show();
                verifChamp();
                break;
        }
    }


    @Override
    public void showResult2(String s) {
        if (s.equals("-1"))
            Toast.makeText(this, "Erreur", Toast.LENGTH_SHORT).show();
        else {

            try {
                JSONArray result = Functions.extractJson(s);
                JSONObject jsonObject = result.getJSONObject(0);
                Log.i("show2", "BADABOUM");
                String sMdp =  jsonObject.getString("userPassword");

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

                String mdpSafe = new String(digestMdp);

                u = new User(jsonObject.getString("userLogin"),mdpSafe,id,jsonObject.getString("nom"),jsonObject.getString("prenom"),jsonObject.getString("userEmail"));

                Log.i("show3", " : " + u.getFirst_name());

                Log.i("show4", " : " + u.getName());

                pseudo.setText(u.getPseudo());
                name.setText(u.getName());
                firstname.setText(u.getFirst_name());
                mail.setText(u.getMail());


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }





    public void verifChamp(){
        UpdateProfil upProfil = new UpdateProfil(this);



        if((pseudo.getText().toString() != null)|| (name.getText().toString() != null) ||  (firstname.getText().toString() != null)||  (mail.getText().toString() != null)) {
            Toast.makeText(this, "Les champs doivent être remplis!", Toast.LENGTH_SHORT).show();
        }else{
            if((new_pass.getText().toString() != null) || (conf_pass.getText().toString() != null) || (pass.getText().toString() != null)){
                if(verifPass()){
                    Toast.makeText(this, "enregistrer BDD + pass", Toast.LENGTH_SHORT).show();
                    upProfil.execute(JSON_URL, String.valueOf(id), pseudo.getText().toString(), name.getText().toString(), firstname.getText().toString(), conf_pass.getText().toString(), mail.getText().toString());
                }else{
                    Toast.makeText(this, "passe pas bon", Toast.LENGTH_SHORT).show();
                    //upProfil.execute(JSON_URL2, String.valueOf(id), pseudo.getText().toString(), name.getText().toString(), firstname.getText().toString(), conf_pass.getText().toString(), mail.getText().toString());
                }
            }else {
                Toast.makeText(this, "enregistrer BDD - pass", Toast.LENGTH_SHORT).show();
                upProfil.execute(JSON_URL2, String.valueOf(id),pseudo.getText().toString(), name.getText().toString(), firstname.getText().toString(), u.getPass() , mail.getText().toString());
            }
        }

    }

    public boolean verifPass(){
        boolean ok = false;
        String sMdp = pass.getText().toString();
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

        String mdpSafe = new String(digestMdp);

        if(u.getPass()== mdpSafe){
            if(new_pass.getText().toString()==conf_pass.getText().toString()){
                if(new_pass.getText().toString().length() >3){
                    ok = true;
                }
                else {
                    ok = false;
                    Toast.makeText(this, "Mot de passe trop court", Toast.LENGTH_SHORT).show();
                }
            }else{
                ok=false;
                Toast.makeText(this, "Mauvais mot de passe", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            ok=false;
            Toast.makeText(this, "Ancien mot de passe non valide", Toast.LENGTH_SHORT).show();
        }
        return ok;
    }


    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showResult(String s) {
        if(s.equals("-1"))
            Toast.makeText(this, "Erreur", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();

        }
    }


}