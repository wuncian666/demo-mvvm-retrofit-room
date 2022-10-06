package com.example.myroom.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myroom.retrofit.AwsInfoResponse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//資料綁定的Getter-Setter,資料庫版本,是否將資料導出至文件
@Database(entities = {AwsInfoResponse.class}, version = 1, exportSchema = true)
public abstract class DataBase extends RoomDatabase {

    public abstract AwsInfoDao getAwsDao();//設置對外接口

    public static final String DB_NAME = "RecordData.db";//資料庫名稱

    private static volatile DataBase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized DataBase getInstance(android.content.Context context) {
        if (INSTANCE == null) {
            synchronized (DataBase.class) {
                if (INSTANCE == null) INSTANCE = create(context);//創立新的資料庫
            }
        }
        return INSTANCE;
    }

    private static DataBase create(final Context context) {
        return Room.databaseBuilder(context.getApplicationContext()
                , DataBase.class, DB_NAME).build();
    }

}
