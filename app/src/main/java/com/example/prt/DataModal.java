package com.example.prt;

public class DataModal {

    private Integer Kod_teacher;
    private String Name;
    private String Surname;
    private String Patronymic;
    private String Subject;
    private String Images;

    public DataModal(Integer Kod_teacher, String Name, String Surname,String Patronymic, String Subject,  String Images ) {
        this.Kod_teacher = Kod_teacher;
        this.Name = Name;
        this.Surname = Surname;
        this.Patronymic = Patronymic;
        this.Subject = Subject;
        this.Images = Images;
    }

    public Integer getKod_teacher() {
        return Kod_teacher;
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
    public String getImages() {
        return Images;
    }

    public void setKod_teacher(Integer Kod_teacher) {
        this.Kod_teacher = Kod_teacher;
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
    public void setImages(String Images) {
        this.Images = Images;
    }

}
