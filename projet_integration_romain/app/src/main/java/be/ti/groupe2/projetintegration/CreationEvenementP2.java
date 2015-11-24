package be.ti.groupe2.projetintegration;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import be.ti.groupe2.projetintegration.Task.TaskCreationEvent;
import be.ti.groupe2.projetintegration.Task.TaskEnvoieCoordonnee;
import be.ti.groupe2.projetintegration.Task.TaskEnvoieLatLng;

public class CreationEvenementP2 extends FragmentActivity implements View.OnClickListener,GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, TaskCreationEvent.CustomCreationEvent,TaskEnvoieCoordonnee.CustomEnvoieCoordonnee,TaskEnvoieLatLng.CustomEnvoieLatLng{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.


    Button btn_valideEtape = null;
    Button btn_changeType = null;


    VariableGlobale context;

    EditText et_address = null;


    Event myEvent = new Event();

    int compteurMarker=1;


    String localiteEvent="";
    String nomEvent="";
    String descriptionEvent="";
    String mdpEvent="";

    int nbEtape=0;

    int i=0;

    String eventId;

    Marker marker = null;
    LatLng latlng ;
    ArrayList<LatLng> arrayAddressValide = new ArrayList<LatLng>();
    JSONObject object =null;
    // JSONArray arrayAddressValide = null;


