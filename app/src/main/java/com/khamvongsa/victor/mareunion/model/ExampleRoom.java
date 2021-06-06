package com.khamvongsa.victor.mareunion.model;


import android.os.Parcel;
import android.os.Parcelable;

public class ExampleRoom implements Parcelable{

    private long id;

    private String name;

    private int color;

    public ExampleRoom(long id, String name, int color) {

        this.id = id;
        this.name = name;
        this.color = color;
    }

    protected ExampleRoom(Parcel in) {
        id = in.readLong();
        name = in.readString();
        color = in.readInt();
    }

    public static final Creator<ExampleRoom> CREATOR = new Creator<ExampleRoom>() {
        @Override
        public ExampleRoom createFromParcel(Parcel in) {
            return new ExampleRoom(in);
        }

        @Override
        public ExampleRoom[] newArray(int size) {
            return new ExampleRoom[size];
        }
    };



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeInt(color);
    }


}
