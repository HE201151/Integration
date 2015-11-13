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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.ti.groupe2.projetintegration.Task.Connexion;
import be.ti.groupe2.projetintegration.Task.SearchUser;
import be.ti.groupe2.projetintegration.Task.update_pseudo;


public class GestionDuProfil extends Activity implements View.OnClickListener,SearchUser.CustomInterface,update_pseudo.CustomInterface{

    Button accueil;
    Button event;
    Button profil;
    Button confirm;
    public static  TextView lv = null;
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
        confirm = (Button) findViewById(R.id.confirm);


        accueil.setOnClickListener(this);
        event.setOnClickListener(this);
        profil.setOnClickListener(this);
        confirm.setOnClickListener(this);

        int id = context.getiDUser();

        SearchUser task = new SearchUser(this);

        task.execute(JSON_URL, String.valueOf(id));
/*
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
*/



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
                Toast.makeText(this, "Tu cliques", Toast.LENGTH_SHORT).show();
                change_profil();
                break;
        }
    }


    @Override
    public void showResult2(String s) {
        if(s.equals("-1"))
            Toast.makeText(this, "Erreur", Toast.LENGTH_SHORT).show();
        else {

            try {


                JSONArray result = functions.extractJson(s );
                JSONObject jsonObject = result.getJSONObject(0);


                //EditText name = (EditText) findViewById(R.id.name);
                //EditText firstname = (EditText) findViewById(R.id.first_name);

                EditText pseudo = (EditText) findViewById(R.id.pseudo);

                pseudo.setText(jsonObject.getString("userLogin"));
                context.setPseudo(pseudo.getText().toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
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

        verif_champs3(pseudo1,name1,firstname1,pass1,new_pass1,conf_pass1);

    }



    public void verif_champs3(String pseudo, String name, String first_name, String pass, String new_pass, String confirm){
        //récup user

        Toast.makeText(this, "CLICK", Toast.LENGTH_SHORT).show();
        user user1 = new user();
        user1.setPseudo(context.getPseudo());
        boolean ok;

        ok=verif_pseudo2(user1, pseudo);
        if (ok){
            //ok=verif_pass2(user1, pass, new_pass, confirm);

        }

    }

    public void verif_champs2(String pseudo, String name, String first_name, String pass, String new_pass, String confirm){
        //récup user
        user user1 = new user();
        boolean ok;
        ok=verif_pseudo2(user1, pseudo);
        if (ok){
            ok=verif_name(user1.name, name);
            if (ok){
                ok=verif_first_name(user1.first_name,first_name);
                if (ok){
                    ok=verif_pass2(user1, pass, new_pass, confirm);
                    if (ok){
                        //requete update mdp
                    }
                }
            }
        }
        else {
            Toast.makeText(this, "Pseudo existant", Toast.LENGTH_SHORT).show();
        }



    }




    private boolean verif_pseudo2(user user1, String pseudo) {
        //int nbUsers=20;

        Toast.makeText(this,"Pseudo post= "+pseudo, Toast.LENGTH_SHORT).show();

        Toast.makeText(this, "Pseudo context= "+user1.pseudo, Toast.LENGTH_SHORT).show();
        boolean ok=true;
        if(pseudo.equals(user1.pseudo)){
            Toast.makeText(this, "Pseudo inchange", Toast.LENGTH_SHORT).show();
            //System.out.println("Pseudo inchangé");
            return true;
        }
        else{
            Toast.makeText(this, "Change pseudo", Toast.LENGTH_SHORT).show();
            //requete update pseudo
            //requete renvoie -1 si pseudo existe déjà

            int id = context.getiDUser();

            update_pseudo task2 = new update_pseudo(this);

            task2.execute(JSON_URL2, String.valueOf(id), pseudo);

        }
        if (ok) {                                                     //je retourne si c'est ok ou pas
            return true;
        }
        else{
            return false;
        }
    }



    /*public void verif_champs(String pseudo, String name, String first_name, String pass, String new_pass, String confirm){
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



    }*/

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

    public boolean verif_name(String name1, String name){
        boolean ok=true;
        if (name1==name) {

            Toast.makeText(this, "name inchange", Toast.LENGTH_SHORT).show();
            return ok;
        }

        else {


            if (name.length() < 3) {
                ok = false;
                //System.out.print("Nom trop court");
                Toast.makeText(this, "Nom trop court", Toast.LENGTH_SHORT).show();
            } else {
                //requete update name
                ok = true;
            }
        }

        if (ok) {                                                     //je retourne si c'est ok ou pas
            return true;
        }
        else{
            return false;
        }

    }

    public boolean verif_first_name(String fname1, String first_name){
        boolean ok=true;

        if (fname1==first_name){
            Toast.makeText(this, "First name inchange", Toast.LENGTH_SHORT).show();
            return ok;

        }

        else {
            if (first_name.length() < 3) {
                ok = false;
                Toast.makeText(this, "Prenom trop court", Toast.LENGTH_SHORT).show();
            } else {

            }
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


    public boolean verif_pass2(user user1, String pass, String new_pass, String confirm){
        boolean ok=true;

        String mdp=user1.pass;
        if(mdp==pass){
            if(new_pass!=confirm){
                ok=false;

                Toast.makeText(this, "mauvaise confirmation", Toast.LENGTH_SHORT).show();
                //System.out.print("Erreur mot de passe et confirmation différents");
            }
            else{
                if(new_pass.length() >3){
                    //requete update mdp
                    ok = true;
                }
                else{
                    ok= false;
                    Toast.makeText(this, "MDP trop court", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else{
            ok=false;
            Toast.makeText(this, "Mauvais mdp", Toast.LENGTH_SHORT).show();
            //System.out.print("Mauvais mot de passe");
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
    public void showResult3(String s) {
        if(s.equals("-1"))
            Toast.makeText(this, "Erreur", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
/*
            try {


                JSONArray result = functions.extractJson(s );
                JSONObject jsonObject = result.getJSONObject(0);


                //EditText name = (EditText) findViewById(R.id.name);
                //EditText firstname = (EditText) findViewById(R.id.first_name);

                EditText pseudo = (EditText) findViewById(R.id.pseudo);

                pseudo.setText(jsonObject.getString("userLogin"));
                context.setPseudo(pseudo.getText().toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }
    }


}