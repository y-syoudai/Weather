package weather.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import weather.bean.WeatherBean;

public class WeatherDao {
	private Connection connection;

	private static final String SQL_SELECT_CITY_ID = "select * from weather where cityId = ?";
	private static final String SQL_SELECT_ALL = "select * from weather";
	private static final String SQL_DELETE = "delete from weather where cityId = ?";
	private static final String SQL_INSERT = "insert into weather values(?, ?, ?)";
	private static final String COLUMN_CITY_ID = "cityId";
	private static final String COLUMN_CITY_NAME = "cityName";
	private static final String COLUMN_UPDATE_DATE = "updateDate";

	public WeatherDao(Connection connection) {
		this.connection = connection;
	}

	public WeatherBean selectByCityId(String cityId) throws SQLException {
		PreparedStatement ps = this.connection.prepareStatement(SQL_SELECT_CITY_ID);
		ps.setString(1, cityId);
		ResultSet rs = ps.executeQuery();
		WeatherBean weatherBean = new WeatherBean();
		if (rs.next()) {
			weatherBean.setCityId(rs.getString(COLUMN_CITY_ID));
			weatherBean.setCityName(rs.getString(COLUMN_CITY_NAME));
			weatherBean.setUpdateDate(rs.getDate(COLUMN_UPDATE_DATE));
		}
		rs.close();
		ps.close();
		return weatherBean;
	}

	public List<WeatherBean> selectAll() throws SQLException {
		PreparedStatement ps = this.connection.prepareStatement(SQL_SELECT_ALL);
		ResultSet rs = ps.executeQuery();
		List<WeatherBean> list = new ArrayList<WeatherBean>();
		while (rs.next()) {
			WeatherBean weatherBean = new WeatherBean();
			weatherBean.setCityId(rs.getString(COLUMN_CITY_ID));
			weatherBean.setCityName(rs.getString(COLUMN_CITY_NAME));
			weatherBean.setUpdateDate(rs.getDate(COLUMN_UPDATE_DATE));
			list.add(weatherBean);
		}
		rs.close();
		ps.close();
		return list;
	}

	public int delete(String cityId) throws SQLException {
		PreparedStatement ps = this.connection.prepareStatement(SQL_DELETE);
		ps.setString(1, cityId);
		int result = ps.executeUpdate();
		ps.close();
		return result;
	}

	public int insert(WeatherBean weatherBean) throws SQLException {
		PreparedStatement ps = this.connection.prepareStatement(SQL_INSERT);
		ps.setString(1, weatherBean.getCityId());
		ps.setString(2, weatherBean.getCityName());
		weatherBean.setUpdateDate(new Date());
		ps.setDate(3, new java.sql.Date(weatherBean.getUpdateDate().getTime()));
		int result = ps.executeUpdate();
		ps.close();
		return result;
	}
}
