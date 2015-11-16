package be.ti.groupe2.projetintegration;

import android.content.Intent;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import be.ti.groupe2.projetintegration.Task.Connexion;
import be.ti.groupe2.projetintegration.Task.TaskInscription;


public class MainActivity extends Activity implements View.OnClickListener,Connexion.CustomInterface,TaskInscription.CustomInscription{

        Button connexion;
        Button inscription;
        TextView tLogin;
        TextView tMdp;
        TextView cError;
        CheckBox cb_rememberMe;
        String login;
        String mdp;
        Boolean granted = false;
        String resu;

        public TextView lv = null;
        public static final String myJson = null;
        public static final String MY_JSON ="MY_JSON";
        public static final String URL_CONNEXION = "http://projet_groupe2.hebfree.org/connexion3.php";
        private static final String ID = "userID";

        public static final String JSON_URL2 = "http://projet_groupe2.hebfree.org/Events.php";
        public static final String JSON_URL3 = "http://projet_groupe2.hebfree.org/Clients.php";
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

            cb_rememberMe = (CheckBox) findViewById(R.id.cb_rememberMe);

            cError.setVisibility(View.INVISIBLE);

            context = (VariableGlobale) this.getApplicationContext();

            functions.getJSON(JSON_URL3, lv);


            //connexion.setOnClickListener(this);
            inscription.setOnClickListener(this);

            //connexion
            et_main_login = (EditText) findViewById(R.id.tLogin);
            et_main_password = (EditText) findViewById(R.id.tMdp);
            btn_connexion = (Button) findViewById(R.id.tConnexion);
            pb_main_connexion = (ProgressBar) findViewById(R.id.pb_mainAct);

            btn_connexion.setOnClickListener(this);

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            cb_rememberMe.setChecked(prefs.getBoolean("remember", false));
            et_main_login.setText(prefs.getString("username", ""));
        }

        @Override
        public void onClick(View v) {
            if(v == btn_connexion){
                String username = et_main_login.getText().toString();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = prefs.edit();
                if (cb_rememberMe.isChecked()) {
                    editor.putString("username", username);
                    editor.putBoolean("remember", true);
                    editor.apply();
                } else {
                    editor.remove("username");
                    editor.putBoolean("remember", false);
                    editor.apply();
                }
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

                    task.execute(URL_CONNEXION,login,mdp);
                }
                else{
                    Toast.makeText(this, "Erreur login/mot de passe incorrects", Toast.LENGTH_SHORT).show();
                }

                //JSONArray result = functions.extractJson(lv.getText().toString());

                //id=functions.searchLogin(result, granted, login, mdp);

                //context.setiDUser(id);

               // granted =true;

                /*if(granted) {
                    System.out.println("Connexion réussie");

                    functions.getJSON(JSON_URL2, lv);
                    functions.extractJson(lv.getText().toString());
                    String events = lv.getText().toString();

                    context.getApplicationContext();

                    System.out.println("---------------    "+ context);

                    context.setListEvent(events);
                    Intent profilFilActu = new Intent(this, filActu.class);
                    startActivity(profilFilActu);
                }*/
                /*else {
                    cError.setText("Login/mdp incorrect(s)");
                    cError.setVisibility(View.VISIBLE);
                }*/
            }
            else if(v == inscription){
                Intent intentInscription = new Intent(getApplicationContext(), be.ti.groupe2.projetintegration.Inscription.class);
                startActivity(intentInscription);
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
            //Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            try {
                JSONArray result = functions.extractJson(s);
                JSONObject jsonObject =  result.getJSONObject(0);
                id = jsonObject.getInt("userID");

                functions.getJSON(JSON_URL3, lv);
                JSONArray result1 = functions.extractJson(lv.getText().toString());

                context.setiDUser(id);
                System.out.println("Connexion réussie");
                String events = lv.getText().toString();
                context.getApplicationContext();
                System.out.println("---------------    " + context);
                context.setListEvent(events);
                Intent profilFilActu = new Intent(this, filActu.class);
                startActivity(profilFilActu);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}