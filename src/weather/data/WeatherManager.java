package weather.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import weather.bean.WeatherBean;
import weather.dao.WeatherDao;

public class WeatherManager {
	private Logger logger = LogManager.getLogger(WeatherManager.class);
	private static WeatherManager weatherManager = new WeatherManager();
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URI = "jdbc:mysql://mysqldb.caemhlpposuy.ap-northeast-1.rds.amazonaws.com:3306/ysyoudaidb";
	private static final String DBUSER = "ysyoudai";
	private static final String PASSWORD = "Yuki6102";

	private List<City> cityList;
	private Map<String, Date> weatherDBManager;

	private WeatherManager() {
		this.createCityList();
		this.createWeatherDBManager();
	}

	public static WeatherManager getInstance() {
		return weatherManager;
	}

	private void createCityList() {
		List<City> cityList = new ArrayList<City>();
		cityList.add(new City("011000", "北海道 道北 稚内"));
		cityList.add(new City("012010", "北海道 道北 旭川"));
		cityList.add(new City("012020", "北海道 道北 留萌"));
		cityList.add(new City("013010", "北海道 道東 網走"));
		cityList.add(new City("013020", "北海道 道東 北見"));
		cityList.add(new City("013030", "北海道 道東 紋別"));
		cityList.add(new City("014010", "北海道 道東 根室"));
		cityList.add(new City("014020", "北海道 道東 釧路"));
		cityList.add(new City("014030", "北海道 道東 帯広"));
		cityList.add(new City("015010", "北海道 道南 室蘭"));
		cityList.add(new City("015020", "北海道 道南 浦河"));
		cityList.add(new City("016010", "北海道 道央 札幌"));
		cityList.add(new City("016020", "北海道 道央 岩見沢"));
		cityList.add(new City("016030", "北海道 道央 倶知安"));
		cityList.add(new City("017010", "北海道 道南 函館"));
		cityList.add(new City("017020", "北海道 道南 江差"));
		cityList.add(new City("020010", "青森県 青森"));
		cityList.add(new City("020020", "青森県 むつ"));
		cityList.add(new City("020030", "青森県 八戸"));
		cityList.add(new City("030010", "岩手県 盛岡"));
		cityList.add(new City("030020", "岩手県 宮古"));
		cityList.add(new City("030030", "岩手県 大船渡"));
		cityList.add(new City("040010", "宮城県 仙台"));
		cityList.add(new City("040020", "宮城県 白石"));
		cityList.add(new City("050010", "秋田県 秋田"));
		cityList.add(new City("050020", "秋田県 横手"));
		cityList.add(new City("060010", "山形県 山形"));
		cityList.add(new City("060020", "山形県 米沢"));
		cityList.add(new City("060030", "山形県 酒田"));
		cityList.add(new City("060040", "山形県 新庄"));
		cityList.add(new City("070010", "福島県 福島"));
		cityList.add(new City("070020", "福島県 小名浜"));
		cityList.add(new City("070030", "福島県 若松"));
		cityList.add(new City("080010", "茨城県 水戸"));
		cityList.add(new City("080020", "茨城県 土浦"));
		cityList.add(new City("090010", "栃木県 宇都宮"));
		cityList.add(new City("090020", "栃木県 大田原"));
		cityList.add(new City("100010", "群馬県 前橋"));
		cityList.add(new City("100020", "群馬県 みなかみ"));
		cityList.add(new City("110010", "埼玉県 さいたま"));
		cityList.add(new City("110020", "埼玉県 熊谷"));
		cityList.add(new City("110030", "埼玉県 秩父"));
		cityList.add(new City("120010", "千葉県 千葉"));
		cityList.add(new City("120020", "千葉県 銚子"));
		cityList.add(new City("120030", "千葉県 館山"));
		cityList.add(new City("130010", "東京都 東京"));
		cityList.add(new City("130020", "東京都 大島"));
		cityList.add(new City("130030", "東京都 八丈島"));
		cityList.add(new City("130040", "東京都 父島"));
		cityList.add(new City("140010", "神奈川県 横浜"));
		cityList.add(new City("140020", "神奈川県 小田原"));
		cityList.add(new City("150010", "新潟県 新潟"));
		cityList.add(new City("150020", "新潟県 長岡"));
		cityList.add(new City("150030", "新潟県 高田"));
		cityList.add(new City("150040", "新潟県 相川"));
		cityList.add(new City("160010", "富山県 富山"));
		cityList.add(new City("160020", "富山県 伏木"));
		cityList.add(new City("170010", "石川県 金沢"));
		cityList.add(new City("170020", "石川県 輪島"));
		cityList.add(new City("180010", "福井県 福井"));
		cityList.add(new City("180020", "福井県 敦賀"));
		cityList.add(new City("190010", "山梨県 甲府"));
		cityList.add(new City("190020", "山梨県 河口湖"));
		cityList.add(new City("200010", "長野県 長野"));
		cityList.add(new City("200020", "長野県 松本"));
		cityList.add(new City("200030", "長野県 飯田"));
		cityList.add(new City("210010", "岐阜県 岐阜"));
		cityList.add(new City("210020", "岐阜県 高山"));
		cityList.add(new City("220010", "静岡県 静岡"));
		cityList.add(new City("220020", "静岡県 網代"));
		cityList.add(new City("220030", "静岡県 三島"));
		cityList.add(new City("220040", "静岡県 浜松"));
		cityList.add(new City("230010", "愛知県 名古屋"));
		cityList.add(new City("230020", "愛知県 豊橋"));
		cityList.add(new City("240010", "三重県 津"));
		cityList.add(new City("240020", "三重県 尾鷲"));
		cityList.add(new City("250010", "滋賀県 大津"));
		cityList.add(new City("250020", "滋賀県 彦根"));
		cityList.add(new City("260010", "京都府 京都"));
		cityList.add(new City("260020", "京都府 舞鶴"));
		cityList.add(new City("270000", "大阪府 大阪"));
		cityList.add(new City("280010", "兵庫県 神戸"));
		cityList.add(new City("280020", "兵庫県 豊岡"));
		cityList.add(new City("290010", "奈良県 奈良"));
		cityList.add(new City("290020", "奈良県 風屋"));
		cityList.add(new City("300010", "和歌山県 和歌山"));
		cityList.add(new City("300020", "和歌山県 潮岬"));
		cityList.add(new City("310010", "鳥取県 鳥取"));
		cityList.add(new City("310020", "鳥取県 米子"));
		cityList.add(new City("320010", "島根県 松江"));
		cityList.add(new City("320020", "島根県 浜田"));
		cityList.add(new City("320030", "島根県 西郷"));
		cityList.add(new City("330010", "岡山県 岡山"));
		cityList.add(new City("330020", "岡山県 津山"));
		cityList.add(new City("340010", "広島県 広島"));
		cityList.add(new City("340020", "広島県 庄原"));
		cityList.add(new City("350010", "山口県 下関"));
		cityList.add(new City("350020", "山口県 山口"));
		cityList.add(new City("350030", "山口県 柳井"));
		cityList.add(new City("350040", "山口県 萩"));
		cityList.add(new City("360010", "徳島県 徳島"));
		cityList.add(new City("360020", "徳島県 日和佐"));
		cityList.add(new City("370000", "香川県 高松"));
		cityList.add(new City("380010", "愛媛県 松山"));
		cityList.add(new City("380020", "愛媛県 新居浜"));
		cityList.add(new City("380030", "愛媛県 宇和島"));
		cityList.add(new City("390010", "高知県 高知"));
		cityList.add(new City("390020", "高知県 室戸岬"));
		cityList.add(new City("390030", "高知県 清水"));
		cityList.add(new City("400010", "福岡県 福岡"));
		cityList.add(new City("400020", "福岡県 八幡"));
		cityList.add(new City("400030", "福岡県 飯塚"));
		cityList.add(new City("400040", "福岡県 久留米"));
		cityList.add(new City("410010", "佐賀県 佐賀"));
		cityList.add(new City("410020", "佐賀県 伊万里"));
		cityList.add(new City("420010", "長崎県 長崎"));
		cityList.add(new City("420020", "長崎県 佐世保"));
		cityList.add(new City("420030", "長崎県 厳原"));
		cityList.add(new City("420040", "長崎県 福江"));
		cityList.add(new City("430010", "熊本県 熊本"));
		cityList.add(new City("430020", "熊本県 阿蘇乙姫"));
		cityList.add(new City("430030", "熊本県 牛深"));
		cityList.add(new City("430040", "熊本県 人吉"));
		cityList.add(new City("440010", "大分県 大分"));
		cityList.add(new City("440020", "大分県 中津"));
		cityList.add(new City("440030", "大分県 日田"));
		cityList.add(new City("440040", "大分県 佐伯"));
		cityList.add(new City("450010", "宮崎県 宮崎"));
		cityList.add(new City("450020", "宮崎県 延岡"));
		cityList.add(new City("450030", "宮崎県 都城"));
		cityList.add(new City("450040", "宮崎県 高千穂"));
		cityList.add(new City("460010", "鹿児島県 鹿児島"));
		cityList.add(new City("460020", "鹿児島県 鹿屋"));
		cityList.add(new City("460030", "鹿児島県 種子島"));
		cityList.add(new City("460040", "鹿児島県 名瀬"));
		cityList.add(new City("471010", "沖縄県 那覇"));
		cityList.add(new City("471020", "沖縄県 名護"));
		cityList.add(new City("471030", "沖縄県 久米島"));
		cityList.add(new City("472000", "沖縄県 南大東"));
		cityList.add(new City("473000", "沖縄県 宮古島"));
		cityList.add(new City("474010", "沖縄県 石垣島"));
		cityList.add(new City("474020", "沖縄県 与那国島"));
		this.cityList = cityList;
	}

