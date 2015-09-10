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
                for (int i = 0; i < soldierResponse.soldiers.size(); i++) {
                    MainActivity.longitudes[i] = soldierResponse.soldiers.get(i).Longitude;
                    MainActivity.latitudes[i] = soldierResponse.soldiers.get(i).Latitude;
                }
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("error = " + error);
            }

        });
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.setOnMapLoadedCallback(this);
            mMap.setOnMarkerClickListener(this);
        }
    }

    @Override
    public void onMapLoaded() {
        for(int i=0; i<MainActivity.soldierSize; i++) {
            LatLng location = new LatLng(MainActivity.latitudes[i], MainActivity.longitudes[i]);
            mMap.addMarker(new MarkerOptions().position(location).title(MainActivity.names[i]));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            CameraUpdate Location = CameraUpdateFactory.newLatLngZoom(location, 10);
            mMap.animateCamera(Location);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}