package thv92.apb;

import android.content.Intent;
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

import java.util.List;

import thv92.apb.Model.Animal;


public class MainActivity extends ActionBarActivity {


    private Spinner genderSpinner;
    private Spinner kindSpinner;

    private Button currentLocationButton;
    private Button searchButton;

    private EditText locationText;


    final static String TAG_LISTENER = "Listeners";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connects buttons and spinners
        configUIElements();
        setClickListeners();

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

    }
}
