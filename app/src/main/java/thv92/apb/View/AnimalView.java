package thv92.apb.View;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.socrata.android.ui.list.BindableView;
import com.socrata.android.ui.list.SodaHolder;

import thv92.apb.Model.Animal;
import thv92.apb.R;

/**
 * Created by Nafeal on 4/6/2015.
 */


@SodaHolder(layout = "animal_view")
public class AnimalView implements BindableView<Animal> {


    private TextView breed;

    private TextView location;

//    private TextView depth;

    @Override
    public void createViewHolder(View convertView) {
        breed = (TextView) convertView.findViewById(R.id.animal_breed);
        location = (TextView) convertView.findViewById(R.id.animal_location);
    }

    @Override
    public void bindView(Animal item, int position, View convertView, ViewGroup parent) {
//        title.setText(item);
//        magnitude.setText(convertView.getContext().getString(R.string.magnitude, item.getMagnitude()));
//        depth.setText(convertView.getContext().getString(R.string.depth, item.getDepth()));
          breed.setText(item.getLooksLike());
//          location.setText(convertView.getContext().getString(R.string.locationText, item.getLocation()));
        Log.d("AnimalView", item.getLooksLike());

    }


}
