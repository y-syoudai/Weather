package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SendRequest {
	private Logger logger = LogManager.getLogger(SendRequest.class);

	public String send(String urlString) {
		String responseData = "";
		try {
			URL url = new URL(urlString);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("GET");
			http.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				responseData += line;
			}
			reader.close();
		} catch (IOException e) {
			logger.error("レスポンスエラー", e);
			return null;
		}
		return responseData;
	}
}
