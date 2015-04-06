package thv92.apb;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.socrata.android.client.Consumer;
import com.socrata.android.soql.Query;
import com.socrata.android.soql.clauses.OrderDirection;
import com.socrata.android.ui.list.SodaListActivity;

import thv92.apb.Model.Animal;
import thv92.apb.View.AnimalView;

import static com.socrata.android.soql.clauses.Expression.contains;
import static com.socrata.android.soql.clauses.Expression.eq;
import static com.socrata.android.soql.clauses.Expression.gt;
import static com.socrata.android.soql.clauses.Expression.order;


public class ListViewResultActivity extends SodaListActivity<AnimalView, Animal> {


    private Consumer consumer;
    private String gender;
    private String kind;
    private String location;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        consumer = new Consumer(getString(R.string.soda_endpoint), getString(R.string.soda_token));
        Intent extras = getIntent();

        if(extras != null){
            gender = extras.getStringExtra("gender");
            kind = extras.getStringExtra("kind");
            location = extras.getStringExtra("location");
        }else{
            gender = "";
            kind = "";
            location = "";
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public Consumer getConsumer() {
        return consumer;
    }

    @Override
    public Query getQuery() {
        Query query = new Query("animals", Animal.class);
//        query.addWhere(eq("sex", "Intact Male"));
        Log.d("ListViewResultActivity", "testing");
        Log.d("ListViewResultActivity", ""+ isNetworkAvailable());
        return query;
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    }
}
