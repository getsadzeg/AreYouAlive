package none.areyoualive;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.gc.materialdesign.views.ButtonFlat;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.widget.EditText;
import com.gc.materialdesign.widgets.Dialog;
import com.baoyz.widget.PullRefreshLayout;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.view.MenuInflater;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Activity2 extends ActionBarActivity {
    RadioButton radio1;
    SoldierServices ss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                    if (MainActivity.userstring.equals(MainActivity.name))
                        updateInfo(1, MainActivity.name, "Disabled", MainActivity.message, MainActivity.longitude, MainActivity.latitude);
                    if(MainActivity.userstring.equals(MainActivity.name1))
                        updateInfo(2, MainActivity.name1, "Disabled", MainActivity.message1, MainActivity.longitude1, MainActivity.latitude1);
                }
                break;
            case R.id.radioButton2:
                if(checked) {
                    if (MainActivity.userstring.equals(MainActivity.name))
                        updateInfo(1, MainActivity.name, "Bad", MainActivity.message, MainActivity.longitude, MainActivity.latitude);
                    if(MainActivity.userstring.equals(MainActivity.name1))
                        updateInfo(2, MainActivity.name1, "Bad", MainActivity.message1, MainActivity.longitude1, MainActivity.latitude1);
                }
                break;
            case R.id.radioButton3:
                if(checked) {
                    if (MainActivity.userstring.equals(MainActivity.name))
                        updateInfo(1, MainActivity.name, "Good", MainActivity.message, MainActivity.longitude, MainActivity.latitude);
                    if(MainActivity.userstring.equals(MainActivity.name1))
                        updateInfo(2, MainActivity.name1, "Good", MainActivity.message1, MainActivity.longitude1, MainActivity.latitude1);
                }
                break;
        }
    }
    public void bulletsButtonOnClick(View a) {
        if (MainActivity.userstring.equals(MainActivity.name))
            updateInfo(1, MainActivity.name, MainActivity.status, "Need Bullets", MainActivity.longitude, MainActivity.latitude);
        if(MainActivity.userstring.equals(MainActivity.name1))
            updateInfo(2, MainActivity.name1, MainActivity.status1, "Need Bullets", MainActivity.longitude1, MainActivity.latitude1);
    }
    public void medicalhelpButtonOnClick(View b) {
        if (MainActivity.userstring.equals(MainActivity.name))
            updateInfo(1, MainActivity.name, MainActivity.status, "Need Medical Help", MainActivity.longitude, MainActivity.latitude);
        if(MainActivity.userstring.equals(MainActivity.name1))
            updateInfo(2, MainActivity.name1, MainActivity.status1, "Need Medical Help", MainActivity.longitude1, MainActivity.latitude1);
    }
    public void humanresourcesButtonOnClick(View c) {
        if (MainActivity.userstring.equals(MainActivity.name))
            updateInfo(1, MainActivity.name, MainActivity.status, "Need Human Resources", MainActivity.longitude, MainActivity.latitude);
        if(MainActivity.userstring.equals(MainActivity.name1))
            updateInfo(2, MainActivity.name1, MainActivity.status1, "Need Human Resources", MainActivity.longitude1, MainActivity.latitude1);
    }
    public void productButtonOnClick(View d) {
        if (MainActivity.userstring.equals(MainActivity.name))
            updateInfo(1, MainActivity.name, MainActivity.status, "Need Food & Water", MainActivity.longitude, MainActivity.latitude);
        if(MainActivity.userstring.equals(MainActivity.name1))
            updateInfo(2, MainActivity.name1, MainActivity.status1, "Need Food & Water", MainActivity.longitude1, MainActivity.latitude1);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_activity2, menu);
        //return true;
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
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    //int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    // if (id == R.id.action_settings) {
    //return true;
    // }

    //return super.onOptionsItemSelected(item);
}
