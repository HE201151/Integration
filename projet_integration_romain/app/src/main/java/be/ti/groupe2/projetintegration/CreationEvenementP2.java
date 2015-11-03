package be.ti.groupe2.projetintegration;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class CreationEvenementP2 extends FragmentActivity implements View.OnClickListener{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    Button btn_recherche = null;
    Button btn_etapeSuivante = null;
    Button btn_changeType = null;
    EditText address = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_evenement_p2);
        setUpMapIfNeeded();

        address = (EditText)findViewById(R.id.adresse);
        btn_recherche = (Button)findViewById(R.id.btn_recherche);
        // btn_etapeSuivante = (Button)findViewById(R.id.btn_etapeSuivante);
        btn_changeType = (Button)findViewById(R.id.btn_changeType);

        btn_recherche.setOnClickListener(this);
        btn_changeType.setOnClickListener(this);
       // btn_zoomInMap.setOnClickListener(this);
        //btn_zoomOutMap.setOnClickListener(this);
        // btn_etapeSuivante.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
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
            // case R.id.btn_etapeSuivante:
            //    etapeSuivante();
            //    break;
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
        LatLng posBelgique = new LatLng (50.636600, 4.858398);
        centreCameraPosition(posBelgique);
       // mMap.addMarker(new MarkerOptions().position(new LatLng(50.636600, 4.858398)).title("Marker")); // Met un marker au centre de la Belgique
        mMap.setMyLocationEnabled(true); // Permet de cibler la carte sur notre position
        mMap.getUiSettings().setZoomControlsEnabled(true); //Ajoute bouton Zoom en bas à droite de la carte

    }

    /** Méthode qui permet de trouver un lieu en fonction de ce que l'utilisateur écrit dans le champ adresse. **/
    private void rechercheLocalite(){
        String location = address.getText().toString();
        List<Address> addressList =null;
        if (location != null || location.equals("")){
            Geocoder geocoder =new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            }catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            //Toast.makeText(this, "Lat : "+address.getLatitude(), Toast.LENGTH_SHORT).show();
            LatLng lating = new LatLng (address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions()
                            .position(lating)
                            .title("Marker")
                            .snippet("HQ")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

            mMap.animateCamera(CameraUpdateFactory.newLatLng(lating));

        }
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
    private void etapeSuivante(){
        //Code à venir
    }

    /** Cette méthode permet à l'utilisateur de sélectionner une étape suivante. **/
    private void centreCameraPosition(LatLng currentLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());// Zoom in
        mMap.animateCamera(CameraUpdateFactory.zoomTo(7), 2000, null); //Dézoom de lvl 7 pendant 2 sec


    }

}
