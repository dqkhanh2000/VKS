package com.sict.mobile.vks.item;

public class ItemTodo {
    private  String NameTodo;
    private  String Timetodo;

    public ItemTodo(String nameTodo, String timetodo) {
        NameTodo = nameTodo;
        Timetodo = timetodo;
    }

    public String getNameTodo() {
        return NameTodo;
    }

    public String getTimetodo() {
        return Timetodo;
    }
}
