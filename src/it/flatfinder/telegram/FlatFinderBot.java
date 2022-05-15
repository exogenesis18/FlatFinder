package it.flatfinder.telegram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import it.flatfinder.building.Building;
import it.flatfinder.filter.UserFilter;
import it.flatfinder.filter.applier.UserFilterApplier;
import it.flatfinder.filter.enums.AgencyFilterEnum;
import it.flatfinder.filter.enums.ContractFilterEnum;
import it.flatfinder.filter.enums.RoomsFilterEnum;
import it.flatfinder.mapper.ImmobiliareMapper;
import it.flatfinder.mapper.universal.UniversalMapper;
import it.flatfinder.telegram.utils.FlatFinderBotCommonFunctions;

/*
 * cerca_appartamenti - Cerca gli appartamenti in base ai filtri applicati
 * seleziona_filtri - Seleziona i filtri
 * visualizza_filtri - Visualizza i filtri impostati
 * help - Istruzioni
 * 
 * */

public class FlatFinderBot extends TelegramLongPollingBot{
	
	private Map<Long,List<Building>> buildings = new HashMap<>();
	private Map<Long,UserFilter> userFilter = new HashMap<>();
	private boolean selezioneMinPrice = false;
	private boolean selezioneMaxPrice = false;
	private boolean selezioneMinSurface = false;
	private boolean selezioneMaxSurface = false;	
	
	private FlatFinderBotCommonFunctions telegramFunctions = new FlatFinderBotCommonFunctions();
	
	public String getBotToken() {
		return "";
	}

