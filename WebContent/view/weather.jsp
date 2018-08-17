<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Weather</title>
</head>
<body>
	<p>
		<s:form action="Weather">
			<s:select list="cityList" listKey="id" listValue="name" headerValue="地域を選択" headerKey="" name="weatherBean.cityId" value="weatherBean.cityId"/>
			<s:submit value="天気情報取得" />
		</s:form>
	</p>
	<s:if test="weatherBean.cityName != null">
		<s:property value="weatherBean.cityName" />
		<br>
		<s:iterator value="weatherBean.forecasts">
			<p>
				<s:property value="dateLabel" />
				<s:property value="dateText" />
				<br>
				<img src="<s:property value="imageUrl"/>" />
				<s:property value="telop" />
				<br>
				最低気温：
				<s:if test="minTemperature != null">
					<s:property value="minTemperature" />℃
				</s:if>
				<s:else>
					－
				</s:else>
				<br>
				最高気温：
				<s:if test="maxTemperature != null">
					<s:property value="maxTemperature" />℃
				</s:if>
				<s:else>
					－
				</s:else>
			</p>
		</s:iterator>
		<s:property value="weatherBean.debugData" />
	</s:if>
</body>
</html>