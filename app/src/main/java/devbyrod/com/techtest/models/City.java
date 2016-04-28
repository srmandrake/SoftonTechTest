package devbyrod.com.techtest.models;

import java.io.Serializable;

/**
 * Created by Mandrake on 4/26/16.
 */
public class City implements Serializable {

    private String name = "Test Name";
    private Main main;
    private Wind wind;
    private Weather[] weather;
//    private Main main = new Main();
//    private Wind wind = new Wind();
//    private Weather weather = new Weather();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Weather getWeather() {
        return weather[0];
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }
}
