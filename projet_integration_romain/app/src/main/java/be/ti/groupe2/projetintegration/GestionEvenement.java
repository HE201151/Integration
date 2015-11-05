package be.ti.groupe2.projetintegration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GestionEvenement extends Activity implements View.OnClickListener {

    VariableGlobale context;

    Button accueil;
    Button event;
    Button profil;
    Button creaevent;

    String edit;

    private ListView tv;

    private static final String EVENTNAME = "userLogin";
    private static final String USERID = "userEmail";

    List<String> list;
    ArrayAdapter<String> adapter;

    JSONArray user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestion_evenement);

        accueil = (Button) findViewById(R.id.bouton_accueil);
        event = (Button) findViewById(R.id.bouton_event);
        profil = (Button) findViewById(R.id.bouton_profil);
        creaevent = (Button) findViewById(R.id.bouton_creationEvent);
        tv = (ListView) findViewById(R.id.tvk2);

        accueil.setOnClickListener(this);
        event.setOnClickListener(this);
        profil.setOnClickListener(this);
        creaevent.setOnClickListener(this);

        context = (VariableGlobale) this.getApplicationContext();

        user = extractJSON(user);
        list = new ArrayList<>();
        int length = user.length();
        System.out.println(length);
        length = length - 1;
        while (length >= 0) {
            showData(length);
            length--;
        }
        adapter = new ArrayAdapter<String>(this, R.layout.list, R.id.editT, list);

        tv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bouton_accueil:
                Intent gestionEventFilActu = new Intent(this, filActu.class);
                startActivity(gestionEventFilActu);
                break;
            case R.id.bouton_event:
                Intent gestionEventGestionEvenement = new Intent(this, GestionEvenement.class);
                startActivity(gestionEventGestionEvenement);
                break;
            case R.id.bouton_profil:
                Intent gestionEventGestionDuProfil = new Intent(this, GestionDuProfil.class);
                startActivity(gestionEventGestionDuProfil);
                break;
            case R.id.bouton_creationEvent:
                Intent gestionEventCreationEvent = new Intent(this, CreationEvenement.class);
                startActivity(gestionEventCreationEvent);
                break;
        }
    }

    private JSONArray extractJSON(JSONArray user2) {
        try {
            user2 = new JSONArray(context.getlistEvent());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user2;
    }

    private void showData(int i) {
        try {
            JSONObject jsonObject = user.getJSONObject(i);
            list.add((jsonObject.getString(EVENTNAME)) + "   ");
            if (jsonObject.getString(USERID).equals(context.getiDUser())) {
                list.add(edit);


            }
        }catch(JSONException e){
                e.printStackTrace();
        }
    }
}