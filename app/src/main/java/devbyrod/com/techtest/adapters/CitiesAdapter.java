package devbyrod.com.techtest.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.List;

import devbyrod.com.techtest.Constants;
import devbyrod.com.techtest.R;
import devbyrod.com.techtest.models.City;

/**
 * Created by Mandrake on 4/26/16.
 */
public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder>
{
    public interface CitiesAdapterListener{

        void onCityItemClick( City city );
    }

    private List< City > mCitiesList;
    private CitiesAdapterListener mListener;

    public CitiesAdapter(List< City > citiesList, CitiesAdapterListener listener ){

        mCitiesList = citiesList;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.city_list_content, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final City city = mCitiesList.get( position );

        holder.mCity = city;
        Ion.with( holder.mImgIcon ).load( String.format( Constants.ICONS_URL, city.getWeather().getIcon() ) );
        holder.mTxtCityName.setText( city.getName() );
        holder.mTxtCityTemperature.setText( String.format( "%.2f\u00B0", city.getMain().getTemp() ) );

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( mListener != null ){

                    mListener.onCityItemClick( city );
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return mCitiesList == null ? 0 : mCitiesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        public final ImageView mImgIcon;
        public final TextView mTxtCityName;
        public final TextView mTxtCityTemperature;
        public City mCity;

        public ViewHolder( View view ) {

            super( view );

            mView = view;
            mImgIcon = (ImageView) view.findViewById( R.id.imgIcon );
            mTxtCityName = (TextView) view.findViewById( R.id.txtCityName );
            mTxtCityTemperature = (TextView) view.findViewById( R.id.txtCityTemperature );
        }
    }
}
