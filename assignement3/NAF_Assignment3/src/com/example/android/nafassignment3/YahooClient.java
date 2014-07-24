package com.example.android.nafassignment3;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/*****************************
 *     YAHOO CLIENT			 *
 *****************************/

/************************************************************************************************ 
 * REFERENCE: http://android-er.blogspot.fi/2013/03/example-to-start-asynctask-when-button.html *
 ************************************************************************************************/
public class YahooClient {
	String description;
	String city;
	String region;
	String country;
	String windChill;
	String windDirection;
	String windSpeed;
	String sunrise;
	String sunset;
	String conditiontext;
	String conditiondate;
	String forecast;
	String imageweather;
	
	public YahooClient()
	{
		setDescription("");
		setCity("");
		setRegion("");
		setCountry("");
		setWindChill("");
		setWindDirection("");
		setWindSpeed("");
		setSunrise("");
		setSunset("");
		setConditiontext("");
		setConditiondate("");
		setImageWeather("");
		setForecast("");
	}

	public String toString(){
		
		return "\n- " + getDescription() + " -\n"
			+ "City: " + getCity() + ", " + getRegion() +"\n"
			+ "Country: " + getCountry() + "\n\n"
	
			+ "Condition: " + getConditiontext() + "\n"
			+ getConditiondate() +"\n"
			+ "Wind\n"
			+ "Chill: " + getWindChill() + " "
			+ "Direction: " + getWindDirection() + " "
			+ "Speed: " + getWindSpeed() + "\n\n"

			+ "Sunrise: " + getSunrise() + " "
			+ "Sunset: " + getSunset() + "\n\n"
	
			+ "Forecast\n"
			+ getForecast(); 
	}	

	 
    private static String BASE_URL = "http://weather.yahooapis.com/forecastrss?w=";
     
