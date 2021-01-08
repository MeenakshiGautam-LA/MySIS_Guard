package com.sisindia.mysis.entity.base;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

abstract public class CSObject implements Parcelable,Comparable {

//    public String parentID;
//    public boolean isSelected;
//
//
//
//    public String getId() {
//        return parentID;
//    }
//
//    public void setId(String id) {
//        this.parentID = parentID;
//    }
//
//    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString("");
//    }
    }

}
