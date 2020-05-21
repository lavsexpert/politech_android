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
        handler = new Handler() {
            public void handleMessage(@NonNull Message message){
                adapter.list = list;
                adapter.notifyDataSetChanged();
                editName.setText("");
                editEmail.setText("");
            }
        };
    }

    public void onAddClick(View view){
        Thread dbThread = new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.id = App.db.userDao().count();
                user.name = editName.getText().toString();
                user.email = editEmail.getText().toString();
                App.db.userDao().create(user);
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
                list.clear();
                list = App.db.userDao().readAll();
                for (User user: list) {
                    Log.d("Polytech", "ID = "+user.id
                    + ", name = " + user.name + ", email = " + user.email);
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
                App.db.userDao().clear();
                list.clear();
                handler.sendEmptyMessage(0);
            }
        });
        dbThread.start();
    }
}
