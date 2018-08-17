create table ysyoudaidb.weather (cityId char(6) not null primary key, cityName varchar(12), updateDate datetime);
create table ysyoudaidb.forecast (cityId char(6) not null, dateLabel char(3) not null, dateText varchar(10), telop varchar(4), minTemperature varchar(2), maxTemperature varchar(2), imageUrl varchar(43), primary key(cityId, dateLabel));
