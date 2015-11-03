package be.ti.groupe2.projetintegration;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONArray;

public class MainActivity extends Activity implements View.OnClickListener {

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
        public static final String JSON_URL = "http://projet_groupe2.hebfree.org/Clients.php";
        private static final String ID = "userID";

        VariableGlobale context;



        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            connexion = (Button) findViewById(R.id.button);
            inscription = (Button) findViewById(R.id.button2);

            lv = (TextView) findViewById(R.id.lv);
            tLogin = (TextView) findViewById(R.id.tLogin);
            tMdp =(TextView) findViewById(R.id.tMdp);
            cError = (TextView) findViewById(R.id.Error);


            cError.setVisibility(View.INVISIBLE);

            context = (VariableGlobale) this.getApplicationContext();

            functions.getJSON(JSON_URL, lv);


            connexion.setOnClickListener(this);
            inscription.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v == connexion){
                login = tLogin.getText().toString();
                mdp = tMdp.getText().toString();

                System.out.println("login : " + login);
                System.out.println("myjson : " + myJson);


                JSONArray result = functions.extractJson(lv.getText().toString());

                functions.searchLogin(result, granted, login, mdp);

                granted =true;

                if(granted) {
                    System.out.println("Connexion réussie");
                    String events = lv.getText().toString();
                    context.getApplicationContext();
                    System.out.println("---------------    "+ context);
                    context.setListEvent(events);
                    Intent profilFilActu = new Intent(this, filActu.class);
                    startActivity(profilFilActu);
                }
                else {
                    cError.setText("Login/mdp incorrect(s)");
                    cError.setVisibility(View.VISIBLE);
                }
            }
            else if(v == inscription){
              //  functions.VersInscription(v);
            }
        }
    }