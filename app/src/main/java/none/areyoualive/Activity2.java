package none.areyoualive;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.RadioButton;
import android.view.MenuInflater;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Activity2 extends ActionBarActivity {
    RadioButton radio1;
    SoldierServices ss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println(MainActivity.names[0]);
        System.out.println(MainActivity.names[1]);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        ss = ServiceGenerator.createService(SoldierServices.class, this);
        radio1 = (RadioButton) findViewById(R.id.radioButton);
    }
    public void radioButtonOnClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radioButton:
                if (checked) {
                    for(int i=0; i<MainActivity.soldierSize; i++) {
                        if (MainActivity.userstring.equals(MainActivity.names[i])) {
                            MainActivity.statuses[i] = "Disabled";
                            updateInfo(i+1, MainActivity.names[i], "Disabled", MainActivity.messages[i], MainActivity.longitudes[i], MainActivity.latitudes[i]);
                        }
                    }
                }
                break;
            case R.id.radioButton2:
                if(checked) {
                    for(int i=0; i<MainActivity.soldierSize; i++) {
                        if (MainActivity.userstring.equals(MainActivity.names[i])) {
                            MainActivity.statuses[i] = "Bad";
                            updateInfo(i+1, MainActivity.names[i], "Bad", MainActivity.messages[i], MainActivity.longitudes[i], MainActivity.latitudes[i]);
                        }
                    }
                }
                break;
            case R.id.radioButton3:
                if(checked) {
                    for(int i=0; i<MainActivity.soldierSize; i++) {
                        if (MainActivity.userstring.equals(MainActivity.names[i])) {
                            MainActivity.statuses[i] = "Good";
                            updateInfo(i+1, MainActivity.names[i], "Good", MainActivity.messages[i], MainActivity.longitudes[i], MainActivity.latitudes[i]);
                        }
                    }
                }
                break;
        }
    }
    public void bulletsButtonOnClick(View a) {
        for(int i=0; i<MainActivity.soldierSize; i++) {
            if (MainActivity.userstring.equals(MainActivity.names[i])) {
                MainActivity.messages[i] = "Need Bullets";
                updateInfo(i+1, MainActivity.names[i], MainActivity.statuses[i], "Need Bullets", MainActivity.longitudes[i], MainActivity.latitudes[i]);
            }
        }
    }
    public void medicalhelpButtonOnClick(View b) {
        for(int i=0; i<MainActivity.soldierSize; i++) {
            if (MainActivity.userstring.equals(MainActivity.names[i])) {
                MainActivity.messages[i] = "Need Medical Help";
                updateInfo(i+1, MainActivity.names[i], MainActivity.statuses[i], "Need Medical Help", MainActivity.longitudes[i], MainActivity.latitudes[i]);
            }
        }
    }
    public void humanresourcesButtonOnClick(View c) {
        for(int i=0; i<MainActivity.soldierSize; i++) {
            if (MainActivity.userstring.equals(MainActivity.names[i])) {
                MainActivity.messages[i] = "Need Food & Water";
                updateInfo(i+1, MainActivity.names[i], MainActivity.statuses[i], "Need Food & Water", MainActivity.longitudes[i], MainActivity.latitudes[i]);
            }
        }
    }
    public void productButtonOnClick(View d) {
        for(int i=0; i<MainActivity.soldierSize; i++) {
            if (MainActivity.userstring.equals(MainActivity.names[i])) {
                MainActivity.messages[i] = "Need Food & Water";
                updateInfo(i+1, MainActivity.names[i], MainActivity.statuses[i], "Need Food & Water", MainActivity.longitudes[i], MainActivity.latitudes[i]);
            }
        }
    }
    private void updateInfo(int id, String name, String status, String message, double longitude, double latitude) {
        ss.updateInfo(id, name, status, message, longitude, latitude, new Callback<UpdateInfoResponse>() {
            @Override
            public void success(UpdateInfoResponse updateInfoResponse, Response response) {
                System.out.println("UpdateInfoResponse.success = " + updateInfoResponse.success);
                if(updateInfoResponse.success){
                    System.out.println("SUCCESSFULLY UPDATED");
                } else {
                    System.out.println("UPDATE FAILED");
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_activity2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.connectedsoldiers:
                startActivity(new Intent(Activity2.this, Activity3.class));
                break;
            case R.id.info:
                startActivity(new Intent(Activity2.this, Activity4.class));
                break;
            case R.id.map:
                startActivity(new Intent(Activity2.this, MapsActivity.class));
        }

        return true;
    }
}