package com.holynekk.kafka.broker.message;

public class WebDesignVoteMessage {

    private String color;
    private String layout;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    @Override
    public String toString() {
        return "WebDesignVoteMessage{" +
                "color='" + color + '\'' +
                ", layout='" + layout + '\'' +
                '}';
    }
}
