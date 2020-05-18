package ru.mospolytech.room;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editName, editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
    }

    public void onAddClick(View view){
        Thread dbThread = new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.name = editName.getText().toString();
                user.email = editEmail.getText().toString();
                App.app.db.userDao().insert(user);
            }
        });
        dbThread.start();
    }

    public void onReadClick(View view){
        Thread dbThread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<User> users = App.app.db.userDao().readAll();
                for (User user: users) {
                    Log.d("SQL", "ID = " + user.id + "Name = " + user.name + "Email = " + user.email);
                }
            }
        });
        dbThread.start();
    }

    public void onClearClick(View view){
        Thread dbThread = new Thread(new Runnable() {
            @Override
            public void run() {
                App.app.db.userDao().deleteAll();
            }
        });
        dbThread.start();
    }
}
