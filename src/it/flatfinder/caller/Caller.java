package it.flatfinder.caller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;
import org.brotli.dec.BrotliInputStream;
import org.json.JSONObject;

public class Caller {
	

	public List<JSONObject> getData() throws IOException, InterruptedException {

		List<JSONObject> data = new LinkedList<JSONObject>();
		data.addAll(getImmobiliareData());
		data.addAll(getCasaData());

		return data;
	}

	public List<JSONObject> getImmobiliareData() throws IOException, InterruptedException {

		List<JSONObject> jsons = new LinkedList<JSONObject>();
		String url = "";
		int i = 19;
		String nextPage = "";

		while (!nextPage.equals("null")) {
			url = (i == 1) ? "https://www.immobiliare.it/affitto-case/verona-provincia/"
					: "https://www.immobiliare.it/affitto-case/verona-provincia/?pag=" + i;

			System.out.println(url);
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
			String response = client.send(request, BodyHandlers.ofString()).body();

			JSONObject json = new JSONObject(
					response.substring(response.indexOf("application/json") + 18, response.length() - 109));
			try {
				nextPage = (String) ((JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) json.get("props"))
						.get("pageProps")).get("atoms")).get("SeoDataAtom")).get("nextPage");
			} catch (Exception e) {
				nextPage = "null";
			}

			jsons.add(json);
			i++;

		}

		return jsons;
	}

	public List<JSONObject> getCasaData() throws IOException, InterruptedException {

		List<JSONObject> jsons = new LinkedList<JSONObject>();
		String url = "";
		int i = 14;
		String nextPage = "";

		while (!nextPage.equals("null")) {
			url = (i == 1) ? "https://www.casa.it/affitto/residenziale/verona-provincia/"
					: "https://www.casa.it/affitto/residenziale/verona-provincia?page=" + i;

			System.out.println(url);
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).headers("authority", "www.casa.it",
					"method", "GET", "path", "/affitto/residenziale/verona-provincia/", "scheme", "https", "accept",
					"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
					"accept-encoding", "gzip, deflate, br", "accept-language", "it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7",
					"cache-control", "max-age=0", "cookie",
					"_gcl_au=1.1.1265759278.1651607725; X-Casa-AB-PDPnw=false; OptanonAlertBoxClosed=2022-05-03T19:55:28.874Z; X-Casa-AB-Quality=A; AWSALB=QPyBnbXw8m+3KhZiG280mLFAH4T1Tbg/8iS+jAHqO1xG70unJ7YIOc9BcAUTOz3d1vSO+PrSqC3SKDvP2kvH+qSmPMuJ1D/RhmzV3UDzLRqQyiWvYYbR7YRk24dy; AWSALBCORS=QPyBnbXw8m+3KhZiG280mLFAH4T1Tbg/8iS+jAHqO1xG70unJ7YIOc9BcAUTOz3d1vSO+PrSqC3SKDvP2kvH+qSmPMuJ1D/RhmzV3UDzLRqQyiWvYYbR7YRk24dy; _gid=GA1.2.457821148.1653211871; eupubconsent-v2=CPYb1bUPYb1bpAcABBENCQCsAP_AAAAAAChQImtf_X__b3_j-_5_f_t0eY1P9_7_v-0zjhfdt-8N3f_X_L8X42M7vF36pq4KuR4Eu3LBIQdlHOHcTUmw6okVrzPsbk2cr7NKJ7PEmnMbO2dYGH9_n93TuZKY7_____7z_v-v_v____f_7-3f3__5_3---_e_V_99zLn9____39nP___9v-_9_____-AAAAcJAGABAADIAGgARABlALzFQAQF5jIAIC8x0AcAEAAMgAaABEADMAMoBeZCACAGYlADACIAGYBeZSAOACAAGQANAAiABmAGUAvM.f_gAAAAAAAAA; _ga_FNXRYZ9VWF=GS1.1.1653211870.3.1.1653215232.0; _ga=GA1.2.27344392.1651607726; OptanonConsent=isGpcEnabled=0&datestamp=Sun+May+22+2022+12%3A27%3A14+GMT%2B0200+(Ora+legale+dell%E2%80%99Europa+centrale)&version=6.31.0&isIABGlobal=false&hosts=&consentId=0fbcef0d-36cd-49c7-a6aa-f4def095f7e9&interactionCount=1&landingPath=NotLandingPage&groups=C0002%3A1%2CC0001%3A1%2CC0004%3A1%2CBG44%3A1&geolocation=IT%3B34&AwaitingReconsent=false; cto_bundle=Cq_d2F91YW9GMWQ5R3NCQm5hRUNOWktzNFJoMldZWkU2bUJWUWhQUFlsUnlNMlhvbERwV2Z3ampKd1NRTHc2ViUyQjFuZnBpa3hNJTJCcHI5OSUyQnlaTThCV3ZUeUNnWTlNbll3TTFNVGNSc25mMiUyQndIM1ZBRTF4ZnRlNmswbGkzbXdYWWF1bkxB; datadome=r.E.m9~gnXB4J9rJdpK0.ql7MFqd14IW8Qp-JBMnUATztuV6GZphbjKd~eoUAZd9oIi51_mCWy_3FE8OJ1r~ZBBxJeDnRvQHyXN4YAxCWi3k0uEfXE_fCV-cI5_L.hM",
					"dnt", "1", "if-none-match", "W/\"7b179-sqoZKZYG9YcgeSRYNHMmpfyH7Aw\"", "referer",
					"https://www.casa.it/affitto/residenziale/verona-provincia?page=2", "sec-ch-ua",
					"\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"101\", \"Google Chrome\";v=\"101\"",
					"sec-ch-ua-mobile", "?0", "sec-ch-ua-platform", "\"Windows\"", "sec-fetch-dest", "document",
					"sec-fetch-mode", "navigate", "sec-fetch-site", "same-origin", "sec-fetch-user", "?1",
					"upgrade-insecure-requests", "1", "user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.67 Safari/537.36")
					.build();

			HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

			BufferedReader reader = new BufferedReader(new InputStreamReader(new BrotliInputStream(response.body())));

			StringBuilder responseBuilder = new StringBuilder();

			while (true) {
				int ch = reader.read();
				if (ch == -1) {
					break;
				}
				responseBuilder.append((char) ch);
			}
			
			String responseString = responseBuilder.toString();

			responseString = responseString.substring(responseString.indexOf("JSON.parse(") + 12,
					responseString.indexOf("\");"));
			responseString = responseString.replace("\\\"", "\"");
			responseString = responseString.replace("\\\\", "\\");

			JSONObject json = new JSONObject(responseString);

			try {
				nextPage = ((String) ((JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) json.get("agencySrp"))
						.get("seoDataHeader")).get("urls")).get("pages")).get("next"));
			} catch (Exception e) {
				nextPage = "null";
			}

			jsons.add(json);
			
			i++;

		}

		return jsons;
	}

}
