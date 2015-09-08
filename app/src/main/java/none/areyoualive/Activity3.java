package none.areyoualive;

import none.areyoualive.MainActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.lang.String;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
public class Activity3 extends ActionBarActivity {
    ListView listView;
    SoldierServices ss;
    String namewithyou;
    String[] values = {""};
    private ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity3);
        listView = (ListView) findViewById(R.id.list);
        ss = ServiceGenerator.createService(SoldierServices.class, this);
        ss.getSoldiers(new Callback<SoldierResponse>() {
            @Override
            public void success(SoldierResponse soldierResponse, Response response) {
                for(int i=0; i<MainActivity.soldierSize; i++) {
                    if (MainActivity.userstring.equals(MainActivity.names[i])) MainActivity.names[i] = MainActivity.names[i] + " (you)";
                    values = new String[]{MainActivity.names[0],MainActivity.names[1],MainActivity.names[2], MainActivity.names[3], MainActivity.names[4]};
                    MainActivity.statuses[i] = soldierResponse.soldiers.get(i).Status;
                }
                dealwithit();
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("error = " + error);
            }

        });
    }
    public void dealwithit() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String itemStatus = "";
                String itemMessage = "";
                // ListView Clicked item index
                int itemPosition = position + 1;
                for(int i=0; i<MainActivity.soldierSize; i++) {
                    if(itemPosition == i+1) {
                        itemStatus = MainActivity.statuses[i];
                        itemMessage = MainActivity.messages[i];
                    }

                }
                System.out.println(itemStatus);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Id :" + itemPosition + "," + "  Status : " + itemStatus + "," + "  Message: " + itemMessage, Toast.LENGTH_LONG)
                        .show();
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity3, menu);
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

}