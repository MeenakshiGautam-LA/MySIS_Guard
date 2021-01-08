package com.sisindia.mysis.dataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sisindia.mysis.entity.App_Update_Model;

@Dao
public interface App_Update_Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(App_Update_Model app_update_model);

    @Delete()
    void delete(App_Update_Model app_update_model);

    @Query("SELECT * from APP_UPDATE_TABLE where FLAG='1'Order by VER_ID desc limit 1")
    LiveData<App_Update_Model> checkApp_Update();

    @Query("SELECT * from APP_UPDATE_TABLE where FLAG='1' Order by VER_ID desc limit 1")
    LiveData<App_Update_Model>  getData();
}
