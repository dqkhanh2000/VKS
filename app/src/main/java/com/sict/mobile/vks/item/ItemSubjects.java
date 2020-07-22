package com.sict.mobile.vks.item;

public class ItemSubjects {
    private String NameSubjects;
    private String timeSubjects;
    private String ClassSubjects;

    public ItemSubjects(String nameSubjects, String timeSubjects, String classSubjects) {
        NameSubjects = nameSubjects;
        this.timeSubjects = timeSubjects;
        ClassSubjects = classSubjects;
    }

    public String getNameSubjects() {
        return NameSubjects;
    }

    public String getTimeSubjects() {
        return timeSubjects;
    }

    public String getClassSubjects() {
        return ClassSubjects;
    }
}
