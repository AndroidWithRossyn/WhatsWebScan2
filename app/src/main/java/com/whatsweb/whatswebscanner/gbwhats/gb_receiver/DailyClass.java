package com.whatsweb.whatswebscanner.gbwhats.gb_receiver;

public class DailyClass {
    private int hour = 0;
    private String message = "";
    private String typer = "";

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public String getTyper() {
        return this.typer;
    }

    public void setTyper(String str) {
        this.typer = str;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int i) {
        this.hour = i;
    }
}
