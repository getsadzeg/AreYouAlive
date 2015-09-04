package none.areyoualive;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gc.materialdesign.widgets.Dialog;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ActionBarActivity {
    EditText password;
    EditText username;

    GPSTracker gpsTracker;

    SoldierServices ss;
    public static String[] names = new String[12];
    public static String name = "";
    public static String name1 = "";
    public static String status = "";
    public static String status1 = "";
    public static String message = "";
    public static String message1 = "";
    public static double longitude;
    public static double longitude1;
    public static double latitude;
    public static double latitude1;
    public static String userstring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gpsTracker = new GPSTracker(this);
        gpsTracker.getLocation();
        if (gpsTracker.canGetLocation()) {
            System.out.println(gpsTracker.getLongitude());
            System.out.println(gpsTracker.getLatitude());
        } else {
            Toast.makeText(getApplicationContext(), "Please check your location settings.", Toast.LENGTH_LONG).show();
            finish();
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings

        }
        if (!haveNetworkConnection()) {
            Toast.makeText(getApplicationContext(), "Please check your internet connection. Cannot retrieve from database.", Toast.LENGTH_LONG).show();
            finish();
        }
        username = (EditText) findViewById(R.id.editText1);
        password = (EditText) findViewById(R.id.editText2);
        ss = ServiceGenerator.createService(SoldierServices.class, this);
        ss.getSoldiers(new Callback<SoldierResponse>() {
            @Override
            public void success(SoldierResponse soldierResponse, Response response) {
                System.out.println("soldierResponse.soldiers.size() = " + soldierResponse.soldiers.size());
                for (int i = 0; i < soldierResponse.soldiers.size(); i++) {
                    names[0] = soldierResponse.soldiers.get(0).Name;
                    status = soldierResponse.soldiers.get(0).Status;
                    message = soldierResponse.soldiers.get(0).Message;
                    longitude = gpsTracker.getLongitude();
                    latitude = gpsTracker.getLatitude();
                    names[1] = soldierResponse.soldiers.get(1).Name;
                    status1 = soldierResponse.soldiers.get(1).Status;
                    message1 = soldierResponse.soldiers.get(1).Message;
                    longitude1 = gpsTracker.getLongitude();
                    latitude1 = gpsTracker.getLatitude();
                    //System.out.println("soldierResponse output is " + soldierResponse.soldiers.get(i).Name);
                }

            }


            @Override
            public void failure(RetrofitError error) {
                System.out.println("error = " + error);
            }

        });
        // updateInfo(1, "guri", "alive", "test", "5", "5");
    }

    private void updateInfo(int id, String name, String status, String message, long longitude, long latitude) {
        ss.updateInfo(id, name, status, message, longitude, latitude, new Callback<UpdateInfoResponse>() {
            @Override
            public void success(UpdateInfoResponse updateInfoResponse, Response response) {
                System.out.println("UpdateInfoResponse.success = " + updateInfoResponse.success);
                if (updateInfoResponse.success) {
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //@Override
    public void buttonflatOnClick(View v) {
        username = (EditText) findViewById(R.id.editText1);
        password = (EditText) findViewById(R.id.editText2);
        //password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        userstring = username.getText().toString();
        if (password.getText().toString().equals("lala") && username.getText().toString().equals(names[0]) || username.getText().toString().equals(names[1])) {
            startActivity(new Intent(MainActivity.this, Activity2.class));
            finish();
        } else {
            Dialog dialog = new Dialog(this, "SOS", "You're trying to access our servers. Go to hell. Regards.");
            dialog.show();
        }
    }

    public String getUserstring() {
        return userstring;
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}