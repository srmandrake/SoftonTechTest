package devbyrod.com.techtest.activities;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.util.ArrayList;
import java.util.List;

import devbyrod.com.techtest.BuildConfig;
import devbyrod.com.techtest.Constants;
import devbyrod.com.techtest.R;
import devbyrod.com.techtest.adapters.CitiesAdapter;
import devbyrod.com.techtest.fragments.CityDetailFragment;
import devbyrod.com.techtest.models.City;

public class MainActivity extends AppCompatActivity implements LocationListener,
                                                                NavigationView.OnNavigationItemSelectedListener,
                                                                CitiesAdapter.CitiesAdapterListener{

    private boolean mTwoPane = false;
    private Location mDeviceLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();

        ImageView logo = (ImageView) navigationView.getHeaderView( 0 ).findViewById( R.id.imageView );
        Ion.with( logo ).load( Constants.SOFTON_LOGO );

        setupRecyclerView();

        //if city_detail_container is not null, we got a Tablet layout
        if (findViewById(R.id.city_detail_container) != null) {

            mTwoPane = true;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

            Toast.makeText( this, "Settings button has been clicked :)", Toast.LENGTH_LONG ).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch( item.getItemId() ){

            case R.id.nav_cities:

                setupRecyclerView();
                break;

            case R.id.nav_about:

                createAboutDialog();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCityItemClick(City city) {

        if( mTwoPane ){

            Bundle arguments = new Bundle();
            arguments.putSerializable( CityDetailFragment.ARG_ITEM_CITY, city );
            CityDetailFragment fragment = new CityDetailFragment();
            fragment.setArguments( arguments );
            getSupportFragmentManager().beginTransaction()
                    .replace( R.id.city_detail_container, fragment )
                    .commit();
            return;
        }

        Intent detailActivity = new Intent( MainActivity.this, CityDetailActivity.class );
        detailActivity.putExtra( CityDetailFragment.ARG_ITEM_CITY, city );

        startActivity( detailActivity );
    }

    @Override
    public void onLocationChanged(Location location) {

        mDeviceLocation = location;
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

    private void setupRecyclerView() {

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.city_list);
        assert recyclerView != null;

        findViewById( R.id.progressBar ).setVisibility( View.VISIBLE );
        recyclerView.setVisibility( View.GONE );

        if( mDeviceLocation == null ) {

            mDeviceLocation = new Location("");
            mDeviceLocation.setLatitude(Constants.COSTA_RICA_LAT);
            mDeviceLocation.setLongitude(Constants.COSTA_RICA_LON);
        }

        String weatherUrl = Constants.API_URL + "&APPID=" + Constants.API_KEY;
        weatherUrl = String.format( weatherUrl, mDeviceLocation.getLatitude(), mDeviceLocation.getLongitude() );

        Ion.with( this )
                .load( weatherUrl )
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        if( e == null && !result.isJsonNull() ) {

                            if (result.has("list")) {

                                findViewById( R.id.progressBar ).setVisibility( View.GONE );
                                recyclerView.setVisibility( View.VISIBLE );

                                JsonArray cities = result.getAsJsonArray("list");

                                ArrayList<City> arrayList = new Gson().fromJson( cities.toString(), new TypeToken<List<City>>(){}.getType());

                                recyclerView.setAdapter( new CitiesAdapter( arrayList, MainActivity.this ) );
                            }
                        }
                    }
                });
    }

    private void createAboutDialog(){

        View view = getLayoutInflater().inflate( R.layout.dialog_about, null, false );

        ( (TextView) view.findViewById( R.id.txtVersionName ) )
                .setText( getString( R.string.app_name ) + "\n" +
                        getString( R.string.about_package_name ) +
                        " " + BuildConfig.APPLICATION_ID + "\n" +
                        getString( R.string.about_app_version ) +
                        BuildConfig.VERSION_NAME );

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setIcon( R.drawable.ic_menu_about );
        builder.setTitle( getString( R.string.about_app_title ) );
        builder.setView( view );
        builder.create();
        builder.show();
    }
}
