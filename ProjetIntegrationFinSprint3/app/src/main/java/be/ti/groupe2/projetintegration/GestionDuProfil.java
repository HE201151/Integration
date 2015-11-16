package be.ti.groupe2.projetintegration;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;


public class GestionDuProfil extends Activity implements View.OnClickListener{

    Button accueil;
    Button event;
    Button profil;

    EditText name;
    EditText firstname;
    EditText pseudo;
    EditText pass;
    EditText new_pass;
    EditText conf_pass;

    User u;

    int id;
    boolean granted;
    int nbUsers;

    public static  TextView lv = null;
    public static final String JSON_URL = "http://projet_groupe2.hebfree.org/Clients.php";

    VariableGlobale context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_du_profil);

        context = (VariableGlobale) this.getApplicationContext();

        accueil = (Button) findViewById(R.id.bouton_accueil);
        event = (Button) findViewById(R.id.bouton_event);
        profil = (Button) findViewById(R.id.bouton_profil);

        name = (EditText) findViewById(R.id.name);
        firstname = (EditText) findViewById(R.id.first_name);
        pseudo = (EditText) findViewById(R.id.pseudo);
        pass = (EditText) findViewById(R.id.password);
        new_pass = (EditText) findViewById(R.id.new_pass);
        conf_pass = (EditText) findViewById(R.id.confirm_pass);

        accueil.setOnClickListener(this);
        event.setOnClickListener(this);
        profil.setOnClickListener(this);

        granted=false;
        id = context.getiDUser();
        u = null;
        JSONArray result = functions.extractJson(context.getlistEvent());
        nbUsers = 35;
        u = functions.searchUser(result, granted, id);

        name.setText(u.getName());
        firstname.setText(u.getFirst_name());
        pseudo.setText(u.getPseudo());
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
                verif_champs(u, nbUsers, pseudo.getText().toString(),name.getText().toString(),firstname.getText().toString(),pass.getText().toString(),new_pass.getText().toString(),conf_pass.getText().toString());

                break;
        }
    }



    public void verif_champs(User u, int n, String pseudo, String name, String first_name, String pass, String new_pass, String confirm){



        User[] t_users = new User[10];
        boolean ok;
        ok = verif_pseudo(u, pseudo, t_users, n);
        if (ok){
            ok=verif_name(name);
            if (ok){
                ok=verif_first_name(first_name);
                if (ok){
                    ok=verif_pass(u,pass,new_pass,confirm);
                }
            }
        }



    }

    public boolean verif_pseudo(User u,String pseudo, User[] t_users, int nb_users){
        boolean ok=true;
        if(pseudo == u.pseudo){
            System.out.println("Pseudo inchangé");
            return true;
        }
        else{
            String pseudo2;

            pseudo=pseudo.toLowerCase();                                //Je met les pseudo en lower case pour les comparer
            for(int i =0; i<nb_users; i++){
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

    public boolean verif_pass(User u, String pass, String new_pass, String confirm){
        boolean ok=true;
        //requete pour trouver le user avec le pseudo
        String mdp=u.pass;
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