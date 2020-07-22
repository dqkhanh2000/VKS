package com.sict.mobile.vks.item;

public class ItemToday {
    private String subjectTitle;
    private  String teacher;
    private  String className;
    private  String lesson ;

    public ItemToday(String subjectTitle, String teacher, String className, String lesson) {
        this.subjectTitle = subjectTitle;
        this.teacher = teacher;
        this.className = className;
        this.lesson = lesson;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getClassName() {
        return className;
    }

    public String getLesson() {
        return lesson;
    }
}
