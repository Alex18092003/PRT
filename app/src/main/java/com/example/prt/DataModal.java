package com.example.prt;

import android.os.Parcel;
import android.os.Parcelable;

public class DataModal implements Parcelable {

    private Integer Kod_teacher;
    private String Name;
    private String Surname;
    private String Patronymic;
    private String Subject;
    private String Images;

    public DataModal( Integer Kod_teacher, String Name, String Surname,String Patronymic, String Subject,  String Images ) {
        this.Kod_teacher = Kod_teacher;
        this.Name = Name;
        this.Surname = Surname;
        this.Patronymic = Patronymic;
        this.Subject = Subject;
        this.Images = Images;
    }

    protected DataModal(Parcel in)
    {
        Kod_teacher = in.readInt();
        Name = in.readString();
        Surname = in.readString();
        Patronymic = in.readString();
        Subject = in.readString();
        Images = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeInt(Kod_teacher);
        parcel.writeString(Name);
        parcel.writeString(Surname);
        parcel.writeString(Patronymic);
        parcel.writeString(Subject);
        parcel.writeString(Images);
    }
    public static final Creator<DataModal> CREATOR = new Creator<DataModal>() {
        @Override
        public DataModal createFromParcel(Parcel in) {
            return new DataModal(in);
        }

        @Override
        public DataModal[] newArray(int size) {
            return new DataModal[size];
        }
    };
}
