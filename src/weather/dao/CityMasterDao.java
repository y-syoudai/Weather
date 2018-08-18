package weather.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import weather.data.City;

public class CityMasterDao {
	private Connection connection;

	private static final String SQL_SELECT_ALL = "select * from city_master order by cityId";
	private static final String COLUMN_CITY_ID = "cityId";
	private static final String COLUMN_CITY_NAME = "cityName";

	public CityMasterDao(Connection connection) {
		this.connection = connection;
	}

	public List<City> selectAll() throws SQLException {
		PreparedStatement ps = this.connection.prepareStatement(SQL_SELECT_ALL);
		ResultSet rs = ps.executeQuery();
		List<City> list = new ArrayList<City>();
		while (rs.next()) {
			City city = new City(rs.getString(COLUMN_CITY_ID), rs.getString(COLUMN_CITY_NAME));
			list.add(city);
		}
		rs.close();
		ps.close();
		return list;
	}
}
