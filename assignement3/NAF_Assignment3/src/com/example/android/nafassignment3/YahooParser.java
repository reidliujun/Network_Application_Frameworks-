package com.example.android.nafassignment3;

import com.example.android.effectivenavigation.R;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Asynctask for Yahoo Weather API
 */
public class YahooParser extends Fragment {
	
	YahooClient weatherResult;
	private TextView textweather;
	private ImageView imgImagen;
	private Bitmap imagen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	    final View rootView = inflater.inflate(R.layout.yahoo_parser, container, false);
    	    textweather=(TextView) rootView.findViewById(R.id.txt_view);
    	    imgImagen=(ImageView) rootView.findViewById(R.id.imview_weather);
	        rootView.findViewById(R.id.get_weatherbt)    
	        .setOnClickListener(new View.OnClickListener() {
	        	public void onClick(View view){    
	        	YahooTask weat = new YahooTask();
	        	EditText editText = (EditText) rootView.findViewById(R.id.txtlocation);
	        	String location = editText.getText().toString();
	        	if(location.matches(""))
	        		Toast.makeText(getActivity(), "Please input the location!", Toast.LENGTH_SHORT).show();
	        	else
	        		weat.execute(location);
	        	}
	        });
	        
	        return rootView;
    }
    
    private class YahooTask  extends AsyncTask<String, Void, Void>{
		 ProgressDialog pDialog;
		 	 
		    @Override
		    protected void onPreExecute() {
		        // TODO Auto-generated method stub
		        super.onPreExecute();
		        pDialog = new ProgressDialog(getActivity());
		        pDialog.setMessage("Loading Data");
		        pDialog.setCancelable(true);
		        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		        pDialog.show();    
		    }
		    
		    @Override
		    protected Void doInBackground(String... params) {
		        // TODO Auto-generated method stub
		        String location = params[0];
		        YahooClient yahoo= new YahooClient();
		    	String weatherString = yahoo.getWeatherData(location);
		    	org.w3c.dom.Document weatherDoc = XMLFunctions.stringToDocument(weatherString);
		        weatherResult = yahoo.parseWeather(weatherDoc);
		        imagen = yahoo.downloadImage(weatherResult.imageweather);
				return null;
		    }
		    
		    @Override
			protected void onPostExecute(Void result) {
		    	textweather.setText(weatherResult.toString());
		    	imgImagen.setImageBitmap(imagen);
				super.onPostExecute(result);
				pDialog.dismiss();
			}

	}
}