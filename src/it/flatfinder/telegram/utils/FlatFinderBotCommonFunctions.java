package it.flatfinder.telegram.utils;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class FlatFinderBotCommonFunctions {
	
	public SendMessage sendMessage(Long chatId, String text) {
		
        SendMessage message = new SendMessage(); 
		
		message.setChatId(chatId.toString());
    	message.setText(text);
    	
    	return message;
        
	}
	
	public InlineKeyboardMarkup addInlineKeyboardMarkup(String... params) {
		
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        
        int c = 0;
        
        while(c < params.length) {
        
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            int currLine = Integer.parseInt(params[c+2]);
            
	        while(c+2 < params.length && currLine == Integer.parseInt(params[c+2])) {
		        
		        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
		        inlineKeyboardButton.setText(params[c]);
		        inlineKeyboardButton.setCallbackData(params[c+1]);
		        rowInline.add(inlineKeyboardButton);
		        
		        c=c+3;
		        
	        }
	        // Set the keyboard to the markup
	        rowsInline.add(rowInline);
	        
		}
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        
        return markupInline;
	}
	
	public EditMessageText editMessageText(Update update, String text) {
		
        EditMessageText new_message = new EditMessageText();
        new_message.setChatId(Long.toString(update.getCallbackQuery().getMessage().getChatId()));
        new_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        new_message.setText(text);
        
        return new_message;
		
	}

}
