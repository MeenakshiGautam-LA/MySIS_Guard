package com.sisindia.mysis.dataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.sisindia.mysis.entity.App_Update_Model;
import com.sisindia.mysis.entity.AttendanceGuardPicModel;
import com.sisindia.mysis.entity.AttendanceMasterModel;
import com.sisindia.mysis.entity.DailyAttendanceDetail;
import com.sisindia.mysis.entity.Duty_PostDetail_Model;
import com.sisindia.mysis.entity.Flash_Message_Model;
import com.sisindia.mysis.entity.GetSiteDetail;
import com.sisindia.mysis.entity.LeaveDetailTransaction;
import com.sisindia.mysis.entity.LeaveMaster;
import com.sisindia.mysis.entity.LeaveTypeListModel;
import com.sisindia.mysis.entity.NotificationModel;
import com.sisindia.mysis.entity.PeriodicTrackingInfoModel;
import com.sisindia.mysis.entity.ProfileModel;
import com.sisindia.mysis.entity.RosterModel;
import com.sisindia.mysis.entity.ServiceInfoDetail;
import com.sisindia.mysis.entity.ShiftMasterModel;
import com.sisindia.mysis.entity.UnitDetailModel;
import com.sisindia.mysis.entity.UserDetailModel;
import com.sisindia.mysis.utils.Constants;

import static com.sisindia.mysis.utils.Constants.APP_DATABASE_VERSION;


@Database(entities = {ShiftMasterModel.class, UserDetailModel.class, RosterModel.class, LeaveDetailTransaction.class,
        Duty_PostDetail_Model.class, ServiceInfoDetail.class, DailyAttendanceDetail.class, ProfileModel.class, EmployeDetailsModel.class,
        AttendanceMasterModel.class, AttendanceGuardPicModel.class, UnitDetailModel.class, App_Update_Model.class, Flash_Message_Model.class, GetSiteDetail.class, NotificationModel.class, PeriodicTrackingInfoModel.class,
        LeaveMaster.class, LeaveTypeListModel.class},
        version = APP_DATABASE_VERSION, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase INSTANCE;

    public abstract ShifMaster_DAO shifMaster_dao();

    public abstract UnitDetailDao unitDetailDao();

    public abstract Notification_DAO notification_dao();

    public abstract App_Update_Dao app_update_dao();

    //    public abstract TrackingInfoDAO111 trackingInfoDAO111();
    public abstract TrackingInfoDAO trackingInfoDAO();

    public abstract User_Detail_Dao user_detail_dao();

    public abstract Employee_DAO getEmployee_dao();

    public abstract FlashMessageDao flashMessageDao();

    public abstract RosterDetailDao rosterDetailDao();

    public abstract ProfileDao profileDao();

    public abstract LeaveEmployeeDetail_Dao leaveTransactionDetail_dao();

    public abstract GetLeaveTypeList_Dao getLeaveTypeList_dao();

    public abstract DailyAttendanceDetail_DAO markAttendanceDao();

    public abstract LeaveMaster_Dao leave_Master_Dao();

    public abstract AttendanceMasterDao attendanceMasterDao();

    public abstract SiteDetail_DAO siteDetail_DAO();

    public abstract Duty_Post_Detail_Dao duty_post_detail_dao();

    public abstract ServiceInfoDetail_DAO serviceDAO();

    public abstract AttendancePicDao attendancePicDao();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, Constants.DATABASE)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