	private void createWeatherDBManager() {
		Connection connection = null;
		try {
			connection = this.getConnection();
			WeatherDao weatherDao = new WeatherDao(connection);
			List<WeatherBean> weatherList = weatherDao.selectCityList();
			this.weatherDBManager = new HashMap<String, Date>();
			for(WeatherBean weatherBean : weatherList) {
				this.weatherDBManager.put(weatherBean.getCityId(), weatherBean.getUpdateDate());
			}
		} catch (SQLException e) {
			logger.error("SQLエラー", e);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			logger.error("コネクション取得エラー", e);
		} finally {
			try {
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				logger.error("Connectionクローズエラー", e);
			}
		}

	}

	public Connection getConnection() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class.forName(DRIVER).newInstance();
		Connection connection = DriverManager.getConnection(URI, DBUSER, PASSWORD);
		return connection;
	}

	public List<City> getCityList() {
		return cityList;
	}

	public boolean isDBData(String cityId) {
		Date updateDate = weatherDBManager.get(cityId);
		if(updateDate == null) {
			return false;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String strNowDate = simpleDateFormat.format(new Date());
		String strUpdateDate = simpleDateFormat.format(updateDate);
		if(strUpdateDate.equals(strNowDate)) {
			return true;
		}
		return false;
	}

	public void setWeatherDBManager(WeatherBean weatherBean) {
		this.weatherDBManager.put(weatherBean.getCityId(), weatherBean.getUpdateDate());
	}
}