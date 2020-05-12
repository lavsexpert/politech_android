package ru.mospolytech.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey
    int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "email")
    String email;
}
