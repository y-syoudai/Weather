package weather.bean;

public class ForecastBean {
	private String cityId;
	private String dateLabel;
	private String dateText;
	private String telop;
	private String minTemperature;
	private String maxTemperature;
	private String imageUrl;

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getDateLabel() {
		return dateLabel;
	}

	public void setDateLabel(String dateLabel) {
		this.dateLabel = dateLabel;
	}

	public String getDateText() {
		return dateText;
	}

	public void setDateText(String dateText) {
		this.dateText = dateText;
	}

	public String getTelop() {
		return telop;
	}

	public void setTelop(String telop) {
		this.telop = telop;
	}

	public String getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(String minTemperature) {
		this.minTemperature = minTemperature;
	}

	public String getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(String maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		StringBuffer strbuff = new StringBuffer();
		strbuff.append("・DateLabel=" + this.dateLabel + "\n");
		strbuff.append("・Date=" + this.dateText + "\n");
		strbuff.append("・Telop=" + this.telop + "\n");
		strbuff.append("・MinTemperature=" + this.minTemperature + "\n");
		strbuff.append("・MaxTemperature=" + this.maxTemperature + "\n");
		strbuff.append("・image=" + this.imageUrl + "\n");
		return strbuff.toString();
	}
}
