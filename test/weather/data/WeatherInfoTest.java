package weather.data;

import org.junit.Test;

import weather.bean.WeatherBean;
import weather.data.WeatherInfo;

public class WeatherInfoTest {

	@Test
	public void testGetWeatherInfo() {
		WeatherInfo weatherInfo = new WeatherInfo();
		WeatherBean weatherBean = weatherInfo.getWeatherInfo("474020");
		System.out.println(weatherBean.toString());
	}
}
