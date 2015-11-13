package be.ti.groupe2.projetintegration;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.hardware.camera2.TotalCaptureResult;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import be.ti.groupe2.projetintegration.Task.Connexion;
import be.ti.groupe2.projetintegration.Task.Inscription;

public class MainActivity extends Activity implements View.OnClickListener,Connexion.CustomInterface,Inscription.CustomInscription{

        Button connexion;
        Button inscription;
        TextView tLogin;
        TextView tMdp;
        TextView cError;
        String login;
        String mdp;
        Boolean granted = false;
        String resu;

        public static  TextView lv = null;
        public static final String myJson = null;
        public static final String MY_JSON ="MY_JSON";
        public static final String URL_CONNEXION = "http://projet_groupe2.hebfree.org//connexion3.php";
        private static final String ID = "userID";

        public static final String JSON_URL2 = "http://projet_groupe2.hebfree.org/Events.php";
        private static final String EVENTNAME = "eventName";
        private static final String USERID = "sClient";


        //connexion
        private EditText et_main_login;
        private EditText et_main_password;
        private Button btn_connexion;
        private ProgressBar pb_main_connexion;
        int id;
        VariableGlobale context;




        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //connexion = (Button) findViewById(R.id.tConnexion);
            inscription = (Button) findViewById(R.id.button2);

            lv = (TextView) findViewById(R.id.lv);
            //tLogin = (TextView) findViewById(R.id.tLogin);
            //tMdp =(TextView) findViewById(R.id.tMdp);
            cError = (TextView) findViewById(R.id.Error);


            cError.setVisibility(View.INVISIBLE);

            context = (VariableGlobale) this.getApplicationContext();

            //functions.getJSON(JSON_URL, lv);


            //connexion.setOnClickListener(this);
            inscription.setOnClickListener(this);

            //connexion
            et_main_login = (EditText) findViewById(R.id.tLogin);
            et_main_password = (EditText) findViewById(R.id.tMdp);
            btn_connexion = (Button) findViewById(R.id.tConnexion);
            pb_main_connexion = (ProgressBar) findViewById(R.id.pb_mainAct);

            btn_connexion.setOnClickListener(this);
        }
/*
@Override
        public void onClick(View v) {
    if (v == connexion) {
        login = tLogin.getText().toString();
        mdp = tMdp.getText().toString();
        int id;

        JSONArray result = functions.extractJson(lv.getText().toString());

        id = functions.searchLogin(result, granted, login, mdp);

        context.setiDUser(id);
*/
        @Override
<<<<<<< HEAD
        public void onClick (View v){
            if (v == connexion) {
                login = tLogin.getText().toString();
                mdp = tMdp.getText().toString();

                System.out.println("login : " + login);
                System.out.println("myjson : " + myJson);


                JSONArray result = functions.extractJson(lv.getText().toString());
=======
        public void onClick(View v) {
            if(v == btn_connexion){
                login = et_main_login.getText().toString();
                mdp = et_main_password.getText().toString();
                //int id;
                Connexion task = new Connexion(this);
                if((login != null && !login.equals("")) && (mdp != null && !mdp.equals(""))){
                    /*try {
                        passHash = SHA1(pass);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }*/
                    Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();

                    task.execute(URL_CONNEXION,login,mdp);
                }
                else{
                    Toast.makeText(this, "Erreur login/mot de passe incorrects", Toast.LENGTH_SHORT).show();
                }

                //JSONArray result = functions.extractJson(lv.getText().toString());

                //id=functions.searchLogin(result, granted, login, mdp);

                //context.setiDUser(id);
>>>>>>> origin/master

                functions.searchLogin(result, granted, login, mdp);

                granted = true;

                if (granted) {
                    System.out.println("Connexion réussie");
                    String events = lv.getText().toString();
                    context.getApplicationContext();
                    System.out.println("---------------    " + context);
                    context.setListEvent(events);
                    Intent profilFilActu = new Intent(this, filActu.class);
                    startActivity(profilFilActu);
                } else {
                    cError.setText("Login/mdp incorrect(s)");
                    cError.setVisibility(View.VISIBLE);
                }
            } else if (v == inscription) {
                //  functions.VersInscription(v);
            }
        }
<<<<<<< HEAD
    }
=======

    @Override
    public void showProgressBarInscription() {

    }

    @Override
    public void hideProgressBarInscription() {

    }

    @Override
    public void showResultInscription(String s) {

    }

    @Override
    public void showProgressBar() {
        pb_main_connexion.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        pb_main_connexion.setVisibility(View.GONE);

    }

    @Override
    public void showResult(String s) {
        if(s.equals("-1"))
            Toast.makeText(this, "Login/mot de passe incorrect", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            try {
                JSONObject jo = new JSONObject(s);
                id = jo.getInt("userID");
                Toast.makeText(this, "id : " + id, Toast.LENGTH_SHORT).show();
                context.setiDUser(id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
>>>>>>> origin/master
