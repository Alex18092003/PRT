package com.example.prt;

import android.os.Parcel;
import android.os.Parcelable;

public class Mask implements Parcelable {

    private int Kod_teacher;
    private String Name;
    private String Surname;
    private String Patronymic;
    private String Subject;
    private String Images;

    public Mask(int Kod_teacher, String name, String surname, String patronymic, String subject, String images ) {
        this.Kod_teacher = Kod_teacher;
        Name = name;
        Surname = surname;
        Patronymic = patronymic;
        Subject = subject;
        Images = images;
    }

    protected Mask(Parcel in) {
        Kod_teacher = in.readInt();
        Name = in.readString();
        Surname = in.readString();
        Patronymic = in.readString();
        Subject = in.readString();
        Images = in.readString();
    }

    public static final Creator<Mask> CREATOR = new Creator<Mask>() {
        @Override
        public Mask createFromParcel(Parcel in) {
            return new Mask(in);
        }

        @Override
        public Mask[] newArray(int size) {
            return new Mask[size];
        }
    };

    public void setKod_teacher(int Kod_teacher) {
        this.Kod_teacher = Kod_teacher;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public void setPatronymic(String patronymic) {
        Patronymic = patronymic;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public void setImages(String images) {
        Images = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Kod_teacher);
        parcel.writeString(Name);
        parcel.writeString(Surname);
        parcel.writeString(Patronymic);
        parcel.writeString(Subject);
        parcel.writeString(Images);
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

    public int getKod_teacher() {
        return Kod_teacher;
    }
}
