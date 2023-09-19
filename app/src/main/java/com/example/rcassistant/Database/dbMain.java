package com.example.rcassistant.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {users.class},version = 3,exportSchema = false)
public abstract class dbMain extends RoomDatabase {
public abstract usersDao userDao();

}
