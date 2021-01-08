package com.sisindia.mysis.dataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.sisindia.mysis.entity.Duty_PostDetail_Model;

import java.util.List;

@Dao
public interface Duty_Post_Detail_Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Duty_PostDetail_Model> duty_postDetail_models);

    @Query("select * from DUTY_POST_DETAIL where ID=:ID")
    Duty_PostDetail_Model getShiftDetail(String ID);
    @Query("delete from DUTY_POST_DETAIL where id in (:idList)")
    void deleteAll(List<String> idList);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertsingle(Duty_PostDetail_Model duty_postDetail_model);

    @Update
    void update(Duty_PostDetail_Model task_model);

    @Query("select POST_NAME from DUTY_POST_DETAIL where ID=:postID")
    String postName(String postID);

    @Query("select * from DUTY_POST_DETAIL where ID=:ID")
    Duty_PostDetail_Model getDutyPostDetail(String ID);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSingleRecord(Duty_PostDetail_Model unitDutyPostDetail);
}