    public String getWeatherData(String location) {
        URL url=null;
		String unit="&u=c";
        try {
        	  url = new URL(BASE_URL + location+ unit);
              HttpURLConnection con = (HttpURLConnection) url.openConnection();
              con.setRequestMethod("GET");
              con.connect();
	          StringBuffer buffer = new StringBuffer();
	          InputStream is = con.getInputStream();
	          BufferedReader br = new BufferedReader(new InputStreamReader(is));
	          String line = null;
            while (  (line = br.readLine()) != null )
                buffer.append(line + "\r\n");             
            is.close();
            con.disconnect();
            Log.e("value",buffer.toString());
            return buffer.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        return null;        
    }
    
    public Bitmap downloadImage (String imageHttpAddress){
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
        
    public YahooClient parseWeather(org.w3c.dom.Document srcDoc){
	   YahooClient weather = new YahooClient();
	 try
	 {
    	//<description>
	   weather.setDescription(srcDoc.getElementsByTagName("description").item(0).getTextContent());
       
	   //<img value/>
	   String imagevalue=srcDoc.getElementsByTagName("description").item(1).getTextContent();
	   int nodeLst = imagevalue.indexOf("src");
	   int fin = imagevalue.indexOf("/>");   
	   weather.setImageWeather(srcDoc.getElementsByTagName("description").item(1).getTextContent().substring(nodeLst+4, fin).replace("\"", ""));   

	   //<yweather:location/>
       Node locationNode = srcDoc.getElementsByTagName("yweather:location").item(0);
       weather.setCity(locationNode.getAttributes().getNamedItem("city").getNodeValue().toString());
       weather.setRegion(locationNode.getAttributes().getNamedItem("region").getNodeValue().toString());
       weather.setCountry(locationNode.getAttributes().getNamedItem("country").getNodeValue().toString());
    	
       //<yweather:wind/>
       Node windNode = srcDoc.getElementsByTagName("yweather:wind").item(0);
       weather.setWindChill(windNode.getAttributes().getNamedItem("chill").getNodeValue().toString());
       weather.setWindDirection(windNode.getAttributes().getNamedItem("direction").getNodeValue().toString());
       weather.setWindSpeed(windNode.getAttributes().getNamedItem("speed").getNodeValue().toString());

    	//<yweather:astronomy/>
    	Node astronomyNode = srcDoc.getElementsByTagName("yweather:astronomy").item(0);
    	weather.setSunrise(astronomyNode.getAttributes().getNamedItem("sunrise").getNodeValue().toString());
    	weather.setSunset(astronomyNode.getAttributes().getNamedItem("sunset").getNodeValue().toString());

    	//<yweather:condition/>
    	Node conditionNode = srcDoc.getElementsByTagName("yweather:condition").item(0);
    	weather.setConditiontext(conditionNode.getAttributes().getNamedItem("text").getNodeValue().toString());
    	weather.setConditiondate(conditionNode.getAttributes().getNamedItem("date").getNodeValue().toString());
    	
    	//<yweather:forecast/>
    	NodeList forecastList = srcDoc.getElementsByTagName("yweather:forecast");
    	weather.forecast = "";
    	if(forecastList.getLength() > 0){
    		for(int i = 0; i < forecastList.getLength(); i++){
    			Node forecastNode = forecastList.item(i);
    			//.forecast +=
    			weather.setForecast(weather.getForecast()+ forecastNode.getAttributes().getNamedItem("date").getNodeValue().toString() + " " +
    					           forecastNode.getAttributes().getNamedItem("text").getNodeValue().toString() +" High: " +
    					           forecastNode.getAttributes().getNamedItem("high").getNodeValue().toString() +" Low: " + 
    					           forecastNode.getAttributes().getNamedItem("low").getNodeValue().toString() + "\n");
    		}
    	}else{
    		weather.forecast = "No forecast";
    	}
	 
    }catch (Exception err) {
       System.err.println(" " + err.getMessage ());
       }catch (Throwable t) {
       t.printStackTrace ();
       }
    	return weather;	
    }
   
   /*=================GETTERS AND SETTERS=================*/
		public String getDescription() {
			return description;
		}
		
		public void setDescription(String description) {
			this.description = description;
		}
		
		public String getCity() {
			return city;
		}
		
		public void setCity(String city) {
			this.city = city;
		}
		
		public String getRegion() {
			return region;
		}
		
		public void setRegion(String region) {
			this.region = region;
		}
		
		public String getCountry() {
			return country;
		}
		
		public void setCountry(String country) {
			this.country = country;
		}
		
		public String getWindChill() {
			return windChill;
		}
		
		public void setWindChill(String windChill) {
			this.windChill = windChill;
		}
		
		public String getWindDirection() {
			return windDirection;
		}
		
		public void setWindDirection(String windDirection) {
			this.windDirection = windDirection;
		}
		
		public String getWindSpeed() {
			return windSpeed;
		}
		
		public void setWindSpeed(String windSpeed) {
			this.windSpeed = windSpeed;
		}
		
		public String getSunrise() {
			return sunrise;
		}
		
		public void setSunrise(String sunrise) {
			this.sunrise = sunrise;
		}
		
		public String getSunset() {
			return sunset;
		}
		
		public void setSunset(String sunset) {
			this.sunset = sunset;
		}
		
		public String getConditiontext() {
			return conditiontext;
		}
		
		public void setConditiontext(String conditiontext) {
			this.conditiontext = conditiontext;
		}
		
		public String getConditiondate() {
			return conditiondate;
		}
		
		public void setConditiondate(String conditiondate) {
			this.conditiondate = conditiondate;
		}
		
		public String getForecast() {
			return forecast;
		}
		
		public void setForecast(String forecast) {
			this.forecast = forecast;
		}
		
		public String getImageWeather() {
			return imageweather;
		}
		
		public void setImageWeather(String imageweather) {
			this.imageweather = imageweather;	
		}
}