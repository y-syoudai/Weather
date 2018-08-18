package weather.action;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import weather.bean.ForecastBean;
import weather.bean.WeatherBean;
import weather.dao.DaoManager;
import weather.dao.ForecastDao;
import weather.dao.WeatherDao;
import weather.data.City;
import weather.data.WeatherInfo;
import weather.data.WeatherManager;

public class WeatherAction {
	private Logger logger = LogManager.getLogger(WeatherAction.class);
	private WeatherBean weatherBean;
	private List<City> cityList;

	public WeatherBean getWeatherBean() {
		return weatherBean;
	}

	public void setWeatherBean(WeatherBean weatherBean) {
		this.weatherBean = weatherBean;
	}

	public List<City> getCityList() {
		return cityList;
	}

	public String execute() {
		WeatherBean weatherBean = null;
		WeatherManager weatherManager = WeatherManager.getInstance();
		if (this.weatherBean != null) {
			if (weatherManager.isDBData(this.weatherBean.getCityId())) {
				weatherBean = this.getDBData();
			} else {
				weatherBean = this.getJsonData();
			}
		} else {
			weatherBean = new WeatherBean();
		}
		this.weatherBean = weatherBean;
		this.cityList = weatherManager.getCityList();
		return "success";
	}

	private WeatherBean getJsonData() {
		WeatherBean weatherBean = new WeatherBean();
		WeatherInfo weatherInfo = new WeatherInfo();
		weatherBean = weatherInfo.getWeatherInfo(this.weatherBean.getCityId());
		Connection connection = null;
		try {
			connection = DaoManager.getInstance().getConnection();
			WeatherDao weatherDao = new WeatherDao(connection);
			weatherDao.delete(weatherBean.getCityId());
			weatherDao.insert(weatherBean);
			ForecastDao forecastDao = new ForecastDao(connection);
			forecastDao.delete(weatherBean.getCityId());
			for (ForecastBean forecastBean : weatherBean.getForecasts()) {
				forecastDao.insert(forecastBean);
			}
			WeatherManager.getInstance().setWeatherDBManager(weatherBean);
		} catch (SQLException e) {
			logger.error("SQLエラー", e);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			logger.error("コネクション取得エラー", e);
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				logger.error("Connectionクローズエラー", e);
			}
		}
		weatherBean.setDebugData("JSONから取得");
		return weatherBean;
	}

	private WeatherBean getDBData() {
		WeatherBean weatherBean = new WeatherBean();
		Connection connection = null;
		try {
			connection = DaoManager.getInstance().getConnection();
			WeatherDao weatherDao = new WeatherDao(connection);
			weatherBean = weatherDao.selectByCityId(this.weatherBean.getCityId());
			ForecastDao forecastDao = new ForecastDao(connection);
			weatherBean.setForecasts(forecastDao.selectByCityId(this.weatherBean.getCityId()));
		} catch (SQLException e) {
			logger.error("SQLエラー", e);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			logger.error("コネクション取得エラー", e);
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				logger.error("Connectionクローズエラー", e);
			}
		}
		weatherBean.setDebugData("DBから取得");
		if (weatherBean.getCityId() == null || weatherBean.getForecasts().isEmpty()) {
			weatherBean = this.getJsonData();
		}
		return weatherBean;
	}
}
