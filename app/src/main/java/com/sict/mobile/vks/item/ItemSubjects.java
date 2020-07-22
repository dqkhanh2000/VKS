package com.sict.mobile.vks.item;

public class ItemSubjects {
    private String nameSubjects;
    private String timeSubjects;
    private String classSubjects;

    public ItemSubjects(String nameSubjects, String timeSubjects, String classSubjects) {
        this.nameSubjects = nameSubjects;
        this.timeSubjects = timeSubjects;
        this.classSubjects = classSubjects;
    }

    public String getNameSubjects() {
        return nameSubjects;
    }

    public String getTimeSubjects() {
        return timeSubjects;
    }

    public String getClassSubjects() {
        return classSubjects;
    }
}
