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
        User user = new User();
        user.name = editName.getText().toString();
        user.email = editEmail.getText().toString();
        App.db.userDao().create(user);
    }

    public void onReadClick(View view){
        List<User> list = App.db.userDao().readAll();
        for (User user: list) {
            Log.d("Polytech", "ID = "+user.id
            + ", name = " + user.name + ", email = " + user.email);
        }
    }

    public void onClearClick(View view){
        App.db.userDao().clear();
    }
}
