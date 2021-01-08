package com.sisindia.mysis.entity;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.sisindia.mysis.entity.base.CSObject;
import com.sisindia.mysis.utils.Constants;

@SuppressLint("ParcelCreator")
@Entity(tableName = Constants.NOTIFICATION_TABLE)
public class NotificationModel extends CSObject {
    @PrimaryKey
    @NonNull
    @SerializedName("Id")
    @ColumnInfo(name = "ID")
    String Id;
    @SerializedName("Category")
    @ColumnInfo(name = "CATEGORY")
    String Category;
    @SerializedName("Title")
    @ColumnInfo(name = "TITLE")
    String Title;
    @SerializedName("Author")
    @ColumnInfo(name = "AUTHOR")
    String Author;
    @SerializedName("Descr")
    @ColumnInfo(name = "DESCR")
    String Descr;
    @SerializedName("DateRead")
    @ColumnInfo(name = "DATE_READ")
    String DateRead;
    @SerializedName("ReadStatus")
    @ColumnInfo(name = "READ_STATUS")
    Integer ReadStatus;
    @SerializedName("PopupAlert")
    @ColumnInfo(name = "POPUP_ALERT")
    Integer PopupAlert;
    @SerializedName("Smiely")
    @ColumnInfo(name = "SMIELY")
    String Smiely;
    @SerializedName("ExpiryDate")
    @ColumnInfo(name = "EXPIRY_DATE")
    String ExpiryDate;
    @ColumnInfo(name = "DELETED")
    @SerializedName("Deleted")
    Integer Deleted;
    @SerializedName("Sync")
    @ColumnInfo(name = "SYNC")
    Integer Sync;
    @SerializedName("SyncTable")
    @ColumnInfo(name = "SYNC_TABLE")
    String SyncTable;
    @SerializedName("FCM_ID")
    @ColumnInfo(name = "FCM_ID")
    String FCM_ID;
    @SerializedName("SEND_FCM")
    @ColumnInfo(name = "SEND_FCM")
    Integer SEND_FCM;
    @SerializedName("FCM_SENT")
    @ColumnInfo(name = "FCM_SENT")
    Integer FCM_SENT;
    @SerializedName("FCM_SEND_ON")
    @ColumnInfo(name = "FCM_SEND_ON")
    String FCM_SEND_ON;
    @ColumnInfo(name = "FCM_IMAGE_URL")
    @SerializedName(("FCM_IMAGE_URL"))
    String FCM_IMAGE_URL;
    @SerializedName("PARENT_ID")
    @ColumnInfo(name = "PARENT_ID")
    String PARENT_ID;
    @SerializedName("PARENT_TYPE")
    @ColumnInfo(name = "PARENT_TYPE")
    String PARENT_TYPE;
    @ColumnInfo(name = "FLAG")
    String flag;
    @ColumnInfo(name = "JSON")
    String json;
    @ColumnInfo(name = "DATE_MODIFIED")
    @SerializedName("Date_Modified")
    String DATE_MODIFIED;
    @SerializedName("TotalTimeInMinute")
    @ColumnInfo(name = "CREATE_TIME")
    String TotalTimeInMinute;
    @SerializedName("CreatedOn")
    @ColumnInfo(name = "CREATED_ON")
    String CreatedOn;


    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }


    public String getTotalTimeInMinute() {
        return TotalTimeInMinute;
    }

    public void setTotalTimeInMinute(String totalTimeInMinute) {
        TotalTimeInMinute = totalTimeInMinute;
    }

    @NonNull
    public String getId() {
        return Id;
    }

    public void setId(@NonNull String id) {
        Id = id;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getDescr() {
        return Descr;
    }

    public void setDescr(String descr) {
        Descr = descr;
    }

    public String getDateRead() {
        return DateRead;
    }

    public void setDateRead(String dateRead) {
        DateRead = dateRead;
    }

    public Integer getReadStatus() {
        return ReadStatus;
    }

    public void setReadStatus(Integer readStatus) {
        ReadStatus = readStatus;
    }

    public Integer getPopupAlert() {
        return PopupAlert;
    }

    public void setPopupAlert(Integer popupAlert) {
        PopupAlert = popupAlert;
    }

    public String getSmiely() {
        return Smiely;
    }

    public void setSmiely(String smiely) {
        Smiely = smiely;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public Integer getDeleted() {
        return Deleted;
    }

    public void setDeleted(Integer deleted) {
        Deleted = deleted;
    }

    public Integer getSync() {
        return Sync;
    }

    public void setSync(Integer sync) {
        Sync = sync;
    }

    public String getSyncTable() {
        return SyncTable;
    }

    public void setSyncTable(String syncTable) {
        SyncTable = syncTable;
    }

    public String getFCM_ID() {
        return FCM_ID;
    }

    public void setFCM_ID(String FCM_ID) {
        this.FCM_ID = FCM_ID;
    }

    public Integer getSEND_FCM() {
        return SEND_FCM;
    }

    public void setSEND_FCM(Integer SEND_FCM) {
        this.SEND_FCM = SEND_FCM;
    }

    public Integer getFCM_SENT() {
        return FCM_SENT;
    }

    public void setFCM_SENT(Integer FCM_SENT) {
        this.FCM_SENT = FCM_SENT;
    }

    public String getFCM_SEND_ON() {
        return FCM_SEND_ON;
    }

    public void setFCM_SEND_ON(String FCM_SEND_ON) {
        this.FCM_SEND_ON = FCM_SEND_ON;
    }

    public String getFCM_IMAGE_URL() {
        return FCM_IMAGE_URL;
    }

    public void setFCM_IMAGE_URL(String FCM_IMAGE_URL) {
        this.FCM_IMAGE_URL = FCM_IMAGE_URL;
    }

    public String getPARENT_ID() {
        return PARENT_ID;
    }

    public void setPARENT_ID(String PARENT_ID) {
        this.PARENT_ID = PARENT_ID;
    }

    public String getPARENT_TYPE() {
        return PARENT_TYPE;
    }

    public void setPARENT_TYPE(String PARENT_TYPE) {
        this.PARENT_TYPE = PARENT_TYPE;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getDATE_MODIFIED() {
        return DATE_MODIFIED;
    }

    public void setDATE_MODIFIED(String DATE_MODIFIED) {
        this.DATE_MODIFIED = DATE_MODIFIED;
    }
}
