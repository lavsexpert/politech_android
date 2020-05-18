package ru.mospolytech.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM user")
    List<User> readAll();

    @Query("SELECT COUNT(id) FROM user")
    int count();

    @Query("SELECT * FROM user WHERE name = :name")
    User readByName(String name);

    @Query("SELECT * FROM user WHERE email = :email")
    User readByEmail(String email);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user")
    void deleteAll();
}
