package none.areyoualive;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMapLoadedCallback, GoogleMap.OnMarkerClickListener {
    SoldierServices ss;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ss = ServiceGenerator.createService(SoldierServices.class, this);
        ss.getSoldiers(new Callback<SoldierResponse>() {
            @Override
            public void success(SoldierResponse soldierResponse, Response response) {
                System.out.println("soldierResponse.soldiers.size() = " + soldierResponse.soldiers.size());
                MainActivity.longitudes[0] = soldierResponse.soldiers.get(0).Longitude;
                MainActivity.latitudes[0] = soldierResponse.soldiers.get(0).Latitude;
                MainActivity.longitudes[1] = soldierResponse.soldiers.get(1).Longitude;
                MainActivity.latitudes[1] = soldierResponse.soldiers.get(1).Latitude;
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("error = " + error);
            }

        });
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.setOnMapLoadedCallback(this);
            mMap.setOnMarkerClickListener(this);
        }
    }

    @Override
    public void onMapLoaded() {
        LatLng location = new LatLng(MainActivity.latitudes[0], MainActivity.longitudes[0]);
        LatLng location1 = new LatLng(MainActivity.latitudes[1], MainActivity.longitudes[1]);
        mMap.addMarker(new MarkerOptions().position(location).title(MainActivity.names[0]));
        mMap.addMarker(new MarkerOptions().position(location1).title(MainActivity.names[1]));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        CameraUpdate Location = CameraUpdateFactory.newLatLngZoom(location, 10);
        mMap.animateCamera(Location);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}