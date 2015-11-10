package be.ti.groupe2.projetintegration;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreationEvenementP2 extends FragmentActivity implements View.OnClickListener{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    Button btn_recherche = null;
    Button btn_valideEtape = null;
    Button btn_changeType = null;

    EditText et_address = null;
    TextView tv_lieuSelectionne =null;

    int compteurMarker=1;
    String localite="";
    int nbEtape=0;
    Marker marker = null;
    ArrayList<Marker> arrayMarkerValide= new ArrayList<Marker>();
    // ArrayList<Address> arrayAddressValide = new ArrayList<Address>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Recuperation de l'intent venant de CreationEvent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            localite = extras.getString("localite");
            nbEtape = extras.getInt("nbEtape");
            Toast.makeText(CreationEvenementP2.this, "localite : "+localite, Toast.LENGTH_SHORT).show();
            Toast.makeText(CreationEvenementP2.this, "nbEtape : "+ nbEtape, Toast.LENGTH_SHORT).show();
            //extras.getSerializable()
        }

        setContentView(R.layout.activity_creation_evenement_p2);
        setUpMapIfNeeded();

        et_address = (EditText)findViewById(R.id.et_adresse);
        tv_lieuSelectionne = (TextView) findViewById(R.id.tv_lieuSelectionne);

        btn_recherche = (Button)findViewById(R.id.btn_recherche);
        btn_valideEtape = (Button)findViewById(R.id.btn_valideEtape);
        btn_valideEtape.setText("Valider étape 1/"+nbEtape);
        btn_changeType = (Button)findViewById(R.id.btn_changeType);

        btn_recherche.setOnClickListener(this);
        btn_changeType.setOnClickListener(this);
        btn_valideEtape.setOnClickListener(this);

        //mMap.setOnMapClickListener();


    }
    /*@Override
    public void onMapClick(LatLng point) {
        Toast.makeText(CreationEvenementP2.this, "je clique", Toast.LENGTH_SHORT).show();
 
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
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
                Toast.makeText(CreationEvenementP2.this, "Menu aide putain", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_recherche:
                rechercheLocalite();
                break;
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
        posLocalite = getLatitudeLongitude(localite);
        centreCameraPosition(posLocalite);
        mMap.setMyLocationEnabled(true); // Permet de cibler la carte sur notre position
        mMap.getUiSettings().setZoomControlsEnabled(true); //Ajoute bouton Zoom en bas à droite de la carte
    }

    /** Méthode qui permet de trouver un lieu en fonction de ce que l'utilisateur écrit dans le champ adresse. **/
    private void rechercheLocalite(){
        LatLng latlng;
        String location = et_address.getText().toString();
        latlng = getLatitudeLongitude(location);
        // centreCameraPosition(latlng);
        if ((marker != null) && (marker.getTitle().equals("Marker"))){
            marker.remove();
        }
        marker = mMap.addMarker(new MarkerOptions()
                .position(latlng)
                .title("Marker")
                .snippet("HQ")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        getLocalite(latlng.latitude,latlng.longitude);
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
                arrayMarkerValide.add(marker);
                compteurMarker++;
                if (compteurMarker>nbEtape){
                    Toast.makeText(CreationEvenementP2.this, "Encodage des Etapes finis! G G", Toast.LENGTH_SHORT).show();
                    //Changement d'activity ici
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
        mMap.animateCamera(CameraUpdateFactory.zoomTo(7), 2000, null); //Dézoom de lvl 7 pendant 2 sec
    }

    /** Méthode qui permet de trouver un lieu en fonction de ce que l'utilisateur écrit dans le champ adresse. **/
    private LatLng getLatitudeLongitude(String localite) {
        List<Address> addressList = null;
        LatLng latlng = null;
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

        tv_lieuSelectionne.setText(situation);
        return situation;
    }
}