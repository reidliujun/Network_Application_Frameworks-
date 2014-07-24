package com.example.android.nafassignment3;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.android.effectivenavigation.R;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * A fragment that launches other parts of the demo application.
 */

/***************************************************************************************************************** 
 * REFERENCE: http://eclipsesource.com/blogs/2012/07/31/loading-caching-and-displaying-images-in-android-part-1/ *
 *			  http://android-developers.blogspot.de/2010/07/multithreading-for-performance.html 				 *
 *****************************************************************************************************************/

public class ImgRetrieveUI extends Fragment {

	public static final String img_URL = "http://group01.naf.cs.hut.fi/img/fb_twitter_google.jpg";
    private ImageView imgImagen;
    private EditText urlImagen;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.img_retrieve, container, false);
        imgImagen=(ImageView) rootView.findViewById(R.id.imview);
        urlImagen = (EditText) rootView.findViewById(R.id.urlimage);
        
        
        rootView.findViewById(R.id.get_imagebt)
        .setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view){
        	 String location = urlImagen.getText().toString();
             if(location.matches(""))
             	Toast.makeText(getActivity(), "Please input the location!", Toast.LENGTH_SHORT).show();
            else{
            	LoadImage img = new LoadImage();
 	        	img.execute(urlImagen.getText().toString());}
            }	        
        });

        return rootView;
    }

	private class LoadImage  extends AsyncTask<String, Void, Bitmap>{
		 ProgressDialog pDialog;
		 	 
		    @Override
		    protected void onPreExecute() {
		        // TODO Auto-generated method stub
		        super.onPreExecute();
		        pDialog = new ProgressDialog(getActivity());
		        pDialog.setMessage("Loading Image");
		        pDialog.setCancelable(true);
		        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		        pDialog.show();    
		    }
		    @Override
		    protected Bitmap doInBackground(String... params) {
		        // TODO Auto-generated method stub
		        Log.i("doInBackground" , "Process in Background");
		        String url = params[0];
		        Bitmap imagen = downloadImage(url);
		        return imagen;
		    }
		    
		    private Bitmap downloadImage (String imageHttpAddress){
		        URL imageUrl = null;
		        Bitmap imagen = null;
		        try{
		            imageUrl = new URL(imageHttpAddress);
		            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
		            conn.connect();
		            imagen = BitmapFactory.decodeStream(conn.getInputStream());
		        }catch(IOException ex){
		            ex.printStackTrace();
		        }
		         
		        return imagen;
		    } 
		    @Override
		    protected void onPostExecute(Bitmap result) {
		        // TODO Auto-generated method stub
		        super.onPostExecute(result);
		         
		        imgImagen.setImageBitmap(result);
		        pDialog.dismiss();
		    }
	}
}