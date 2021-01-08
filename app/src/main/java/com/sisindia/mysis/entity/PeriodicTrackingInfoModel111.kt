//package com.sisindia.guardattendance.entity
//
//import androidx.room.ColumnInfo
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import com.google.gson.annotations.Expose
//import com.sisindia.guardattendance.utils.Constants
//import org.jetbrains.annotations.NotNull
//
//@Entity(tableName = Constants.TRACKING_INFO)
//data class PeriodicTrackingInfoModel111(
//    @PrimaryKey
//    @ColumnInfo(name = "ID")
//    @Expose
//    @NotNull
//    var ID: String,
//
//    @ColumnInfo(name = "FLAG")
//    var FLAG: String = "2",
//
//    @ColumnInfo(name = "DATE_MODIFIED")
//    var DATE_MODIFIED: String,
//
//    @ColumnInfo(name = "JSON")
//    var JSON: String
//
//)