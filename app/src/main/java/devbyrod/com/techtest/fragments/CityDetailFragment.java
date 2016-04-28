package devbyrod.com.techtest.fragments;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import devbyrod.com.techtest.Constants;
import devbyrod.com.techtest.R;
import devbyrod.com.techtest.models.City;

/**
 * A fragment representing a single City detail screen.
 * This fragment is either contained in a {@link devbyrod.com.techtest.activities.MainActivity}
 * in two-pane mode (on tablets) or a {@link devbyrod.com.techtest.activities.MainActivity}
 * on handsets.
 */
public class CityDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_CITY = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private City mCity;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CityDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_CITY)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mCity = (City)getArguments().getSerializable( ARG_ITEM_CITY );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);

        View rootView = inflater.inflate(R.layout.city_detail, container, false);

        if (appBarLayout != null) {

            appBarLayout.setTitle(mCity.getName());
        }
        else{

            TextView textView = (TextView) rootView.findViewById( R.id.txtCityName );
            textView.setText( mCity.getName() );
            textView.setVisibility( View.VISIBLE );
        }

        // Show the dummy content as text in a TextView.
        if (mCity != null) {

            Ion.with ( ( (ImageView) rootView.findViewById( R.id.imgIcon ) ) ).load( String.format( Constants.ICONS_URL, mCity.getWeather().getIcon() ) );
            ( (TextView) rootView.findViewById(R.id.txtWeatherDescription) ).setText( mCity.getWeather().getDescription() );
            ( (TextView) rootView.findViewById(R.id.txtTemperature) ).setText( getString( R.string.title_temperature ) + " " + String.format( "%.2f\u00B0", mCity.getMain().getTemp() ) );
            ( (TextView) rootView.findViewById(R.id.txtHumidity) ).setText( getString( R.string.title_humidity ) + " " + String.format( "%.2f", mCity.getMain().getHumidity() ) );
            ( (TextView) rootView.findViewById(R.id.txtWind) ).setText( getString( R.string.title_wind ) + " " + String.format( "%.2f", mCity.getWind().getSpeed() ) );
        }

        return rootView;
    }
}
