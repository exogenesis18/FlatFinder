package it.flatfinder.caller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import it.flatfinder.building.Building;

public class ImmobiliareCaller {
	
	public List<Building> getResults(){
		
		List<Building> buildings = new LinkedList<Building>();
		
		List<JSONObject> json = getJsons();
		
		for(int i = 0; i < json.size(); i++) {
			JSONArray results = (JSONArray) ((JSONObject) ((JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) ((JSONArray) ((JSONObject) ((JSONObject) ((JSONObject) json.get(i).get("props")).get("pageProps")).get("dehydratedState")).getJSONArray("queries")).get(0)).get("state")).get("data")).get("pages")).get(0)).get("results");
			
			for (int j = 0; j < results.length(); j++) {
				Building building = new Building();
				building.setId(Integer.toString(((JSONObject) ((JSONObject) results.get(j)).get("realEstate")).getInt("id")));
				building.setTitle((String) ((JSONObject) ((JSONObject) results.get(j)).get("realEstate")).get("title"));
				building.setCategory((String) ((JSONObject) ((JSONObject) ((JSONObject) results.get(j)).get("realEstate")).get("typology")).get("name"));
				building.setPrice((((JSONObject) ((JSONObject) ((JSONObject) results.get(j)).get("realEstate")).get("price")).get("value").equals("null") ? 0 : ((JSONObject) ((JSONObject) ((JSONObject) results.get(j)).get("realEstate")).get("price")).getInt("value")));
				//building.setPrice(((JSONObject) ((JSONObject) ((JSONObject) results.get(j)).get("realEstate")).get("price")).getInt("value"));

				buildings.add(building);
			}
		}
		
		return buildings;
		
	}
	
	private List<JSONObject> getJsons() {
		
		List<JSONObject> jsons = new LinkedList<JSONObject>();
		
		for(int i = 2; i <=20; i++) {
			String url = "https://www.immobiliare.it/affitto-case/verona-provincia/?pag=" + i;
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
			      .uri(URI.create(url))
			      .build();
			String risposta = client.sendAsync(request, BodyHandlers.ofString())
			      .thenApply(HttpResponse::body)
			      .join();	
			//System.out.println(risposta);
			
			JSONObject json = new JSONObject(risposta.substring(risposta.indexOf("application/json")+18, risposta.length()-109));
			jsons.add(json);
		}
		
		return jsons;		
	}

}
