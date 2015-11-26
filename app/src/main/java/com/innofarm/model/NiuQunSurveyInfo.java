package com.innofarm.model;

/**
 * Created by dell on 2015/11/12.
 */
public class NiuQunSurveyInfo {

    public String cattleType;
    public String count;
    public String present;

    public String getCattleType() {
        return cattleType;
    }

    public void setCattleType(String cattleType) {
        this.cattleType = cattleType;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    @Override
    public String toString() {
        return "NiuQunSurveyInfo{" +
                "cattleType='" + cattleType + '\'' +
                ", count='" + count + '\'' +
                ", present='" + present + '\'' +
                '}';
    }
}
