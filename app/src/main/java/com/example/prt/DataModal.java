package com.example.prt;

public class DataModal {

    private String Name;
    private String Surname;
    private String Patronymic;
    private String Subject;

    public DataModal(String Name, String Surname,String Patronymic, String Subject ) {
        this.Name = Name;
        this.Surname = Surname;
        this.Patronymic = Patronymic;
        this.Subject = Subject;
    }

    public String getName() {
        return Name;
    }
    public String getSurname() {
        return Surname;
    }
    public String getPatronymic() {
        return Patronymic;
    }
    public String getSubject() {
        return Subject;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
    public void setSurname(String Surname) {
        this.Surname = Surname;
    }
    public void setPatronymic(String Patronymic) {
        this.Patronymic = Patronymic;
    }
    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

}
