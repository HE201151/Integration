package be.ti.groupe2.projetintegration;


import java.util.*;
import android.content.Intent;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.ti.groupe2.projetintegration.Task.ConnEvent;

public class FilActu extends Activity implements View.OnClickListener,ConnEvent.CustomInterface{

    public static final String USER = "userID";
    public static final String NOM = "eventName";
    public static final String URL = "http://projet_groupe2.hebfree.org/Events.php";


    VariableGlobale context;

    private ListView tv;
    private TextView editT;

    Button accueil;
    Button event;
    Button profil;

    List <String> list;
    ArrayAdapter<String> adapter;

    private JSONArray user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);

        tv = (ListView) findViewById(R.id.tvk);
        editT = (TextView) findViewById(R.id.editT);

        context = (VariableGlobale) this.getApplicationContext();

        accueil = (Button) findViewById(R.id.bouton_accueil);
        event = (Button) findViewById(R.id.bouton_event);
        profil = (Button) findViewById(R.id.bouton_profil);

        ConnEvent connEvent = new ConnEvent(this);
        connEvent.execute(URL);

        extractJSON();
        list=new ArrayList<String>();
        int length = user.length();
        System.out.println("nombre d'event : "+length);
        length = length -1;
        while (length >= 0){
            showData(length);
            length --;
        }
        adapter = new ArrayAdapter<String>(this, R.layout.list, R.id.editT, list);




        tv.setAdapter(adapter);


        accueil.setOnClickListener(this);
        event.setOnClickListener(this);
        profil.setOnClickListener(this);
    }


    private void extractJSON() {
        try {
            user = new JSONArray(context.getlistEvent());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showData(int i) {
        try {
            JSONObject jsonObject = user.getJSONObject(i);
            if (i%2 ==0){
                tv.setBackgroundColor(Color.parseColor("#F1F8E9"));
            }else{
                tv.setBackgroundColor(Color.parseColor("#000000"));
            }
            list.add((jsonObject.getString(NOM)) + "  " + (jsonObject.getString(USER)));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    public void showResults(String s) {
        if(s.equals("-1"))
            Toast.makeText(this, "Erreur", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
            context.setListEvent(s);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bouton_accueil:
                ConnEvent connEvent = new ConnEvent(this);
                connEvent.execute(URL);

                Intent filActuFilActu = new Intent(this, FilActu.class);
                startActivity(filActuFilActu);

                break;
            case R.id.bouton_event:
                Intent filActuGestionEvenement = new Intent(this, GestionEvenement.class);
                startActivity(filActuGestionEvenement);
                break;
            case R.id.bouton_profil:
                Intent filActuGestionDuProfil = new Intent(this, GestionDuProfil.class);
                startActivity(filActuGestionDuProfil);
                break;
        }
    }
}