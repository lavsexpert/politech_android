package ru.mospolytech.sql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static String ID = "id";
    private static String NAME = "name";
    private static String EMAIL = "email";
    private static String USERS = "Users";

    EditText editName, editEmail;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        dbHelper = new DBHelper(this);
    }

    public void onAddClick(View view){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, editName.getText().toString());
        contentValues.put(EMAIL, editEmail.getText().toString());
        db.insert(USERS, null, contentValues);
        dbHelper.close();
    }

    public void onReadClick(View view){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(USERS, null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            int idIndex = cursor.getColumnIndex(ID);
            int nameIndex = cursor.getColumnIndex(NAME);
            int emailIndex = cursor.getColumnIndex(EMAIL);
            do {
                Log.d("SQL", "ID = " + cursor.getInt(idIndex)
                        + ", Name = " + cursor.getString(nameIndex)
                        + ", Email = " + cursor.getString(emailIndex));
            } while (cursor.moveToNext());
        }
        cursor.close();
        dbHelper.close();
    }

    public void onClearClick(View view){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(USERS, null, null);
        dbHelper.close();
    }
}
