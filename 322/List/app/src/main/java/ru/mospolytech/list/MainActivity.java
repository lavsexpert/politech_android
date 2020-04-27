package ru.mospolytech.list;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ListAdapter adapter;
    List<ListItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setData();
        adapter = new ListAdapter(this, list);
        recyclerView = findViewById(R.id.listView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            ListItem item = null;
            int position = -1;
            if (requestCode == EditActivity.ADD_REQUEST){
                Uri image = Uri.parse(data.getExtras().getString(EditActivity.IMAGE));
                String title = data.getExtras().getString(EditActivity.TITLE);
                String text = data.getExtras().getString(EditActivity.TEXT);
                item = new ListItem(image, title, text);
                position = list.size();
                list.add(item);
                adapter.notifyDataSetChanged();
            }
            if (requestCode == EditActivity.EDIT_REQUEST){
                position = data.getExtras().getInt(EditActivity.POSITION);
                item = list.get(position);
                item.image = Uri.parse(data.getExtras().getString(EditActivity.IMAGE));
                item.title = data.getExtras().getString(EditActivity.TITLE);
                item.text = data.getExtras().getString(EditActivity.TEXT);
                list.set(position, item);
                adapter.notifyDataSetChanged();
            }
            SharedPreferences preferences = getSharedPreferences(EditActivity.PREF, MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putInt(EditActivity.POSITION, list.size()-1);
            edit.putString(EditActivity.IMAGE + position, item.image.toString());
            edit.putString(EditActivity.TITLE + position, item.title);
            edit.putString(EditActivity.TEXT + position, item.text);
            edit.apply();
        }
    }

    private void setData(){
        list = new ArrayList<>();
        SharedPreferences preferences = getSharedPreferences(EditActivity.PREF, MODE_PRIVATE);
        int position = preferences.getInt(EditActivity.POSITION, -1);
        for (int i = 0; i <= position; i++){
            Uri image = Uri.parse(preferences.getString(EditActivity.IMAGE + i, ""));
            String title = preferences.getString(EditActivity.TITLE + i, "");
            String text = preferences.getString(EditActivity.TEXT + i, "");
            list.add(new ListItem(image, title, text));
        }
    }

    public static Uri getUriToDrawable(@NonNull Context context,
                                       @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
        return imageUri;
    }

    public void onClick(View view){
        Intent addIntent = new Intent(this, EditActivity.class);
        addIntent.putExtra(EditActivity.IMAGE, getUriToDrawable(this, android.R.drawable.ic_menu_save).toString());
        addIntent.putExtra(EditActivity.TITLE, "");
        addIntent.putExtra(EditActivity.TEXT, "");
        addIntent.putExtra(EditActivity.POSITION, -1);
        startActivityForResult(addIntent, EditActivity.ADD_REQUEST);
    }
}