	@Override
	public void onUpdateReceived(Update update) {
		// TODO Auto-generated method stub
		// We check if the update has a message and the message has text
    	
	    if (update.hasMessage() && update.getMessage().hasText()) {
	    	
	    	Long chatId = update.getMessage().getChatId();
	    	System.out.println(chatId + " (" + update.getMessage().getFrom().getFirstName() + ")" + " ha inviato " + update.getMessage().getText());
	    	
	    	if(update.getMessage().getText().equals("/start") || update.getMessage().getText().equals("/help")) {
				
				SendMessage message = telegramFunctions.sendMessage(update, "Ciao! Seleziona i filtri di ricerca tramite il comando /seleziona_filtri");
				
	        	try {
					execute(message);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
				
	    		
	    	}
	    	else if(update.getMessage().getText().equals("/cerca_appartamenti")) {
				
		        SendMessage message;
		       
		        if(userFilter.get(chatId) == null) {
		        	message = telegramFunctions.sendMessage(update, "Non hai selezionato nessun filtro, usa il comando /seleziona_filtri per cominciare");
		        	
			        try {
			            execute(message);
			        } catch (TelegramApiException e) {
			            e.printStackTrace();
			        }
		        }
		        else {
		        	
					UniversalMapper mapper = new UniversalMapper();
					buildings.put(chatId, mapper.getBuildings());
										
					 for (Building elem: new UserFilterApplier(buildings.get(chatId), userFilter.get(chatId)).applyFilter()) {
			        	message = telegramFunctions.sendMessage(update, elem.toString());
			        	
				        try {
				            execute(message);
				        } catch (TelegramApiException e) {
				            e.printStackTrace();
				        }
					 }
		        }
		        
		    }
	    	else if(update.getMessage().getText().equals("/seleziona_filtri")) {
	    		
	    		SendMessage message = telegramFunctions.sendMessage(update, "Seleziona contratto");
                message.setReplyMarkup(telegramFunctions.addInlineKeyboardMarkup("Indifferente", "contratto_indifferente", "1", "Affitto", "contratto_affitto", "2", "Vendita", "contratto_vendita", "2"));
                
		        try {
		            execute(message);
	                userFilter.put(chatId, new UserFilter());
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        
                
	    		
	    	}
	    	else if(update.getMessage().getText().equals("/visualizza_filtri")) {
	    		
	    		SendMessage message = new SendMessage();
	    		
	    		if(userFilter.get(chatId) == null)
		        	message = telegramFunctions.sendMessage(update, "Non hai selezionato nessun filtro, usa il comando /seleziona_filtri per cominciare");
	    		else
	    			message = telegramFunctions.sendMessage(update, userFilter.get(chatId).toString());
                
		        try {
		            execute(message);
		        } catch (TelegramApiException e) {
		            e.printStackTrace();
		        }
	    		
	    	}
	    	else if(selezioneMinPrice) {
	    		
	    		SendMessage message;
	    		try {
	    			int val = Integer.parseInt(update.getMessage().getText());
	    			if(val < 0 || val > 10000000)
	    				throw new Exception();
		    		userFilter.get(chatId).setMinPrice(Integer.parseInt(update.getMessage().getText()));
		    		selezioneMinPrice = false;
		    		selezioneMaxPrice = true;
		        	message = telegramFunctions.sendMessage(update, "Qual è prezzo massimo? (sono consentiti valori da 1 a 10000000 e non minori del prezzo minimo)\nDigita 0 se è indifferente");
	    		}
	    		catch(Exception e) {
		        	message = telegramFunctions.sendMessage(update, "Il valore inserito non è valido, riprova");
	    		}
	    		
		        try {
		            execute(message);
		        } catch (TelegramApiException e) {
		            e.printStackTrace();
		        }
	    		
	    	}
	    	else if(selezioneMaxPrice) {
	    		
	    		SendMessage message;
	    		try {
	    			int val = Integer.parseInt(update.getMessage().getText());
	    			if(val < 0 || val > 10000000 || (val < userFilter.get(chatId).getMinPrice() && val != 0))
	    				throw new Exception();
		    		userFilter.get(chatId).setMaxPrice(Integer.parseInt(update.getMessage().getText()));
		    		selezioneMaxPrice = false;
		        	message = telegramFunctions.sendMessage(update, "Quante stanze deve avere come minimo?");
	                message.setReplyMarkup(telegramFunctions.addInlineKeyboardMarkup("Indifferente", "min_stanze_indifferente","1", "1", "min_stanze_una", "2", "2", "min_stanze_due","2", "3", "min_stanze_tre", "2","4", "min_stanze_quattro","3","5", "min_stanze_cinque","3","5+", "min_stanze_cinque_plus","3"));

	    		}
	    		catch(Exception e) {
		        	message = telegramFunctions.sendMessage(update, "Il valore inserito non è valido, riprova");
	    		}
	    		
		        try {
		            execute(message);
		        } catch (TelegramApiException e) {
		            e.printStackTrace();
		        }
	    		
	    	}
	    	else if(selezioneMinSurface) {
	    		
	    		SendMessage message;
	    		try {
	    			int val = Integer.parseInt(update.getMessage().getText());
	    			if(val < 0 || val > 5000)
	    				throw new Exception();
		    		userFilter.get(chatId).setMinSurface(Integer.parseInt(update.getMessage().getText()));
		    		selezioneMinSurface = false;
		    		selezioneMaxSurface = true;
		        	message = telegramFunctions.sendMessage(update, "Qual è la grandezza massima in metri quadrati? (sono consentiti valori fra 1 e 5000 e non minori della grandezza minima)\nDigita 0 se è indifferente");
	    		}
	    		catch(Exception e) {
		        	message = telegramFunctions.sendMessage(update, "Il valore inserito non è valido, riprova");
	    		}
	    		
		        try {
		            execute(message);
		        } catch (TelegramApiException e) {
		            e.printStackTrace();
		        }
	    		
	    	}
	    	else if(selezioneMaxSurface) {
	    		
	    		SendMessage message;
	    		try {
	    			int val = Integer.parseInt(update.getMessage().getText());
	    			if(val < 0 || val > 5000 || (val < userFilter.get(chatId).getMinSurface() && val != 0))
	    				throw new Exception();
		    		userFilter.get(chatId).setMaxSurface(Integer.parseInt(update.getMessage().getText()));
		    		selezioneMaxSurface = false;
		        	message = telegramFunctions.sendMessage(update, "Agenzia o privato?");
	                message.setReplyMarkup(telegramFunctions.addInlineKeyboardMarkup("Indifferente", "agenzia_indifferente","1", "Agenzia", "agenzia_si", "2", "Privato", "agenzia_no", "2"));

	    		}
	    		catch(Exception e) {
		        	message = telegramFunctions.sendMessage(update, "Il valore inserito non è valido, riprova");
	    		}
	    		
		        try {
		            execute(message);
		        } catch (TelegramApiException e) {
		            e.printStackTrace();
		        }
	    		
	    	}
	    	
	    }
	    else if (update.hasCallbackQuery()) {
	    	
        	EditMessageText newMessage = new EditMessageText();
        	
        	Long chatId = update.getCallbackQuery().getFrom().getId();


            if(update.getCallbackQuery().getData().startsWith("contratto")) {
            	userFilter.get(chatId).setContractFilter(update.getCallbackQuery().getData().endsWith("indifferente") ? ContractFilterEnum.INDIFFERENTE : update.getCallbackQuery().getData().endsWith("affitto") ? ContractFilterEnum.AFFITTO : ContractFilterEnum.VENDITA);
            	newMessage = telegramFunctions.editMessageText(update, "Qual è prezzo minimo? (sono consentiti valori da 1 a 10000000)\nDigita 0 se è indifferente");
            	selezioneMinPrice = true;
            }
            if(update.getCallbackQuery().getData().startsWith("min_stanze")) {
            	userFilter.get(chatId).setMinRooms(update.getCallbackQuery().getData().endsWith("indifferente") ? RoomsFilterEnum.INDIFFERENTE : update.getCallbackQuery().getData().endsWith("una") ? RoomsFilterEnum.ONE : update.getCallbackQuery().getData().endsWith("due") ? RoomsFilterEnum.TWO : update.getCallbackQuery().getData().endsWith("tre") ? RoomsFilterEnum.THREE : update.getCallbackQuery().getData().endsWith("quattro") ? RoomsFilterEnum.FOUR : update.getCallbackQuery().getData().endsWith("cinque") ? RoomsFilterEnum.FIVE : RoomsFilterEnum.FIVEPLUS);
            	newMessage = telegramFunctions.editMessageText(update, "Quante stanze deve avere come massimo?");
            	switch(userFilter.get(chatId).getMinRooms()) {
            		case INDIFFERENTE:
                    newMessage.setReplyMarkup(telegramFunctions.addInlineKeyboardMarkup("Indifferente", "max_stanze_indifferente","1", "1", "max_stanze_una", "2", "2", "max_stanze_due","2", "3", "max_stanze_tre", "2","4", "max_stanze_quattro","3","5", "max_stanze_cinque","3","5+", "max_stanze_cinque_plus","3"));
                    break;
            		case ONE:
                    newMessage.setReplyMarkup(telegramFunctions.addInlineKeyboardMarkup("Indifferente", "max_stanze_indifferente","1", "1", "max_stanze_una", "2", "2", "max_stanze_due","2", "3", "max_stanze_tre", "2","4", "max_stanze_quattro","3","5", "max_stanze_cinque","3","5+", "max_stanze_cinque_plus","3"));
                    break;
            		case TWO:
                    newMessage.setReplyMarkup(telegramFunctions.addInlineKeyboardMarkup("Indifferente", "max_stanze_indifferente","1", "2", "max_stanze_due","2", "3", "max_stanze_tre", "2","4", "max_stanze_quattro","2","5", "max_stanze_cinque","3","5+", "max_stanze_cinque_plus","3"));
            		break;
            		case THREE:
                    newMessage.setReplyMarkup(telegramFunctions.addInlineKeyboardMarkup("Indifferente", "max_stanze_indifferente","1","3", "max_stanze_tre", "2","4", "max_stanze_quattro","2","5", "max_stanze_cinque","2","5+", "max_stanze_cinque_plus","3"));
                    break;
            		case FOUR:
                    newMessage.setReplyMarkup(telegramFunctions.addInlineKeyboardMarkup("Indifferente", "max_stanze_indifferente","1","4", "max_stanze_quattro","2","5", "max_stanze_cinque","2","5+", "max_stanze_cinque_plus","2"));
            		break;
            		case FIVE:
                    newMessage.setReplyMarkup(telegramFunctions.addInlineKeyboardMarkup("Indifferente", "max_stanze_indifferente","1","5", "max_stanze_cinque","2","5+", "max_stanze_cinque_plus","2"));
                    break;
            		case FIVEPLUS:
                    newMessage.setReplyMarkup(telegramFunctions.addInlineKeyboardMarkup("Indifferente", "max_stanze_indifferente","1","5+", "max_stanze_cinque_plus","1"));
                    break;
            			
            	}
            }
            else if(update.getCallbackQuery().getData().startsWith("max_stanze")) {
            	userFilter.get(chatId).setMaxRooms(update.getCallbackQuery().getData().endsWith("indifferente") ? RoomsFilterEnum.INDIFFERENTE : update.getCallbackQuery().getData().endsWith("una") ? RoomsFilterEnum.ONE : update.getCallbackQuery().getData().endsWith("due") ? RoomsFilterEnum.TWO : update.getCallbackQuery().getData().endsWith("tre") ? RoomsFilterEnum.THREE : update.getCallbackQuery().getData().endsWith("quattro") ? RoomsFilterEnum.FOUR : update.getCallbackQuery().getData().endsWith("cinque") ? RoomsFilterEnum.FIVE : RoomsFilterEnum.FIVEPLUS);
            	newMessage = telegramFunctions.editMessageText(update, "Qual è la grandezza minima in metri quadrati? (sono consentiti valori fra 1 e 5000)\nDigita 0 se è indifferente");
            	selezioneMinSurface = true;
            }
            else if(update.getCallbackQuery().getData().startsWith("agenzia")) {
            	userFilter.get(chatId).setAgencyFilter(update.getCallbackQuery().getData().endsWith("indifferente") ? AgencyFilterEnum.INDIFFERENTE : update.getCallbackQuery().getData().endsWith("si") ? AgencyFilterEnum.AGENCY : AgencyFilterEnum.PRIVATE);
            	newMessage = telegramFunctions.editMessageText(update, "I filtri sono stati impostati correttamente!");
            }
            
             try {
                 execute(newMessage); 
             } catch (TelegramApiException e) {
                 e.printStackTrace();
             }
            
        }
		
	}

	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return "FlatFinderBot";
	}


}
