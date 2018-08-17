package weather.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import http.SendRequest;
import weather.bean.ForecastBean;
import weather.bean.WeatherBean;

public class WeatherInfo {
	private static String WEATHER_URL = "http://weather.livedoor.com/forecast/webservice/json/v1?city=";
	private Logger logger = LogManager.getLogger(WeatherInfo.class);

	public WeatherBean getWeatherInfo(String city) {
		if (!this.checkCity(city)) {
			return new WeatherBean();
		}
		SendRequest sendReq = new SendRequest();
		String response = sendReq.send(WEATHER_URL + city);
		if (response != null) {
			return this.parseWeatherInfo(response, city);
		} else {
			return new WeatherBean();
		}
	}

	private boolean checkCity(String city) {
		if (city == null || city.isEmpty()) {
			return false;
		}
		return true;
	}

	private WeatherBean parseWeatherInfo(String jsonData, String city) {
		WeatherBean weatherBean = new WeatherBean();
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(jsonData);
			weatherBean.setCityId(city);
			weatherBean.setCityName(node.get("title").asText());
			weatherBean.setForecasts(this.parseForecasts(node, city));
		} catch (IOException e) {
			logger.error("天気情報(JSON)解析エラー", e);
		}
		return weatherBean;
	}

	private List<ForecastBean> parseForecasts(JsonNode node, String city) {
		List<ForecastBean> forecastList = new ArrayList<ForecastBean>();
		Iterator<JsonNode> forecasts = node.get("forecasts").iterator();
		while (forecasts.hasNext()) {
			JsonNode forecastsNode = forecasts.next();
			forecastList.add(this.parseForecast(forecastsNode, city));
		}
		return forecastList;
	}

	private ForecastBean parseForecast(JsonNode forecastsNode, String city) {
		ForecastBean forecast = new ForecastBean();
		forecast.setCityId(city);
		forecast.setDateLabel(forecastsNode.get("dateLabel").asText());
		forecast.setTelop(forecastsNode.get("telop").asText());
		forecast.setDateText(forecastsNode.get("date").asText());
		forecast.setMinTemperature(this.parseTemperature(forecastsNode.get("temperature").get("min")));
		forecast.setMaxTemperature(this.parseTemperature(forecastsNode.get("temperature").get("max")));
		forecast.setImageUrl(forecastsNode.get("image").get("url").asText());
		return forecast;
	}

	private String parseTemperature(JsonNode temperatureNode) {
		if (temperatureNode.isNull()) {
			return null;
		}
		return temperatureNode.get("celsius").asText();
	}
}
