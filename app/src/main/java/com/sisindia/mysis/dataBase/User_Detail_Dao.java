package com.sisindia.mysis.dataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sisindia.mysis.entity.UserDetailModel;

import java.util.List;

@Dao
public interface User_Detail_Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<UserDetailModel> json);
    @Delete
    void delete(UserDetailModel task_model);

//    @Query("select * from USER_DETAIL where REGNO=:regNo and UNIT_CODE=:unitCode")
    @Query("select * from USER_DETAIL where REGNO=:regNo")
    UserDetailModel getUserDetails(String regNo);


    @Query("select * from USER_DETAIL")
    UserDetailModel getDetails();

    @Query("select * from USER_DETAIL where REGNO=:regNO and FLAG='1' Order by DATE_MODIFIED desc limit 1")
    UserDetailModel getDetails(String regNO);

    @Query("select PROFILE_PICTURE from USER_DETAIL where REGNO=:regNO")
    String getPicture(String regNO);

    @Query("SELECT max(DATE_MODIFIED) FROM  USER_DETAIL")
    String maxModifiedDate();

    @Query("select * from USER_DETAIL where REGNO=:regNo")
    LiveData<UserDetailModel> profileDetails(String regNo);

    @Query("update USER_DETAIL set CHANGE_CONTACT_NO=:changeContactNo,PENDING_APPROVAL='1',FLAG='1' where REGNO=:regNo and UNIT_CODE=:unitCode")
    void updateContactNo(String regNo, String unitCode,String changeContactNo);

    @Query("update USER_DETAIL set FLAG='0',PENDING_APPROVAL=:pendingApproval where REGNO=:regNo")
    void updateWithSyncServer(String regNo,String pendingApproval);

}
