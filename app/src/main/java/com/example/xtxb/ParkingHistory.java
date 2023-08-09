package com.example.xtxb;

import android.os.Parcel;
import android.os.Parcelable;

public class ParkingHistory implements Parcelable {
    private String parkingName;
    private String parkingAddress;
    private String parkingDuration;

    public ParkingHistory(String name, String address, String duration) {
        parkingName = name;
        parkingAddress = address;
        parkingDuration = duration;
    }



    public String getParkingName() {
        return parkingName;
    }

    public String getParkingAddress() {
        return parkingAddress;
    }

    public String getParkingDuration() {
        return parkingDuration;
    }
    // Parcelable 接口的实现
    protected ParkingHistory(Parcel in) {
        parkingName = in.readString();
        parkingName = in.readString();
        parkingDuration = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(parkingName);
        dest.writeString(parkingName);
        dest.writeString(parkingDuration);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ParkingHistory> CREATOR = new Creator<ParkingHistory>() {
        @Override
        public ParkingHistory createFromParcel(Parcel in) {
            return new ParkingHistory(in);
        }

        @Override
        public ParkingHistory[] newArray(int size) {
            return new ParkingHistory[size];
        }
    };
}
