package com.example.android.nafassignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.android.effectivenavigation.R;
import com.facebook.SessionState;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/********************************************************************************************************** 
 * REFERENCE: http://www.learn2crack.com/2013/10/android-asynctask-json-parsing-example.html 			  *
 * 			  https://github.com/TechIsFun/android-facebook-login-example/blob/master/AndroidManifest.xml *
*             http://www.technowise.in/2013/05/validate-facebook-auth-token-in-php.html					  *
 *********************************************************************************************************/

/**
 * HSL Feature
 */
public class HslFeature extends Fragment {
	private EditText editStart;
	private EditText editDest;
	private Button   btn_check;
	private Button   btn_add;
	private TextView routeShow;
	
	private String start_time;
	private String end_time;
	private String leg_key;
	private String start_location;
	private String token;
	private static final String TAG = HslFeature.class.getSimpleName();
	private UiLifecycleHelper uiHelper;

	private final List<String> permissions;
	
	public HslFeature() {
		permissions = Arrays.asList("user_status");
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	
        final View rootView = inflater.inflate(R.layout.hsl, container, false);
        LoginButton authButton = (LoginButton) rootView.findViewById(R.id.authButton);
		authButton.setFragment(this);
		authButton.setReadPermissions(permissions);
	
		editStart= (EditText) rootView.findViewById(R.id.from_location);
    	editDest= (EditText) rootView.findViewById(R.id.to_location);
        routeShow=((TextView) rootView.findViewById(R.id.show_route));
        btn_check=((Button)rootView.findViewById(R.id.check_route));
    	btn_add=((Button)rootView.findViewById(R.id.btn_add_calenda));
    	
        
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	start_location = editStart.getText().toString();
            	String dest_location = editDest.getText().toString();
            	if (start_location.matches("") || dest_location.matches("")){
            		Toast.makeText(getActivity(), "Please input the location!", Toast.LENGTH_SHORT).show();
            	}
            	else{
                	RouteGenerate route = new RouteGenerate();
                	route.execute(start_location,dest_location);
            	}
            }
        });   
        

        
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	//add to calendar test
            	Calendar cal = Calendar.getInstance();
            	int year = cal.get(Calendar.YEAR);
            	int month = cal.get(Calendar.MONTH);
            	int date = cal.get(Calendar.DATE);
            	//calculate the hour and minute for the calendar
            	int start_time_hour = Integer.parseInt(start_time.substring(0,2));
            	int start_time_min = Integer.parseInt(start_time.substring(3,5));
            	int end_time_hour = Integer.parseInt(end_time.substring(0,2));
            	int end_time_min = Integer.parseInt(end_time.substring(3,5));
            	
            	Intent intent = new Intent(Intent.ACTION_EDIT);
            	intent.setType("vnd.android.cursor.item/event");
            	cal.set( year, month, date, start_time_hour, start_time_min);
            	intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
            			cal.getTimeInMillis());
            	cal.set( year, month, date, end_time_hour, end_time_min);
            	intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
            			cal.getTimeInMillis());
            	intent.putExtra("allDay", false);
            	intent.putExtra("title", "A route notification from naf Assig3");
            	intent.putExtra("eventLocation", start_location);
            	startActivity(intent);
            }
        });
        return rootView;
    }
    
    private class RouteGenerate extends AsyncTask<String, Void, Map<String, String>>{
    	ProgressDialog pDialog;
    	@Override
	    protected void onPreExecute() {
	        // TODO Auto-generated method stub
	        super.onPreExecute();
	        pDialog = new ProgressDialog(getActivity());
	        pDialog.setMessage("Wait for the route!");
	        pDialog.setCancelable(true);
	        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        pDialog.show();    
	    }
    	
		@Override
		protected Map<String, String> doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.i("doInBackground" , "Process in Background");
			String address1 = params[0];
			String address2 = params[1];
			Map<String, String> mMap=routeMapGenerator(address1,address2);
			return mMap;
		}
		
		private Map<String, String> routeMapGenerator(String address1, String address2){
			String jsonOut = null; 
			Map<String, String> routeInfo = new TreeMap<String, String>();
			String url = "http://group01.naf.cs.hut.fi/hsl.php?token="+token+"&loc1="+address1+"&loc2="+address2;
			Log.i("url", url);
    		try{
	        	InputStream is = new URL(url).openStream();
	        	try {
	        	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	        	      jsonOut = readAll(rd);
	        	    } finally {
	        	      is.close();
	        	    }
	        	try {
					JSONArray jsonArray1 = new JSONArray(jsonOut);
					JSONArray jsonArray2=(JSONArray)jsonArray1.get(0);
					JSONObject obj=(JSONObject)jsonArray2.get(0);
					JSONArray jsonArray3=(JSONArray)obj.get("legs");
					String distance="Distance: "+String.valueOf(obj.get("length"))+"m";
					String time="Time: "+String.valueOf(obj.get("duration"))+"s";
					routeInfo.put("Distance", distance);
					routeInfo.put("Time",  time);
					leg_key = "leg"+ Integer.toString(jsonArray3.length());
					for(int leg=0;leg<jsonArray3.length();leg++){
						String legDetail;
						String key;
						key = "leg"+Integer.toString(leg+1);
						legDetail = legDetailGenerator(jsonArray3,leg,key);
						Log.i("legdetail", legDetail);
						routeInfo.put(key,  legDetail);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }catch(Exception e)
			{
				System.err.println("Error do in background: "+ e.getMessage());
			}
			
        	
			return routeInfo;
    		
		}
		
		// get detail of one leg
		private String legDetailGenerator(JSONArray jsonArray, int leg, String key){
			String t0;
			String t1;
			String start_loc;
			String end_loc;
			String type;
			String line;
			String legDetail = null;
			JSONObject tmpobj;
			
			try {
				tmpobj = (JSONObject)jsonArray.get(leg);
				type =String.valueOf(tmpobj.get("type"));
				
				JSONArray locs=(JSONArray)tmpobj.get("locs");
				JSONObject locsDetail1 = (JSONObject)locs.get(0);
				JSONObject locsDetail2 = (JSONObject)locs.get(locs.length()-1);
				
				//get the time
				t0 = String.valueOf(locsDetail1.get("arrTime"));
				t0 = t0.substring(8,10)+":"+t0.substring(10,12);
				start_loc = String.valueOf(locsDetail1.get("name"));
				t1 = String.valueOf(locsDetail2.get("arrTime"));
				t1 = t1.substring(8,10)+":"+t1.substring(10,12);
				end_loc = String.valueOf(locsDetail2.get("name"));				

				if(!type.equals("walk")){
					line =String.valueOf(tmpobj.get("code"));
					line = line.substring(1, 5);
					legDetail = key+": "+t0+"-"+t1+", bus line:"+line+" , from "+start_loc+" to "+end_loc;
				}else{
					if (start_loc.equals("null")){
						if (end_loc.equals("null")){
							legDetail = key+": "+t0+"-"+t1+", walk";
						}else{
							legDetail = key+": "+t0+"-"+t1+", walk to "+end_loc;
						}
					}else{
						if(end_loc.equals("null")){
							legDetail = key+": "+t0+"-"+t1+", walk from "+start_loc;
						}else{
							legDetail = key+": "+t0+"-"+t1+", walk from "+start_loc+" to "+end_loc;
						}
					}
				}
				
				//set calendar time 
				if(key.equals("leg1")){
					start_time = t0;
					Log.i("leg1 log", start_time);
				}else if(key.equals(leg_key)){
					end_time = t1;
					Log.i("final leg log", end_time);
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return legDetail;
		}

		
		//read the url file
		private String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }
		
		 @Override
	    protected void onPostExecute(Map result) {
	        // TODO Auto-generated method stub
	        super.onPostExecute(result);
	        if(result!=null){
		        String tmp="";
		        Iterator entries = result.entrySet().iterator();
		        while (entries.hasNext()) {
		          Entry thisEntry = (Entry) entries.next();
		          Object key = thisEntry.getKey();
		          Object value = thisEntry.getValue();
		          tmp = tmp+String.valueOf(value)+"\r\n";
		        }
		        routeShow.setText(tmp);
	        }
	        pDialog.dismiss();
      	    
	    }
    	
    }
    
	    @Override
		public void onResume() {
			super.onResume();
		    Session session = Session.getActiveSession();
		    if (session != null &&
		           (session.isOpened() || session.isClosed()) ) {
		        onSessionStateChange(session, session.getState(), null);
		        token=session.getAccessToken();
		        //Log.e("token",token);
		        editStart.setVisibility(View.VISIBLE);
		        editDest.setVisibility(View.VISIBLE);
		        btn_add.setVisibility(View.VISIBLE);
		        btn_check.setVisibility(View.VISIBLE);
		        routeShow.setVisibility(View.VISIBLE);        
		    }
			uiHelper.onResume();
		}
	
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			uiHelper.onActivityResult(requestCode, resultCode, data);
		}
	
		@Override
		public void onPause() {
			super.onPause();
		   editStart.setVisibility(View.GONE);
		   editDest.setVisibility(View.GONE);
		   btn_add.setVisibility(View.GONE);
		   btn_check.setVisibility(View.GONE);
		   routeShow.setVisibility(View.GONE);
		
			uiHelper.onPause();    
		}
	
		@Override
		public void onDestroy() {
			super.onDestroy();
			   editStart.setVisibility(View.GONE);
		        editDest.setVisibility(View.GONE);
		        btn_add.setVisibility(View.GONE);
		        btn_check.setVisibility(View.GONE);
		        routeShow.setVisibility(View.GONE);
		
			uiHelper.onDestroy();    
		}
	
		@Override
		public void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);
			uiHelper.onSaveInstanceState(outState);
		}
	
		private void onSessionStateChange(Session session, SessionState state,
				Exception exception) {
			if (state.isOpened()) {
				Log.i(TAG, "Logged in...");
			} else if (state.isClosed()) {
				editStart.setVisibility(View.GONE);
		        editDest.setVisibility(View.GONE);
		        btn_add.setVisibility(View.GONE);
		        btn_check.setVisibility(View.GONE);
		        routeShow.setVisibility(View.GONE);
		        routeShow.setText("");
				Log.i(TAG, "Logged out...");
			}
		}
	
		private Session.StatusCallback callback = new Session.StatusCallback() {
			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				onSessionStateChange(session, state, exception);
			}
		};

}