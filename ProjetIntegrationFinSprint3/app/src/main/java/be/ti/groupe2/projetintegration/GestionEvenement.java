package be.ti.groupe2.projetintegration;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.ti.groupe2.projetintegration.Task.ConnEventParticip;

public class GestionEvenement extends Activity implements View.OnClickListener ,ConnEventParticip.CustomInterface{

    VariableGlobale context;

    Button accueil;
    Button event;
    Button profil;
    Button creaevent;

    String edit;

    private ListView tv;

    public static final String USER = "IdUser";
    public static final String NOM = "IdEvent";
    public static final String E = "eventID";
    public static final String USERE = "userID";
    public static final String NOME = "eventName";
    public static final String LOC = "localite";
    public static final String DES = "description";
    public static final String NB = "nbEtape";

    public static final String URL = "http://projet_groupe2.hebfree.org/Particip.php";

    List<String> list;
    List<Event> listE;
    List<String> affiche;
    ArrayAdapter<String> adapter;

    JSONArray eventList;

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

        ConnEventParticip connEventParticip = new ConnEventParticip(this);
        int id = context.getiDUser();
        System.out.println("------" + context.getlistEvent());

        connEventParticip.execute(URL, Integer.toString(id));


    }

    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener(){
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String g = (String) parent.getItemAtPosition(position);
            String s = list.get(position);
            Event e = new Event();
            e.setNom(s);
            context.setEvent(e);
            Intent visuEvenement = new Intent(GestionEvenement.this, VisuEvent.class);
            startActivity(visuEvenement);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bouton_accueil:
                Intent gestionEventFilActu = new Intent(this, FilActu.class);
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

    private JSONArray extractJSON(JSONArray event, String s) {
        try {
            event = new JSONArray(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return event;
    }

    private void showData(int i, JSONArray j) {
        try {
            JSONObject jsonObject = j.getJSONObject(i);
            if (i%2 ==0){
                tv.setBackgroundColor(Color.parseColor("#F1F8E9"));
            }else{
                tv.setBackgroundColor(Color.parseColor("#000000"));
            }
            list.add((jsonObject.getString(NOM)));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showData2(int i,JSONArray j) {
        int k = j.length();
        k--;
        Event ev = new Event();
        while(k>=0) {
            try {
                JSONObject jsonObject = j.getJSONObject(k);
                if (list.get(i).equals(jsonObject.getString(E))) {
                    ev.setNom(jsonObject.getString(NOME));
                    Toast.makeText(this, jsonObject.getString(NOME), Toast.LENGTH_SHORT).show();
                    ev.setDescription(jsonObject.getString(DES));
                    ev.setLocalite(jsonObject.getString(LOC));
                    ev.setNb(jsonObject.getInt(NB));
                    affiche.add((ev.getNom()));
                    listE.add(ev);
                }
                else{

                }

            } catch (JSONException e) {
                e.printStackTrace();

            }
            k--;
        }
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    public void showResultat(String s) {
        if(s.equals("-1"))
            Toast.makeText(this, "Erreur",
                    Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "OK",
                    Toast.LENGTH_SHORT).show();
            context.setListEventParticip(s);
            eventList = extractJSON(eventList,
                    context.getlistEventParticip());
            list = new ArrayList<>();
            int length = eventList.length();
            length = length - 1;
            while (length >= 0) {
                showData(length,eventList);
                length--;
            }

            System.out.println ("-----kkkkk-----"+listE);
            adapter = new ArrayAdapter<String>(this,
                    R.layout.list, R.id.editT, list);
            tv.setAdapter(adapter);
            tv.setOnItemClickListener(onListClick);
        }
    }
}
