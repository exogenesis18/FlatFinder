package it.flatfinder;

import java.io.IOException;
import java.util.List;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import it.flatfinder.building.Building;
import it.flatfinder.mapper.ImmobiliareMapper;
import it.flatfinder.mapper.utils.CommonFunctions;
import it.flatfinder.telegram.FlatFinderBot;


public class Main {

	public static void main(String[] args) throws IOException {
		
		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(new FlatFinderBot());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
