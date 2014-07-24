package com.example.android.nafassignment3;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.*;
import android.widget.*;

import com.example.android.effectivenavigation.R;
import com.google.gson.Gson;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import android.support.v4.app.Fragment;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/*****************************
 * TWITTER CLIENT			 *
 *****************************/

/********************************************************************************* 
 * REFERENCE: http://www.androidhive.info/2012/01/android-json-parsing-tutorial/ *
 * 			  http://java.dzone.com/articles/how-build-android-twitter           *
 *********************************************************************************/

public class TwitterClient extends Fragment {
	String Key = null;
	String Secret = null;
	private ListView list;
	ArrayList<HashMap<String, String>> searchTimelineList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.twitter_client, container, false);
		Key = getKeyFromManifest("CONSUMER_KEY");
		Secret = getKeyFromManifest("CONSUMER_SECRET");
		list= (ListView) rootView.findViewById(R.id.get_list);
		rootView.findViewById(R.id.get_timeline)
        .setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view){
        	GetTwitterTimeline timeline= new GetTwitterTimeline();
        	EditText editText = (EditText) rootView.findViewById(R.id.txtsearchword);
        	String search_term = editText.getText().toString();
        	if(search_term.matches(""))
        		Toast.makeText(getActivity(), "Please enter a search term", Toast.LENGTH_SHORT).show();
        	else
        		timeline.execute(search_term);
        	}
        });		
		return rootView;
	}

	private String getKeyFromManifest(String key) {
		String results = null;
		try {
			Context context = getActivity().getBaseContext();
			ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			results = (String)ai.metaData.get(key);
		} catch (PackageManager.NameNotFoundException e) {
			System.err.println("Error on getKeyFromManifest: "+ e.getMessage());
		}
		return results;
	}

	private class GetTwitterTimeline extends AsyncTask<String, Void, String> {
		final static String TwitterTokenURL = "https://api.twitter.com/oauth2/token";
		final static String TwitterSearchURL = "https://api.twitter.com/1.1/search/tweets.json?q=";
		ProgressDialog pDialog;
	 	 
		    @Override
		    protected void onPreExecute() {
		        // TODO Auto-generated method stub
		        super.onPreExecute();
		        pDialog = new ProgressDialog(getActivity());
		        pDialog.setMessage("Searching..");
		        pDialog.setCancelable(true);
		        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		        pDialog.show();    
		    }
		
			@Override
			protected String doInBackground(String... search_Terms) {
				String result = null;
				if (search_Terms.length > 0) {
					result = searchStream(search_Terms[0]);
				}
				return result;
			}

			@Override
			protected void onPostExecute(String result) {
				if (pDialog.isShowing())
	                pDialog.dismiss();
				try{
					searchTimelineList = new ArrayList<HashMap<String, String>>();
					JSONArray statuses = null;
					JSONObject jsonObj = new JSONObject(result);
				    statuses = jsonObj.getJSONArray("statuses");
				    
		            for (int i = 0; i < statuses.length(); ++i) {
		                JSONObject rec = statuses.getJSONObject(i);
		                String text = rec.getString("text");
		                JSONObject user = rec.getJSONObject("user");
		                String username = user.getString("name");
		                
		                HashMap<String, String> searchtimeline = new HashMap<String, String>();
		                searchtimeline.put("id", Integer.toString(i));
		                searchtimeline.put("username", username);
		                searchtimeline.put("text", text);
		                searchTimelineList.add(searchtimeline);
		            }  
		            ListAdapter adapter = new SimpleAdapter(
		            		getActivity(), searchTimelineList,
		                    R.layout.tweet_search, new String[] { "username", "text"}, new int[] { R.id.username,
		                            R.id.text});
		            list.setAdapter(adapter);			
		        }
				catch(Exception e)
				{
					System.err.println("Error onPostExecute: "+ e.getMessage());
				}
			}


			private String searchStream(String searchTerm) {
					String results = null;
					try {
						String encodedUrl = URLEncoder.encode(searchTerm, "UTF-8");
						results = getStream(TwitterSearchURL + encodedUrl);
					} catch (UnsupportedEncodingException ex) {
						System.err.println("Error searchStream - UnsupportedEncodingException: "+ ex.getMessage());
					} catch (IllegalStateException ex1) {
						System.err.println("Error searchStream - IllegalStateException: "+ ex1.getMessage());
					}
					return results;
			}
			
			private class Authenticated {
				String token_type;
				String access_token;
			
				// convert a JSON authentication object into an Authenticated object
				public Authenticated jsonToAuthenticated(String rawAuthorization) {
					Authenticated auth = null;
					if (rawAuthorization != null && rawAuthorization.length() > 0) {
						try {
							Gson gson = new Gson();
							auth = gson.fromJson(rawAuthorization, Authenticated.class);
						} catch (IllegalStateException ex) {
							System.err.println("Error on jsonToAuthenticated: "+ ex.getMessage());
						}
					}
					return auth;
				}
			}

			private String getHttpResponse(HttpRequestBase request) {
				StringBuilder sb = new StringBuilder();
				try {
					HttpResponse response = new DefaultHttpClient(new BasicHttpParams()).execute(request);
					int statusCode = response.getStatusLine().getStatusCode();
					String reason = response.getStatusLine().getReasonPhrase();
					if (statusCode == 200) {
						HttpEntity entity = response.getEntity();
						InputStream inputStream = entity.getContent();
						BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
						String line = null;
						while ((line = bReader.readLine()) != null) {
							sb.append(line);
						}
					} else {
						sb.append(reason);
					}
				} catch (UnsupportedEncodingException ex) {
					System.err.println("Error on getHttpResponse - UnsupportedEncodingException: "+ ex.getMessage());
				} catch (ClientProtocolException ex1) {
					System.err.println("Error on getHttpResponse - ClientProtocolException: "+ ex1.getMessage());
				} catch (IOException ex2) {
					System.err.println("Error on getHttpResponse - IOException: "+ ex2.getMessage());
				}
				return sb.toString();
			}

			private String getStream(String url) {
				String results = null;
				try {
					String urlApiKey = URLEncoder.encode(Key, "UTF-8");
					String urlApiSecret = URLEncoder.encode(Secret, "UTF-8");
					String combined = urlApiKey + ":" + urlApiSecret;
					String base64Encoded = Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP);
					HttpPost httpPost = new HttpPost(TwitterTokenURL);
					httpPost.setHeader("Authorization", "Basic " + base64Encoded);
					httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
					httpPost.setEntity(new StringEntity("grant_type=client_credentials"));
					String rawAuthorization = getHttpResponse(httpPost);
					Authenticated auth = new Authenticated().jsonToAuthenticated(rawAuthorization);
					if (auth != null && auth.token_type.equals("bearer")) {
						HttpGet httpGet = new HttpGet(url);
						httpGet.setHeader("Authorization", "Bearer " + auth.access_token);
						httpGet.setHeader("Content-Type", "application/json");
						results = getHttpResponse(httpGet);
					}
				} catch (UnsupportedEncodingException ex) {
					System.err.println("Error on getStream - UnsupportedEncodingException: "+ ex.getMessage());
				} catch (IllegalStateException ex1) {
					System.err.println("Error on getStream - IllegalStateException: "+ ex1.getMessage());
				}
				return results;
			}
	}
}