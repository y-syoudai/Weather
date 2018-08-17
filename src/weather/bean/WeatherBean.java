package weather.bean;

import java.util.Date;
import java.util.List;

public class WeatherBean {
	private String cityId;
	private String cityName;
	private Date updateDate;
	private List<ForecastBean> forecasts;
	private String debugData;

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date date) {
		this.updateDate = date;
	}

	public List<ForecastBean> getForecasts() {
		return forecasts;
	}

	public void setForecasts(List<ForecastBean> forecasts) {
		this.forecasts = forecasts;
	}

	public String getDebugData() {
		return debugData;
	}

	public void setDebugData(String debugData) {
		this.debugData = debugData;
	}

	@Override
	public String toString() {
		StringBuffer strbuff = new StringBuffer();
		strbuff.append("■WeatherData\n");
		strbuff.append("□Title=" + this.cityName + "\n");
		if (this.forecasts != null) {
			strbuff.append("□Forecasts\n" + this.forecasts.toString());
		} else {
			strbuff.append("□Forecasts=null\n");
		}
		return strbuff.toString();
	}
}
