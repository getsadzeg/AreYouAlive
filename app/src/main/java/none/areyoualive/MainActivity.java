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
    public static int soldierSize;
    GPSTracker gpsTracker;

    SoldierServices ss;
    public static String[] names = new String[8];
    public static String[] statuses = new String[8];
    public static String[] messages = new String[8];
    public static double[] longitudes = new double[8];
    public static double[] latitudes = new double[8];
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
            Toast.makeText(getApplicationContext(), "Please check your internet connection. Cannot connect to database.", Toast.LENGTH_LONG).show();
            finish();
        }
        username = (EditText) findViewById(R.id.editText1);
        password = (EditText) findViewById(R.id.editText2);
        ss = ServiceGenerator.createService(SoldierServices.class, this);
        ss.getSoldiers(new Callback<SoldierResponse>() {
            @Override
            public void success(SoldierResponse soldierResponse, Response response) {
                soldierSize = soldierResponse.soldiers.size();
                for (int i = 0; i < soldierResponse.soldiers.size(); i++) {
                    names[i] = soldierResponse.soldiers.get(i).Name;
                    statuses[i] = soldierResponse.soldiers.get(i).Status;
                    messages[i] = soldierResponse.soldiers.get(i).Message;
                    longitudes[i] = gpsTracker.getLongitude();
                    latitudes[i] = gpsTracker.getLatitude();
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
        userstring = username.getText().toString();
        System.out.println(soldierSize);
        if (password.getText().toString().equals("lala") && username.getText().toString().equals(names[0]) || username.getText().toString().equals(names[3])) {
            startActivity(new Intent(MainActivity.this, Activity2.class));
            finish();
        }
        else if(password.getText().toString().equals("lala") && username.getText().toString().equals(names[2]) || username.getText().toString().equals(names[3])) {
            startActivity(new Intent(MainActivity.this, Activity2.class));
            finish();
        }
        else if(password.getText().toString().equals("lala") && username.getText().toString().equals(names[4]) || username.getText().toString().equals(names[5])) {
            startActivity(new Intent(MainActivity.this, Activity2.class));
            finish();
        }
        else if(password.getText().toString().equals("lala") && username.getText().toString().equals(names[6]) || username.getText().toString().equals(names[7])) {
            startActivity(new Intent(MainActivity.this, Activity2.class));
            finish();
        }
        else {
                Dialog dialog = new Dialog(this, "SOS", "You're trying to access our data. Go to hell. Regards.");
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