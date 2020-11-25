package com.muslimcharityapps.elbarak.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ssb on 13/3/17.
 */

public class WeatherBean {

    @SerializedName("coord")
    private Coord coord;
    @SerializedName("weather")
    private List<Weather> weather;
    @SerializedName("base")
    private String base;
    @SerializedName("main")
    private Main main;
    @SerializedName("visibility")
    private double visibility;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("clouds")
    private Clouds clouds;
    @SerializedName("dt")
    private double dt;
    @SerializedName("sys")
    private Sys sys;
    @SerializedName("id")
    private double id;
    @SerializedName("name")
    private String name;
    @SerializedName("cod")
    private double cod;

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCod() {
        return cod;
    }

    public void setCod(double cod) {
        this.cod = cod;
    }

    @Override
    public String toString() {
        return "WeatherBean{" +
                "coord=" + coord +
                ", weather=" + weather +
                ", base='" + base + '\'' +
                ", main=" + main +
                ", visibility=" + visibility +
                ", wind=" + wind +
                ", clouds=" + clouds +
                ", dt=" + dt +
                ", sys=" + sys +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", cod=" + cod +
                '}';
    }

    public class Coord {
        @SerializedName("lon")
        private double lon;
        @SerializedName("lat")
        private double lat;

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        @Override
        public String toString() {
            return "Coord{" +
                    "lon=" + lon +
                    ", lat=" + lat +
                    '}';
        }
    }

    public class Weather {
        @SerializedName("id")
        private double id;
        @SerializedName("main")
        private String main;
        @SerializedName("description")
        private String description;
        @SerializedName("icon")
        private String icon;

        @Override
        public String toString() {
            return "Weather{" +
                    "id=" + id +
                    ", main='" + main + '\'' +
                    ", description='" + description + '\'' +
                    ", icon='" + icon + '\'' +
                    '}';
        }

        public double getId() {
            return id;
        }

        public void setId(double id) {
            this.id = id;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public class Main {
        @SerializedName("temp")
        private double temp;
        @SerializedName("pressure")
        private double pressure;
        @SerializedName("humidity")
        private double humidity;
        @SerializedName("temp_min")
        private double tempMin;
        @SerializedName("temp_max")
        private double tempMax;

        @Override
        public String toString() {
            return "Main{" +
                    "temp=" + temp +
                    ", pressure=" + pressure +
                    ", humidity=" + humidity +
                    ", tempMin=" + tempMin +
                    ", tempMax=" + tempMax +
                    '}';
        }

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public double getPressure() {
            return pressure;
        }

        public void setPressure(double pressure) {
            this.pressure = pressure;
        }

        public double getHumidity() {
            return humidity;
        }

        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }

        public double getTempMin() {
            return tempMin;
        }

        public void setTempMin(double tempMin) {
            this.tempMin = tempMin;
        }

        public double getTempMax() {
            return tempMax;
        }

        public void setTempMax(double tempMax) {
            this.tempMax = tempMax;
        }
    }

    public class Wind {
        @SerializedName("speed")
        private double speed;
        @SerializedName("deg")
        private double deg;

        @Override
        public String toString() {
            return "Wind{" +
                    "speed=" + speed +
                    ", deg=" + deg +
                    '}';
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public double getDeg() {
            return deg;
        }

        public void setDeg(double deg) {
            this.deg = deg;
        }
    }

    public class Clouds {
        @SerializedName("all")
        private double all;

        @Override
        public String toString() {
            return "Clouds{" +
                    "all=" + all +
                    '}';
        }

        public double getAll() {
            return all;
        }

        public void setAll(double all) {
            this.all = all;
        }
    }

    public class Sys {
        @SerializedName("type")
        private double type;
        @SerializedName("id")
        private double id;
        @SerializedName("message")
        private double message;
        @SerializedName("country")
        private String country;
        @SerializedName("sunrise")
        private double sunrise;
        @SerializedName("sunset")
        private double sunset;

        @Override
        public String toString() {
            return "Sys{" +
                    "type=" + type +
                    ", id=" + id +
                    ", message=" + message +
                    ", country='" + country + '\'' +
                    ", sunrise=" + sunrise +
                    ", sunset=" + sunset +
                    '}';
        }

        public double getType() {
            return type;
        }

        public void setType(double type) {
            this.type = type;
        }

        public double getId() {
            return id;
        }

        public void setId(double id) {
            this.id = id;
        }

        public double getMessage() {
            return message;
        }

        public void setMessage(double message) {
            this.message = message;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public double getSunrise() {
            return sunrise;
        }

        public void setSunrise(double sunrise) {
            this.sunrise = sunrise;
        }

        public double getSunset() {
            return sunset;
        }

        public void setSunset(double sunset) {
            this.sunset = sunset;
        }
    }
}
