package ru.mospolytech.sql;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editName, editEmail;
    DBHelper dbHelper;
    RecyclerView recyclerView;
    ListAdapter adapter;
    List<ListItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        dbHelper = new DBHelper(this);
        list = new ArrayList<>();
        adapter = new ListAdapter(this, list);
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        onReadClick(null);
    }

    public void onAddClick(View view){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.NAME, editName.getText().toString());
        contentValues.put(DBHelper.EMAIL, editEmail.getText().toString());
        db.insert(DBHelper.TABLE_NAME, null, contentValues);
        dbHelper.close();
        editName.setText("");
        editEmail.setText("");
        onReadClick(null);
    }

    public void onReadClick(View view){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            int idIndex = cursor.getColumnIndex(DBHelper.ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.NAME);
            int emailIndex = cursor.getColumnIndex(DBHelper.EMAIL);
            list.clear();
            do {
                Log.d("Polytech", "ID = "+cursor.getInt(idIndex)
                + ", name = "+cursor.getString(nameIndex) + ", email = "+cursor.getString(emailIndex));
                ListItem item = new ListItem();
                item.id = cursor.getInt(idIndex);
                item.name = cursor.getString(nameIndex);
                item.email = cursor.getString(emailIndex);
                list.add(item);
            } while (cursor.moveToNext());
            adapter.notifyDataSetChanged();
        }
        cursor.close();
        dbHelper.close();
    }

    public void onClearClick(View view){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABLE_NAME, null, null);
        dbHelper.close();
        list.clear();
        adapter.notifyDataSetChanged();
    }
}
