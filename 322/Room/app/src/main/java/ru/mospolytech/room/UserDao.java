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
    void create(User user);

    @Query("SELECT COUNT(id) FROM users")
    int count();

    @Query("SELECT * FROM users")
    List<User> readAll();

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    User read(int id);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM users")
    void clear();
}
