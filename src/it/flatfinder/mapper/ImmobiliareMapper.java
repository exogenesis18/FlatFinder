package it.flatfinder.mapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.flatfinder.building.Building;
import it.flatfinder.caller.Caller;
import it.flatfinder.mapper.utils.CommonFunctions;

public class ImmobiliareMapper extends Mapper{
	
	public List<Building> getBuildings(){
		
		Caller caller = new Caller();
		List<Building> buildings = new LinkedList<Building>();
		
		List<JSONObject> json = caller.getImmobiliareData();
		
		for(int i = 0; i < json.size(); i++) {
			JSONArray results = (JSONArray) ((JSONObject) ((JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) ((JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) json.get(i).get("props")).get("pageProps")).get("dehydratedState")).getJSONArray("queries")).get(0)).get("state")).get("data")).get("pages")).get(0)).get("results");
			
			//System.out.println(results);
			
			for (int j = 0; j < results.length(); j++) {
				Building building = new Building();
				building.setSite("IMMOBILIARE");
				building.setId(Integer.toString(((JSONObject) ((JSONObject) results.get(j)).get("realEstate")).getInt("id")));
				String title = ((JSONObject) ((JSONObject) results.get(j)).get("realEstate")).get("title").toString();
				building.setTitle(title);
				building.setLocation(title.split(",")[title.split(",").length-1]);
				building.setCategory(((JSONObject) ((JSONObject) ((JSONObject) results.get(j)).get("realEstate")).get("typology")).get("name").toString());
				building.setTrattativaRiservata(false);
				building.setUrl(((JSONObject) ((JSONObject) results.get(j)).get("seo")).get("url").toString());
				building.setContract(((JSONObject) ((JSONObject) results.get(j)).get("realEstate")).get("contract").toString());
				String roomString = ((JSONObject) ((JSONArray) ((JSONObject) ((JSONObject) results.get(j)).get("realEstate")).getJSONArray("properties")).get(0)).get("rooms").toString();
				building.setRooms(CommonFunctions.StringToInt(roomString));
				building.setMoreThanFiveRooms(roomString.contains("5+") ? true : false);
				String surfaceString = ((JSONObject) ((JSONArray) ((JSONObject) ((JSONObject) results.get(j)).get("realEstate")).getJSONArray("properties")).get(0)).get("surface").toString();
				building.setSurface(CommonFunctions.StringToInt(surfaceString));
				String bathroomsString = ((JSONObject) ((JSONArray) ((JSONObject) ((JSONObject) results.get(j)).get("realEstate")).getJSONArray("properties")).get(0)).get("bathrooms").toString();
				building.setBathrooms(CommonFunctions.StringToInt(bathroomsString));
				
				try {
					building.setAgency(((JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) results.get(j)).get("realEstate")).get("advertiser")).get("agency")).get("displayName").toString());
				}
				catch(Exception e) {
					building.setAgency("privato");
				}
				
				try {
					building.setPrice(((JSONObject) ((JSONObject) ((JSONObject) results.get(j)).get("realEstate")).get("price")).getInt("value"));
				}
				catch(JSONException e) {
					building.setPrice(0);
					building.setTrattativaRiservata(true);
				}

				buildings.add(building);
			}
		}
		
		return buildings;
		
	}

}
