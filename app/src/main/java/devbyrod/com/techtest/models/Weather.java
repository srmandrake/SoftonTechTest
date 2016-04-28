package devbyrod.com.techtest.models;

import java.io.Serializable;

/**
 * Created by Mandrake on 4/26/16.
 */
public class Weather implements Serializable {

    private String main = "Clouds";
    private String description = "Broken Clouds";
    private String icon = "icon";

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
