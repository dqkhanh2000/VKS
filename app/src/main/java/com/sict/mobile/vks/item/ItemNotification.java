package com.sict.mobile.vks.item;

public class ItemNotification {
    private String NotificationName ;
    private  String NotificationTime ;
    private  String NotificationContent ;
    private  String NotificationLink ;

    public ItemNotification(String notificationName, String notificationTime, String notificationContent, String notificationLink) {
        NotificationName = notificationName;
        NotificationTime = notificationTime;
        NotificationContent = notificationContent;
        NotificationLink = notificationLink;
    }

    public String getNotificationName() {
        return NotificationName;
    }

    public String getNotificationTime() {
        return NotificationTime;
    }

    public String getNotificationContent() {
        return NotificationContent;
    }

    public String getNotificationLink() {
        return NotificationLink;
    }
}
