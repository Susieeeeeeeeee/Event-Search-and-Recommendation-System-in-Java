package external;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.naming.java.javaURLContextFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class TicketMasterAPI {
	private static final String URL = "https://app.ticketmaster.com/discovery/v2/events.json";
	private static final String DEFAULT_KEYWORD = "";
	private static final String API_KEY = "Tj0vP7PJSSsIp1vHb4Jjq3WwfzNXUH6d";
	
	/**
	 * 提供经纬度和keyword
	 * @param lat
	 * @param lon
	 * @param keyword
	 * @return
	 */
	public JSONArray search(double lat, double lon, String keyword) {
		if (keyword == null) {
			keyword = DEFAULT_KEYWORD;
		}
		
		try {
			keyword = java.net.URLEncoder.encode(keyword, "UTF-8"); //e.g 20%
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//construct query
		String geoHash = GeoHash.encodeGeohash(lat, lon, 8);
		String query = String.format("apikey=%s&geoPoint=%s&keyword=%s&radius=%s", API_KEY, geoHash, keyword, 50); // 50 mile
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(URL + "?" + query).openConnection();//cast to httpurlconnection
			int responseCode = connection.getResponseCode(); // get response code: open connect and get response code 
			if (responseCode != 200) {
				return new JSONArray();
			}
			
			//below is try with resource, will auto close
			StringBuilder response = new StringBuilder();
			try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
			}
			
			JSONObject obj = new JSONObject(response.toString());
			if (obj.isNull("_embedded")) { // see if the value associated with key "_embedded" is null or not
				return new JSONArray(); // do not return null, for convenience
			}
			
			JSONObject embededJsonObject = obj.getJSONObject("_embedded");
			JSONArray events = embededJsonObject.getJSONArray("events");
			return events;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return new JSONArray();
	}
	
	private void queryAPI(double lat, double lon) {
		JSONArray events = search(lat, lon, null);
		try {
			for (int i = 0; i < events.length(); i++) {
				JSONObject event = events.getJSONObject(i);
				System.out.println(event);
			}  
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		TicketMasterAPI tmApi = new TicketMasterAPI();
		tmApi.queryAPI(29.682684, -95.295410);
		
	}
}
