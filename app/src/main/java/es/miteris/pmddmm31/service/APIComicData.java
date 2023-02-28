package es.miteris.pmddmm31.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIComicData {
    @SerializedName("num")
    @Expose
    private String num;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("img")
    @Expose
    private String img;

    public APIComicData(String num, String title, String day, String month, String year, String img) {
        this.num = num;
        this.title = title;
        this.day = day;
        this.month = month;
        this.year = year;
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
