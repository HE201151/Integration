package be.ti.groupe2.projetintegration;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GestionDuProfil extends Activity implements View.OnClickListener{

    Button accueil;
    Button event;
    Button profil;
    public static  TextView lv = null;
    public static final String JSON_URL = "http://projet_groupe2.hebfree.org/searchUser.php";

    VariableGlobale context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_du_profil);
        context = (VariableGlobale) this.getApplicationContext();

        accueil = (Button) findViewById(R.id.bouton_accueil);
        event = (Button) findViewById(R.id.bouton_event);
        profil = (Button) findViewById(R.id.bouton_profil);

        accueil.setOnClickListener(this);
        event.setOnClickListener(this);
        profil.setOnClickListener(this);


        try {


            JSONArray result = functions.extractJson(JSON_URL );
            JSONObject jsonObject = result.getJSONObject(0);


            //EditText name = (EditText) findViewById(R.id.name);
            //EditText firstname = (EditText) findViewById(R.id.first_name);

            EditText pseudo = (EditText) findViewById(R.id.pseudo);

            pseudo.setText(jsonObject.getString("userLogin"));

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bouton_accueil:
                Intent profilFilActu = new Intent(this, filActu.class);
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
            case R.id.confirm:
                change_profil();
                break;
        }
    }


    public void change_profil() {
        //setContentView(R.layout.activity_gestion_du_profil);
        EditText name = (EditText) findViewById(R.id.name);
        EditText firstname = (EditText) findViewById(R.id.first_name);
        EditText pseudo = (EditText) findViewById(R.id.pseudo);
        EditText pass = (EditText) findViewById(R.id.password);
        EditText new_pass = (EditText) findViewById(R.id.new_pass);
        EditText conf_pass = (EditText) findViewById(R.id.confirm_pass);


        String name1=name.getText().toString();
        String firstname1=firstname.getText().toString();
        String pseudo1=pseudo.getText().toString();
        String pass1=pass.getText().toString();
        String new_pass1=new_pass.getText().toString();
        String conf_pass1=conf_pass.getText().toString();

        verif_champs(pseudo1,name1,firstname1,pass1,new_pass1,conf_pass1);

    }



    public void verif_champs(String pseudo, String name, String first_name, String pass, String new_pass, String confirm){
        int id;
        boolean granted=false;
        id = context.getiDUser();
        int nb_users=0;
        lv = (TextView) findViewById(R.id.lv);
        functions.getJSON(JSON_URL, lv);
        JSONArray result = functions.extractJson(lv.getText().toString());
        //String s = ((MainActivity) this.getApplication()).getGlobal_pseudo();
        //int id = 0;

        //requête récupère user grâce à l'id
        user user1;
        user1 = functions.searchUser(result, granted, id);
        //requête récupère tableau

        user[] t_users = new user[10];
        boolean ok;
        ok=verif_pseudo(user1, pseudo, t_users, nb_users);
        if (ok){
            ok=verif_name(name);
            if (ok){
                ok=verif_first_name(first_name);
                if (ok){
                    ok=verif_pass(user1,t_users,pass,new_pass,confirm);
                }
            }
        }



    }

    public boolean verif_pseudo(user user1,String pseudo, user[] t_users, int nb_users){
        //int nbUsers=20;
        boolean ok=true;
        if(pseudo == user1.pseudo){
            System.out.println("Pseudo inchangé");
            return true;
        }
        else{
            String pseudo2;

            pseudo=pseudo.toLowerCase();                                //Je met les pseudo en lower case pour les comparer
            for(int i =0; i<nb_users; i++){
                //System.out.print(i);
                pseudo2=t_users[i].pseudo;
                pseudo2=pseudo2.toLowerCase();                          //Je compare chaque pseudo du tableau de users
                if (pseudo == pseudo2){
                    System.out.print("Pseudo déjà utilisé");            //S'il y a un doublon, c'est pas ok
                    ok=false;
                }
            }
            if (ok) {                                                     //je retourne si c'est ok ou pas
                return true;
            }
            else{
                return false;
            }
        }
    }

    public boolean verif_name(String name){
        boolean ok=true;
        if (name.length()<3){
            ok=false;
            System.out.print("Nom trop court");
        }

        if (ok) {                                                     //je retourne si c'est ok ou pas
            return true;
        }
        else{
            return false;
        }

    }

    public boolean verif_first_name(String first_name){
        boolean ok=true;
        if (first_name.length()<3){
            ok=false;
            System.out.print("Prénom trop court");
        }

        if (ok) {                                                     //je retourne si c'est ok ou pas
            return true;
        }
        else{
            return false;
        }

    }

    public boolean verif_pass(user user1, user[] t_users, String pass, String new_pass, String confirm){
        boolean ok=true;
        //T_user t_users;
        //requete pour trouver le user avec le pseudo
        String mdp=user1.pass;
        if(mdp==pass){
            if(new_pass!=confirm){
                ok=false;
                System.out.print("Erreur mot de passe et confirmation différents");
            }
        }
        else{
            ok=false;
            System.out.print("Mauvais mot de passe");
        }
        return ok;
    }
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              