package weather.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import weather.bean.ForecastBean;

public class ForecastDao {
	private Connection connection;

	private static final String SQL_SELECT_CITY = "select * from forecast where cityId = ? order by dateText";
	private static final String SQL_DELETE_CITY = "delete from forecast where cityId = ?";
	private static final String SQL_INSERT = "insert into forecast values(?, ?, ?, ?, ?, ?, ?)";
	private static final String COLUMN_CITY_ID = "cityId";
	private static final String COLUMN_DATE_LABEL = "dateLabel";
	private static final String COLUMN_DATE_TEXT = "dateText";
	private static final String COLUMN_TELOP = "telop";
	private static final String COLUMN_MIN_TEMPERATURE = "minTemperature";
	private static final String COLUMN_MAX_TEMPERATURE = "maxTemperature";
	private static final String COLUMN_IMAGE_URL = "imageUrl";

	public ForecastDao(Connection connection) {
		this.connection = connection;
	}

	public List<ForecastBean> selectCity(String cityId) throws SQLException {
		PreparedStatement ps = this.connection.prepareStatement(SQL_SELECT_CITY);
		ps.setString(1, cityId);
		ResultSet rs = ps.executeQuery();
		List<ForecastBean> list = new ArrayList<ForecastBean>();
		while (rs.next()) {
			ForecastBean forecastBean = new ForecastBean();
			forecastBean.setCityId(rs.getString(COLUMN_CITY_ID));
			forecastBean.setDateLabel(rs.getString(COLUMN_DATE_LABEL));
			forecastBean.setDateText(rs.getString(COLUMN_DATE_TEXT));
			forecastBean.setTelop(rs.getString(COLUMN_TELOP));
			forecastBean.setMinTemperature(rs.getString(COLUMN_MIN_TEMPERATURE));
			forecastBean.setMaxTemperature(rs.getString(COLUMN_MAX_TEMPERATURE));
			forecastBean.setImageUrl(rs.getString(COLUMN_IMAGE_URL));
			list.add(forecastBean);
		}
		rs.close();
		ps.close();
		return list;
	}

	public int deleteCity(String cityId) throws SQLException {
		PreparedStatement ps = this.connection.prepareStatement(SQL_DELETE_CITY);
		ps.setString(1, cityId);
		int result = ps.executeUpdate();
		ps.close();
		return result;
	}

	public int insertCity(ForecastBean forecastBean) throws SQLException {
		PreparedStatement ps = this.connection.prepareStatement(SQL_INSERT);
		ps.setString(1, forecastBean.getCityId());
		ps.setString(2, forecastBean.getDateLabel());
		ps.setString(3, forecastBean.getDateText());
		ps.setString(4, forecastBean.getTelop());
		ps.setString(5, forecastBean.getMinTemperature());
		ps.setString(6, forecastBean.getMaxTemperature());
		ps.setString(7, forecastBean.getImageUrl());
		int result = ps.executeUpdate();
		ps.close();
		return result;
	}
}
