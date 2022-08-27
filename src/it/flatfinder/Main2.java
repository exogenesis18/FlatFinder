package it.flatfinder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import it.flatfinder.building.Building;
import it.flatfinder.caller.Caller;
import it.flatfinder.mapper.CasaMapper;
import it.flatfinder.mapper.ImmobiliareMapper;
import it.flatfinder.mapper.universal.UniversalMapper;
import it.flatfinder.mapper.utils.CommonFunctions;
import it.flatfinder.telegram.FlatFinderBot;


public class Main2 {

	public static void main(String[] args) throws IOException {
		
		try {
			UniversalMapper mapper = new UniversalMapper();
			
			List<Building> buildings = mapper.getBuildings();
			
		    FileWriter myWriter = new FileWriter("output.txt");
		    for (Building building: buildings)
		    	myWriter.write(building.toString());
		    
		    myWriter.close();
			

		} catch (IOException | InterruptedException e1) {
			
			e1.printStackTrace();
		}
	}
}