//                            .addMigrations(MIGRATION_CHANGE_2_To_3)
                            .addMigrations(MIGRATION_CHANGE_3_To_4)
                            .build();
        }
        return INSTANCE;
    }

    @Override
    public void clearAllTables() {
        INSTANCE = null;
    }

    //    static final Migration MIGRATION_CHANGE_2_To_3 = new Migration(2, 3) { // for VERSION 1.0.4
    static final Migration MIGRATION_CHANGE_2_To_3 = new Migration(2, 3) { // for VERSION 1.0.4
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("DROP table ROSTER_DETAIL");
            database.execSQL("CREATE TABLE ROSTER_DETAIL (ID TEXT NOT NULL, UNIT_CODE TEXT, DUTY_IN_ENABLE_TIME TEXT, DUTY_OUT_DISABLE_TIME TEXT, SHIFT_ID TEXT, UNIT_NAME TEXT, ROSTER_DATE TEXT, DATE_MODIFIED TEXT, REGNO TEXT, ROSTER_ID TEXT, DUTY_POST_ID TEXT, DUTY_RANK TEXT, JSON TEXT, FLAG TEXT, GEO_LOCATION TEXT, SHIFT_END_TIME TEXT, SHIFT_START_TIME TEXT, QR_CODE TEXT, PRIMARY KEY(ID))");

            database.execSQL("DROP table APP_UPDATE_TABLE");
            database.execSQL("CREATE TABLE APP_UPDATE_TABLE (VER_ID TEXT NOT NULL, APP_TYPE TEXT, FILE_VERSION TEXT, NAME TEXT, DESCRIPTION TEXT, FILE_URL TEXT, VERSION_CODE TEXT, FLAG TEXT, PRIMARY KEY(VER_ID))");
        }
    };
    /* static final Migration MIGRATION_CHANGE_3_To_4 = new Migration(3, 4) { // for VERSION 1.0.5
         @Override
         public void migrate(SupportSQLiteDatabase database) {
             database.execSQL("DROP table LEAVE_TRANSACTION_DETAILS");
             database.execSQL("DROP table LEAVE_MASTER");
             database.execSQL("DROP table GET_LEAVE_TYPE_LIST");
             database.execSQL("CREATE TABLE LEAVE_MASTER (ID TEXT NOT NULL, LEAVE_TYPE INTEGER, LEAVE_STATUS INTEGER, FLAG TEXT, JSON TEXT, LEAVE_START_DATE TEXT, LEAVE_END_DATE TEXT,DATE_MODIFIED TEXT,LEAVE_REQUEST_TYPE TEXT,REGNO TEXT, PRIMARY KEY(ID))");
             database.execSQL("CREATE TABLE LEAVE_TRANSACTION_DETAILS (ID TEXT NOT NULL, REGNO TEXT, IS_OFF TEXT, LEAVE_TYPE_ID INTEGER, JSON TEXT, FLAG TEXT, LEAVE_DATE TEXT,LEAVE_DESCRIPTION TEXT,LEAVE_MASTER_ID TEXT,DATE_MODIFIED TEXT, PRIMARY KEY(ID))");
             database.execSQL("CREATE TABLE GET_LEAVE_TYPE_LIST (ID TEXT NOT NULL, LEAVE_ID TEXT, LEAVE_TYPE TEXT, LEAVE_CODE TEXT, COLOR_CODE TEXT, REMARK TEXT, FOR_SELF TEXT, PRIMARY KEY(ID))");
         }
     };*/
    static final Migration MIGRATION_CHANGE_3_To_4 = new Migration(3, 4) { // for VERSION 1.0.5
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE IF EXISTS USER_DETAIL");
            database.execSQL("CREATE TABLE USER_DETAIL (ID TEXT NOT NULL," +
                    "DATE_MODIFIED TEXT, " +
                    "USERNAME TEXT, " +
                    "UNIT_CODE TEXT, " +
                    "COMPANY_NAME TEXT, " +
                    "FULLNAME TEXT, " +
                    "REGNO TEXT, " +
                    "DESIGNATION TEXT, " +
                    "PHONE TEXT," +
                    "EMAIL TEXT," +
                    "BRANCH_CODE TEXT," +
                    "BRANCH_NAME TEXT," +
                    "EXPIRY_DATE TEXT," +
                    "PROFILE_PICTURE TEXT," +
                    "EMP_RANK TEXT," +
                    "USER_RATING INTEGER NOT NULL DEFAULT 0," +
                    "USER_BADGES INTEGER NOT NULL DEFAULT 0," +
                    "PENDING_APPROVAL TEXT," +
                    "CHANGE_CONTACT_NO TEXT," +
                    "SUPER_VISIOR_NAME TEXT," +
                    "SUPER_VISIOR_PHONE TEXT," +
                    "SUPER_VISIOR_EMAIL TEXT," +
                    "supRegNo TEXT," +
                    "supPicture TEXT," +
                    "aIRegNo TEXT," +
                    "aIEmail TEXT," +
                    "aIName TEXT," +
                    "aIMobile TEXT," +
                    "AI_Picture TEXT," +
                    "HELPLINE_NO TEXT," +
                    "FLAG TEXT," +
                    "JSON TEXT, PRIMARY KEY(ID))");

            database.execSQL("DROP TABLE IF EXISTS EMPLOYEE_DETAIL_TABLE");
            database.execSQL("CREATE TABLE EMPLOYEE_DETAIL_TABLE (ID TEXT NOT NULL," +
                    "DATE_MODIFIED TEXT, " +
                    "EMP_NAME TEXT, " +
                    "UNIT_CODE TEXT, " +
                    "GENDER TEXT, " +
                    "RANK TEXT, " +
                    "REGNO TEXT, " +
                    "BILL_TYPE TEXT, " +
                    "DATE_OF_BIRTH TEXT," +
                    "JOINING_DATE TEXT," +
                    "MOBILE TEXT," +
                    "IsActive TEXT," +
                    "EMAIL TEXT," +
                    "PICTURE TEXT," +
                    "isSelected TEXT," +
                    "BANK_IFSC TEXT," +
                    "BANK_NAME TEXT," +
                    "BANK_ADDRESS TEXT," +
                    "ACCOUNT_NUMBER TEXT," +
                    "BANK_LOGO TEXT," +
                    "FLAG TEXT," +
                    "TEMP_SYMBOL TEXT," +
                    "AUTH_STRENGTH TEXT," +
                    "EMP_STRENGTH TEXT," +
                    "IsBankDetailUpdated TEXT," +
                    "UniformKitExpiryDate TEXT," +
                    "GunLicenceExpiryDate TEXT," +
                    "DLExpiryDate TEXT," +
                    "IsUanNoUpdated TEXT," +
                    "JSON TEXT, PRIMARY KEY(ID))");
        }
    };
    static final Migration MIGRATION_CHANGE_5_To_6 = new Migration(5, 6) { // for VERSION 1.0.7
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE IF EXISTS NOTIFICATION_TABLE");
            database.execSQL("CREATE TABLE NOTIFICATION_TABLE (ID TEXT NOT NULL," +
                    "DATE_MODIFIED TEXT, " +
                    "CATEGORY TEXT, " +
                    "TITLE TEXT, " +
                    "AUTHOR TEXT, " +
                    "DESCR TEXT, " +
                    "DATE_READ TEXT, " +
                    "READ_STATUS INTEGER NOT NULL DEFAULT 0," +
                    "POPUP_ALERT INTEGER NOT NULL DEFAULT 0," +
                    "SMIELY TEXT," +
                    "EXPIRY_DATE TEXT," +
                    "DELETED INTEGER," +
                    "SYNC INTEGER NOT NULL DEFAULT 0," +
                    "SYNC_TABLE TEXT," +
                    "FCM_ID TEXT," +
                    "SEND_FCM INTEGER NOT NULL DEFAULT 0," +
                    "FCM_SENT INTEGER NOT NULL DEFAULT 0," +
                    "FCM_SEND_ON TEXT," +
                    "FCM_IMAGE_URL TEXT," +
                    "PARENT_ID TEXT," +
                    "PARENT_TYPE TEXT," +
                    "FLAG TEXT," +
                    "JSON TEXT, PRIMARY KEY(ID))");
        }
    };
}
