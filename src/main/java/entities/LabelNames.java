
package entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LabelNames {

    @SerializedName("green")
    @Expose
    private String green;
    @SerializedName("yellow")
    @Expose
    private String yellow;
    @SerializedName("orange")
    @Expose
    private String orange;
    @SerializedName("red")
    @Expose
    private String red;
    @SerializedName("purple")
    @Expose
    private String purple;
    @SerializedName("blue")
    @Expose
    private String blue;
    @SerializedName("sky")
    @Expose
    private String sky;
    @SerializedName("lime")
    @Expose
    private String lime;
    @SerializedName("pink")
    @Expose
    private String pink;
    @SerializedName("black")
    @Expose
    private String black;

    public String getGreen() {
        return green;
    }

    public void setGreen(String green) {
        this.green = green;
    }

    public String getYellow() {
        return yellow;
    }

    public void setYellow(String yellow) {
        this.yellow = yellow;
    }

    public String getOrange() {
        return orange;
    }

    public void setOrange(String orange) {
        this.orange = orange;
    }

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }

    public String getPurple() {
        return purple;
    }

    public void setPurple(String purple) {
        this.purple = purple;
    }

    public String getBlue() {
        return blue;
    }

    public void setBlue(String blue) {
        this.blue = blue;
    }

    public String getSky() {
        return sky;
    }

    public void setSky(String sky) {
        this.sky = sky;
    }

    public String getLime() {
        return lime;
    }

    public void setLime(String lime) {
        this.lime = lime;
    }

    public String getPink() {
        return pink;
    }

    public void setPink(String pink) {
        this.pink = pink;
    }

    public String getBlack() {
        return black;
    }

    public void setBlack(String black) {
        this.black = black;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(LabelNames.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("green");
        sb.append('=');
        sb.append(((this.green == null)?"<null>":this.green));
        sb.append(',');
        sb.append("yellow");
        sb.append('=');
        sb.append(((this.yellow == null)?"<null>":this.yellow));
        sb.append(',');
        sb.append("orange");
        sb.append('=');
        sb.append(((this.orange == null)?"<null>":this.orange));
        sb.append(',');
        sb.append("red");
        sb.append('=');
        sb.append(((this.red == null)?"<null>":this.red));
        sb.append(',');
        sb.append("purple");
        sb.append('=');
        sb.append(((this.purple == null)?"<null>":this.purple));
        sb.append(',');
        sb.append("blue");
        sb.append('=');
        sb.append(((this.blue == null)?"<null>":this.blue));
        sb.append(',');
        sb.append("sky");
        sb.append('=');
        sb.append(((this.sky == null)?"<null>":this.sky));
        sb.append(',');
        sb.append("lime");
        sb.append('=');
        sb.append(((this.lime == null)?"<null>":this.lime));
        sb.append(',');
        sb.append("pink");
        sb.append('=');
        sb.append(((this.pink == null)?"<null>":this.pink));
        sb.append(',');
        sb.append("black");
        sb.append('=');
        sb.append(((this.black == null)?"<null>":this.black));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.orange == null)? 0 :this.orange.hashCode()));
        result = ((result* 31)+((this.red == null)? 0 :this.red.hashCode()));
        result = ((result* 31)+((this.sky == null)? 0 :this.sky.hashCode()));
        result = ((result* 31)+((this.pink == null)? 0 :this.pink.hashCode()));
        result = ((result* 31)+((this.green == null)? 0 :this.green.hashCode()));
        result = ((result* 31)+((this.blue == null)? 0 :this.blue.hashCode()));
        result = ((result* 31)+((this.lime == null)? 0 :this.lime.hashCode()));
        result = ((result* 31)+((this.yellow == null)? 0 :this.yellow.hashCode()));
        result = ((result* 31)+((this.black == null)? 0 :this.black.hashCode()));
        result = ((result* 31)+((this.purple == null)? 0 :this.purple.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof LabelNames) == false) {
            return false;
        }
        LabelNames rhs = ((LabelNames) other);
        return (((((((((((this.orange == rhs.orange)||((this.orange!= null)&&this.orange.equals(rhs.orange)))&&((this.red == rhs.red)||((this.red!= null)&&this.red.equals(rhs.red))))&&((this.sky == rhs.sky)||((this.sky!= null)&&this.sky.equals(rhs.sky))))&&((this.pink == rhs.pink)||((this.pink!= null)&&this.pink.equals(rhs.pink))))&&((this.green == rhs.green)||((this.green!= null)&&this.green.equals(rhs.green))))&&((this.blue == rhs.blue)||((this.blue!= null)&&this.blue.equals(rhs.blue))))&&((this.lime == rhs.lime)||((this.lime!= null)&&this.lime.equals(rhs.lime))))&&((this.yellow == rhs.yellow)||((this.yellow!= null)&&this.yellow.equals(rhs.yellow))))&&((this.black == rhs.black)||((this.black!= null)&&this.black.equals(rhs.black))))&&((this.purple == rhs.purple)||((this.purple!= null)&&this.purple.equals(rhs.purple))));
    }

}
