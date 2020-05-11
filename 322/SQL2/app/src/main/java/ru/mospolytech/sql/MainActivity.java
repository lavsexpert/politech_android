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
        contentValues.put(DBHelper.NAME, editName.getText().toString());
        contentValues.put(DBHelper.EMAIL, editEmail.getText().toString());
        db.insert(DBHelper.DB_TABLE, null, contentValues);
        dbHelper.close();
        editName.setText("");
        editEmail.setText("");
    }

    public void onReadClick(View view){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(DBHelper.DB_TABLE, null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            int idIndex = cursor.getColumnIndex(DBHelper.ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.NAME);
            int emailIndex = cursor.getColumnIndex(DBHelper.EMAIL);
            do {
                Log.d("Polytech", "ID = " + cursor.getInt(idIndex)
                + ", name = " + cursor.getString(nameIndex)
                + ", email = " + cursor.getString(emailIndex));
            } while (cursor.moveToNext());
        }
        cursor.close();
        dbHelper.close();
        editName.setText("");
        editEmail.setText("");
    }

    public void onClearClick(View view){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.DB_TABLE, null, null);
        dbHelper.close();
        editName.setText("");
        editEmail.setText("");
    }
}
