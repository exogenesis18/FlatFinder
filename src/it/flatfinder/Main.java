package it.flatfinder;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import it.flatfinder.building.Building;
import it.flatfinder.mapper.ImmobiliareMapper;
import it.flatfinder.mapper.utils.CommonFunctions;
import it.flatfinder.telegram.FlatFinderBot;
import static java.util.concurrent.TimeUnit.*;


public class Main {

	public static void main(String[] args) throws IOException {
		
		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			
			FlatFinderBot bot = new FlatFinderBot();
			botsApi.registerBot(bot);
			
			Runnable sender = new Runnable() {
                public void run() { bot.sendUpdates(); }
            };
			
			Calendar calendar = Calendar.getInstance();
			ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
			scheduler.scheduleAtFixedRate(sender, millisToNextHour(calendar), 60*60*1000, TimeUnit.MILLISECONDS);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private static long millisToNextHour(Calendar calendar) {
	    int minutes = calendar.get(Calendar.MINUTE);
	    int seconds = calendar.get(Calendar.SECOND);
	    int millis = calendar.get(Calendar.MILLISECOND);
	    int minutesToNextHour = 60 - minutes;
	    int secondsToNextHour = 60 - seconds;
	    int millisToNextHour = 1000 - millis;
	    return minutesToNextHour*60*1000 + secondsToNextHour*1000 + millisToNextHour;
	}

}
