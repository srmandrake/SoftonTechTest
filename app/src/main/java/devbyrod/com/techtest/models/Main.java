package devbyrod.com.techtest.models;

import java.io.Serializable;

/**
 * Created by Mandrake on 4/26/16.
 */
public class Main implements Serializable {

    private float temp = 1.11f;
    private float presure = 2.22f;
    private float humidity = 3.33f;
    private float temp_min = 4.44f;
    private float temp_max = 5.55f;

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getPresure() {
        return presure;
    }

    public void setPresure(float presure) {
        this.presure = presure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(float temp_min) {
        this.temp_min = temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(float temp_max) {
        this.temp_max = temp_max;
    }
}
