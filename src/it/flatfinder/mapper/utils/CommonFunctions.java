package it.flatfinder.mapper.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;


public class CommonFunctions {
	
	static public int StringToInt(String string) {
		
		string = string.trim();
		string = string.replace(".", "");
		
		int c = 0;
		
		for(int i = 0; i < string.length(); i++) {
			if(Character.isDigit(string.charAt(i))) {
				c++;
			}
		}
		
		if(c == 0)
			return 0;
		
		int stringInt = Integer.parseInt(string.substring(0, c));
		return stringInt;
	}

}
