package com.example.sanil.roomdatabasedemo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by sanil on 04-06-2018.
 */

@Database(entities = {University.class}, version = 1)
public abstract class SampleDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();
}
