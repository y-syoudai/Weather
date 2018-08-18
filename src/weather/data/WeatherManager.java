package weather.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import weather.bean.WeatherBean;
import weather.dao.CityMasterDao;
import weather.dao.DaoManager;
import weather.dao.WeatherDao;

public class WeatherManager {
	private Logger logger = LogManager.getLogger(WeatherManager.class);
	private static WeatherManager weatherManager = new WeatherManager();

	private List<City> cityList;
	private Map<String, Date> weatherDBManager;

	private WeatherManager() {
		this.createCityList();
		this.createWeatherDBManager();
	}

	public static WeatherManager getInstance() {
		return weatherManager;
	}

	public List<City> getCityList() {
		return cityList;
	}

	private void createCityList() {
		Connection connection = null;
		try {
			connection = DaoManager.getInstance().getConnection();
			CityMasterDao cityMasterDao = new CityMasterDao(connection);
			this.cityList = cityMasterDao.selectAll();
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
	}

	private void createWeatherDBManager() {
		Connection connection = null;
		try {
			connection = DaoManager.getInstance().getConnection();
			WeatherDao weatherDao = new WeatherDao(connection);
			List<WeatherBean> weatherList = weatherDao.selectAll();
			this.weatherDBManager = new HashMap<String, Date>();
			for (WeatherBean weatherBean : weatherList) {
				this.weatherDBManager.put(weatherBean.getCityId(), weatherBean.getUpdateDate());
			}
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
	}

	public boolean isDBData(String cityId) {
		Date updateDate = weatherDBManager.get(cityId);
		if (updateDate == null) {
			return false;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String strNowDate = simpleDateFormat.format(new Date());
		String strUpdateDate = simpleDateFormat.format(updateDate);
		if (strUpdateDate.equals(strNowDate)) {
			return true;
		}
		return false;
	}

	public void setWeatherDBManager(WeatherBean weatherBean) {
		this.weatherDBManager.put(weatherBean.getCityId(), weatherBean.getUpdateDate());
	}
}