package ru.mospolytech.room;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editName, editEmail;
    RecyclerView recyclerView;
    UserAdapter adapter;
    List<User> list;
    static Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        list = new ArrayList<>();
        adapter = new UserAdapter(this, list);
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        handler = new Handler(){
            public void handleMessage(@NonNull Message message){
                adapter.list = list;
                adapter.notifyDataSetChanged();
            }
        };
    }

    public void onAddClick(View view){
        Thread dbThread = new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.id = App.app.db.userDao().count();
                user.name = editName.getText().toString();
                user.email = editEmail.getText().toString();
                App.app.db.userDao().insert(user);
                list.add(user);
                handler.sendEmptyMessage(0);
            }
        });
        dbThread.start();
    }

    public void onReadClick(View view){
        Thread dbThread = new Thread(new Runnable() {
            @Override
            public void run() {
                list = App.app.db.userDao().readAll();
                for (User user: list) {
                    Log.d("SQL", "ID = " + user.id + "Name = " + user.name + "Email = " + user.email);
                }
                handler.sendEmptyMessage(0);
            }
        });
        dbThread.start();
    }

    public void onClearClick(View view){
        Thread dbThread = new Thread(new Runnable() {
            @Override
            public void run() {
                App.app.db.userDao().deleteAll();
                list.clear();
                handler.sendEmptyMessage(0);
            }
        });
        dbThread.start();
    }
}
