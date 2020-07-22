package com.sict.mobile.vks.item;

public class ItemNotificationHome {
    private String NameNotification ;
    private  String TimeNotification ;

    public ItemNotificationHome(String nameNotification, String timeNotification) {
        NameNotification = nameNotification;
        TimeNotification = timeNotification;
    }

    public String getNameNotification() {
        return NameNotification;
    }

    public String getTimeNotification() {
        return TimeNotification;
    }
}
