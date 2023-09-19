package com.example.rcassistant.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface usersDao {

    @Query("select * from users")
        List<users> getallUsers();

     @Insert
        void insertuser(users user);

    @Query("select exists(select * from users where phonenumber= :number)")
    Boolean userexist(String number);

    @Query("select password from users where phonenumber= :number")
    String getuserpassword(String number);

    @Delete
      void deleteuser(users user);
}
