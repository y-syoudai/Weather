package weather.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoManager {
	private static DaoManager daoManager = new DaoManager();

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URI = "jdbc:mysql://weather.caemhlpposuy.ap-northeast-1.rds.amazonaws.com:3306/weatherdb";
	private static final String DBUSER = "weatherUser";
	private static final String PASSWORD = "weather1234";

	private DaoManager() {
	}

	public static DaoManager getInstance() {
		return daoManager;
	}

	public Connection getConnection()
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName(DRIVER).newInstance();
		Connection connection = DriverManager.getConnection(URI, DBUSER, PASSWORD);
		return connection;
	}
}
