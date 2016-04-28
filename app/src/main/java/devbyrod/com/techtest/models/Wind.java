package devbyrod.com.techtest.models;

import java.io.Serializable;

/**
 * Created by Mandrake on 4/26/16.
 */
public class Wind implements Serializable {

    private float speed = 6.66f;
    private float deg = 7.77f;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDeg() {
        return deg;
    }

    public void setDeg(float deg) {
        this.deg = deg;
    }
}