    public static final String URL_CREATIONEVENT= "http://projet_groupe2.hebfree.org/coordGoogle.php";
    public static final String URL_CREATIONEVENT2= "http://projet_groupe2.hebfree.org/creationEvent.php";
    public static final String URL_CREATIONEVENT3= "http://projet_groupe2.hebfree.org/envoieLatLng.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Recuperation de l'intent venant de CreationEvent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            localiteEvent = extras.getString("localiteEvent");
            nbEtape = extras.getInt("nbEtape");
            nomEvent = extras.getString("nomEvent");
            descriptionEvent = extras.getString("descriptionEvent");
            mdpEvent = extras.getString("mdpEvent");
            Toast.makeText(CreationEvenementP2.this, "localite : "+localiteEvent, Toast.LENGTH_SHORT).show();
            Toast.makeText(CreationEvenementP2.this, "nbEtape : "+ nbEtape, Toast.LENGTH_SHORT).show();
            //extras.getSerializable()
        }


      /* Bundle extras = getIntent().getExtras();
        if (extras != null) {
            myEvent.setNom(extras.getString("nomEvent"));
            myEvent.setMdp(extras.getString("mdpEvent"));
            myEvent.setLocalite(extras.getString("localiteEvent"));
            myEvent.setNb(extras.getInt("nbEtape"));
            Toast.makeText(CreationEvenementP2.this, "localite : "+myEvent.getLocalite(), Toast.LENGTH_SHORT).show();
            Toast.makeText(CreationEvenementP2.this, "nbEtape : "+ myEvent.getNb(), Toast.LENGTH_SHORT).show();
            //extras.getSerializable()
        }*/

        setContentView(R.layout.activity_creation_evenement_p2);
        setUpMapIfNeeded();
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);

        et_address = (EditText)findViewById(R.id.et_adresse);

        btn_valideEtape = (Button)findViewById(R.id.btn_valideEtape);
        btn_valideEtape.setText("Valider étape 1/"+nbEtape);
        btn_changeType = (Button)findViewById(R.id.btn_changeType);


        btn_changeType.setOnClickListener(this);
        btn_valideEtape.setOnClickListener(this);

        et_address.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    rechercheLocalite();

                    //enlever le clavier
                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    handled = true;
                }
                return handled;
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        //setUpMapIfNeeded();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aide:
                Toast.makeText(CreationEvenementP2.this, "Menu aide", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_changeType:
                changeType();
                break;
            case R.id.btn_valideEtape:
                valideEtape();
                break;
        }
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        LatLng posLocalite;
        posLocalite = getLatitudeLongitude(localiteEvent);
        centreCameraPosition(posLocalite);
        mMap.setMyLocationEnabled(true); // Permet de cibler la carte sur notre position
        mMap.getUiSettings().setZoomControlsEnabled(true); //Ajoute bouton Zoom en bas à droite de la carte
    }

    /** Méthode qui permet de trouver un lieu en fonction de ce que l'utilisateur écrit dans le champ adresse. **/
    private void rechercheLocalite(){
        LatLng latlng;
        String location = et_address.getText().toString();
        latlng = getLatitudeLongitude(location);
        centreCameraPosition(latlng);
        if ((marker != null) && (marker.getTitle().equals("Marker"))){
            marker.remove();
        }
        marker = mMap.addMarker(new MarkerOptions()
                .position(latlng)
                .title("Marker")
                .snippet("HQ")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        et_address.setText(getLocalite(latlng.latitude, latlng.longitude));

        //marker[0].remove();  supprimer marker
    }

    /** Méthode qui permet de changer le type de vue de la map sur pression du bouton.
     * Passant de normal à satellite et satellite à normal. **/
    private void changeType(){
        if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL){
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }else{
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    /** Cette méthode permet à l'utilisateur de sélectionner une étape suivante. **/
    private void valideEtape() {
        if (marker != null) {
            if (compteurMarker <= nbEtape) {
                marker.setTitle("Marker" + compteurMarker);
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                arrayAddressValide.add(latlng);
                Toast.makeText(CreationEvenementP2.this, arrayAddressValide.get(0).toString(), Toast.LENGTH_SHORT).show();


                compteurMarker++;
                if (compteurMarker>nbEtape){
                    TaskCreationEvent creationEvent = new TaskCreationEvent(this);
                    Toast.makeText(CreationEvenementP2.this, "Encodage des Etapes finis! G G", Toast.LENGTH_SHORT).show();
                    //Changement d'activity ici

                   String idAuteur = "3";
                    Toast.makeText(CreationEvenementP2.this, "idAuteur : "+idAuteur, Toast.LENGTH_SHORT).show();
                    String SnbEtape = Integer.toString(nbEtape);
                    creationEvent.execute(URL_CREATIONEVENT,nomEvent,mdpEvent,localiteEvent,descriptionEvent,idAuteur,SnbEtape);
                    TaskEnvoieCoordonnee envoieCoordonnee = new TaskEnvoieCoordonnee(this);
                    envoieCoordonnee.execute(URL_CREATIONEVENT2,idAuteur,nomEvent);


                    for (i=0;i<nbEtape;i++){
                        TaskEnvoieLatLng envoieLatLng = new TaskEnvoieLatLng(this);
                        String lat = Double.toString(arrayAddressValide.get(i).latitude);
                        String lng = Double.toString(arrayAddressValide.get(i).longitude);
                        envoieLatLng.execute(URL_CREATIONEVENT3,lat,lng,eventId);
                        //envoieLatLng.cancel(true);
                    }


                    Toast.makeText(CreationEvenementP2.this, "en principe c'est rajouté en bdd", Toast.LENGTH_SHORT).show();
                }
                btn_valideEtape.setText("Valider étape "+compteurMarker+"/"+nbEtape);
            }
        }else {
            Toast.makeText(CreationEvenementP2.this, "Selectionnez un lieu", Toast.LENGTH_SHORT).show();
        }
    }

    /** Cette méthode permet à l'utilisateur de sélectionner une étape suivante. **/
    private void centreCameraPosition(LatLng currentLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());// Zoom in
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null); //Dézoom de lvl 7 pendant 2 sec
    }

    /** Méthode qui permet de trouver un lieu en fonction de ce que l'utilisateur écrit dans le champ adresse. **/
    private LatLng getLatitudeLongitude(String localite) {
        List<Address> addressList = null;

        if (localite != null || localite.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(localite, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            latlng = new LatLng(address.getLatitude(), address.getLongitude());
        }
        return latlng;
    }

    /** Permet de récupérer l'addresse en fonction de coordonnée**/
    private String getLocalite(double lat, double lng){
        String situation="";
        List<Address> addressList = null;
        Geocoder geocoder = new Geocoder(this);
        try {
            addressList = geocoder.getFromLocation(lat,lng,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address address = addressList.get(0);
        situation = address.getAddressLine(0);


        return situation;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(CreationEvenementP2.this, "Click", Toast.LENGTH_SHORT).show();

        centreCameraPosition(latLng);
        if ((marker != null) && (marker.getTitle().equals("Marker"))){
            marker.remove();
        }
        marker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Marker")
                .snippet("HQ")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        et_address.setText(getLocalite(latLng.latitude, latLng.longitude));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Toast.makeText(CreationEvenementP2.this, "LongClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBarCreationEvent() {

    }

    @Override
    public void hideProgressBarCreationEvent() {

    }

    @Override
    public void showResultCreationEvent(String s) {
        Toast.makeText(CreationEvenementP2.this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBarEnvoieCoordonnee() {

    }

    @Override
    public void hideProgressBarEnvoieCoordonnee() {

    }

    @Override
    public void showResultEnvoieCoordonnee(String s) {
        Toast.makeText(CreationEvenementP2.this, s, Toast.LENGTH_SHORT).show();
        eventId = s;

    }

    @Override
    public void showProgressBarEnvoieLatLng() {

    }

    @Override
    public void hideProgressBarEnvoieLatLng() {

    }

    @Override
    public void showResultEnvoieLatLng(String s) {
        Toast.makeText(CreationEvenementP2.this, s, Toast.LENGTH_SHORT).show();
       // i++;
    }
}