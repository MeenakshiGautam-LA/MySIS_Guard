package com.sisindia.mysis.dataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sisindia.mysis.entity.ProfileModel;

@Dao
public interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProfileModel profileModel);

    @Query("select * from PROFILE_TABLE where REGNO=:regNo")
    LiveData<ProfileModel>profileDetails(String regNo);
}
