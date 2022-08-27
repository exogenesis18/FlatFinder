package it.flatfinder.mapper;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.flatfinder.building.Building;
import it.flatfinder.caller.Caller;
import it.flatfinder.mapper.utils.CommonFunctions;

public class CasaMapper extends Mapper{

	@Override
	public List<Building> getBuildings() throws IOException, InterruptedException {
		
		Caller caller = new Caller();
		List<Building> buildings = new LinkedList<Building>();
		
		List<JSONObject> json = caller.getCasaData();
		
		for(int i = 0; i < json.size(); i++) {
			
			JSONArray results = (JSONArray) ((JSONObject) json.get(i).get("agencySrp")).getJSONArray("list");
			
			for (int j = 0; j < results.length(); j++) {
				Building building = new Building();
				building.setSite("CASA");
				building.setId(Integer.toString(((JSONObject) results.get(j)).getInt("id")));
				String title = ((JSONObject) results.get(j)).get("galleryImgAltText").toString();
				building.setTitle(title);
				building.setLocation(((JSONObject) ((JSONObject) ((JSONObject) results.get(j)).get("property")).get("geo")).get("city").toString());
				building.setCategory(((JSONObject) ((JSONObject) results.get(j)).get("property")).get("immotype").toString());
				building.setTrattativaRiservata(false);
				building.setUrl("https://www.casa.it" + ((JSONObject) results.get(j)).get("pdpUri").toString());
				building.setContract(((JSONObject) results.get(j)).get("channel").toString());
				building.setRooms(((JSONObject) ((JSONObject) results.get(j)).get("features")).getInt("rooms"));
				building.setMoreThanFiveRooms(building.getRooms() > 5 ? true : false);
				String surfaceString = ((JSONObject) ((JSONObject) results.get(j)).get("features")).get("mq").toString();
				building.setSurface(CommonFunctions.StringToInt(surfaceString));
				building.setBathrooms(0);
				
				try {
					building.setAgency(((JSONObject) ((JSONObject) results.get(j)).get("publisher")).get("name").toString());
					if(building.getAgency().equals("null"))
						building.setAgency("privato");
				}
				catch(Exception e) {
					building.setAgency("privato");
				}
				
				try {
					building.setPrice(((JSONObject) ((JSONObject) ((JSONObject) results.get(j)).get("features")).get("price")).getInt("value"));
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