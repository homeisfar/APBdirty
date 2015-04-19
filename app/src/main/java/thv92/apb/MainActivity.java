package thv92.apb;

import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.socrata.android.client.Callback;
import com.socrata.android.client.Consumer;
import com.socrata.android.client.Response;
import com.socrata.android.soql.Query;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import thv92.apb.Model.Animal;


public class MainActivity extends ActionBarActivity {


    private Spinner genderSpinner;
    private Spinner kindSpinner;

    private Button currentLocationButton;
    private Button searchButton;

    private EditText locationText;
    private Geocoder geocoder;
    private double latitude;
    private double longitude;
    private static final String BULLETIN_TAG = "AustinPB";

    final static String TAG_LISTENER = "Listeners";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connects buttons and spinners
        configUIElements();
        setClickListeners();
        geocoder = new Geocoder(this, Locale.ENGLISH);
        latitude = 30.2500;
        longitude = -97.7500;

        Consumer consumer = new Consumer("https://data.austintexas.gov/resource/kz4x-q9k5.json", "sb3OofqQ9zQqC9N4iKRAFFT2L");
        Query query = new Query("kz4x-q9k5.json", Animal.class);
        consumer.getObjects(query, new Callback<List<Animal>>() {
            @Override
            public void onResults(Response<List<Animal>> response) {
                List<Animal> earthquakes = response.getEntity();
                //do somethings with earthquakes
                Log.d("count", " " + earthquakes.size());
                for(Animal item : earthquakes){
                    Log.d("sadfasdfads222", item.getLooksLike());
                }
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

    //Config Method
    private void configUIElements(){
        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        kindSpinner = (Spinner) findViewById(R.id.kindSpinner);

        currentLocationButton = (Button) findViewById(R.id.currentLocation);
        searchButton = (Button) findViewById(R.id.search);

        locationText = (EditText) findViewById(R.id.locationText);

        //Arrays for populating spinners
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this, R.array.genderArr,
                android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        ArrayAdapter<CharSequence> breedAdapter = ArrayAdapter.createFromResource(this, R.array.kindArr,
                android.R.layout.simple_spinner_item);
        breedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kindSpinner.setAdapter(breedAdapter);
    }

    private void setClickListeners(){

//        genderSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationText.getText().toString().matches("")) {
                    Toast.makeText(MainActivity.this, "You did not enter a Location", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, ListViewResultActivity.class);
                intent.putExtra("gender", genderSpinner.getSelectedItem().toString());
                intent.putExtra("kind", kindSpinner.getSelectedItem().toString());
                intent.putExtra("location", locationText.getText().toString());
                startActivity(intent);
            }
        });

        currentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View view) {
                Log.d(BULLETIN_TAG, "Clicked on current location Button");


                class MyLocationListener implements LocationListener {
                    @Override
                    public void onLocationChanged(Location location) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();

                        Log.d(BULLETIN_TAG, "Long: " + longitude);
                        Log.d(BULLETIN_TAG, "Lat: " + latitude);
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                }

                if(longitude != 0 && latitude != 0) {

                    LocationManager mgr;
                    mgr = (LocationManager) getSystemService(LOCATION_SERVICE);
                    Criteria criteria = new Criteria();

                    String best = mgr.getBestProvider(criteria, true);
                    Log.d(BULLETIN_TAG, "Best provider: " + best);

                    Location loc = mgr.getLastKnownLocation(best);

                    LocationListener mLocListener = new MyLocationListener();

                    if (loc == null) {
                        mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);
                        loc = mgr.getLastKnownLocation(best);
                    }

                    if (loc != null) {
                        longitude = loc.getLongitude();
                        latitude = loc.getLatitude();
                    }

                }//if guard on whether or not last known location exists.
                //TODO: Fix the damn geolocation long/latitude

                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Address address = null;
                String addr="";
                String zipcode="";
                String city="";
                String state="";
                if (addresses != null && addresses.size() > 0){
                    addr=addresses.get(0).getAddressLine(0)+"," +addresses.get(0).getSubAdminArea();
                    city=addresses.get(0).getLocality();
                    state = addresses.get(0).getAdminArea();

                    for (int i = 0; i < addresses.size(); i++){
                        address = addresses.get(i);
                        if(address.getPostalCode() != null){
                            zipcode=address.getPostalCode();
                            Log.d(BULLETIN_TAG, "Found zipcode: " + zipcode);
                            Log.d(BULLETIN_TAG, "Latitude: " + latitude);
                            Log.d(BULLETIN_TAG, "Longitude: : " + longitude);

                            break;
                        }
                    }
                }

                locationText.setText(zipcode);
            }
        });

    }
}
