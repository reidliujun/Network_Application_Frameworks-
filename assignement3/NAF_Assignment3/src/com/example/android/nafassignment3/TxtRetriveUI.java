package com.example.android.nafassignment3;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.android.effectivenavigation.R;

import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/***********************************************************
 * READ TEXT FILE SERVER								   *
 ***********************************************************/

/*************************************************************************************************************************************** 
 * REFERENCE: http://stackoverflow.com/questions/2416872/how-do-you-obtain-modified-date-from-a-remote-file-java                       *
 * 			  http://www.mkyong.com/java/how-to-get-http-response-header-in-java/           										   *
 * 			  http://java2s.com/Tutorials/Java/URL_Connection_Address/How_to_read_HTTP_header_from_URL_using_URLConnection_in_Java.htm *
 ***************************************************************************************************************************************/

public class TxtRetriveUI extends Fragment {
	private Thread mThread;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.txt_retrieve, container, false);
        
        rootView.findViewById(R.id.txt_show_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    	mThread = new Thread() { 
                    		@Override
                            public void run(){
                            	String path="http://group01.naf.cs.hut.fi/text.txt";
                            	URL u = null;
                            	try {
                                    u = new URL(path);
                                    HttpURLConnection c = (HttpURLConnection) u.openConnection();
                                    c.setRequestMethod("GET");
                                    c.connect();
                                    InputStream in = c.getInputStream();                                  
                                    final String date_modif;
                                    date_modif=c.getHeaderField("Last-Modified");
                                    final ByteArrayOutputStream bo = new ByteArrayOutputStream();
                                    byte[] buffer = new byte[1024];
                                    in.read(buffer); // Read from Buffer.
                                    bo.write(buffer); // Write Into Buffer.
                                    
                                    //start the ui thread to update the ui.
                                    UIUtils.runOnUiThread(new Runnable() {
                    					@Override
                    					public void run() {		
                    						String last_modif = "Last-Modified: " + date_modif; 
                    						((TextView) rootView.findViewById(R.id.txt_view)).setText(bo.toString() + "\n" + last_modif);
                    					}
                    				});
                            	}
                                catch (NetworkOnMainThreadException e) {
                                	e.printStackTrace();
                               }
                                   catch (MalformedURLException e) {
                                   e.printStackTrace();
                                   } catch (FileNotFoundException e) {
                                   e.printStackTrace();
                                   } catch (IOException e) {
                                   e.printStackTrace();
                                   }
                            	super.run();
                                }
                        };
                        mThread.start();
                    }
                });   
        return rootView;
    }
}

