package ru.mospolytech.news;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
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
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            ListItem item = null;
            int position = -1;
            if (requestCode == EditActivity.ADD){
                Uri image = Uri.parse(data.getExtras().getString(EditActivity.IMAGE));
                String title = data.getExtras().getString(EditActivity.TITLE);
                String text = data.getExtras().getString(EditActivity.TEXT);
                item = new ListItem(image, title, text);
                position = list.size();
                list.add(item);
                adapter.notifyDataSetChanged();
            }
            if (requestCode == EditActivity.EDIT){
                position = data.getExtras().getInt(EditActivity.POSITION);
                item = list.get(position);
                item.image = Uri.parse(data.getExtras().getString(EditActivity.IMAGE));
                item.title = data.getExtras().getString(EditActivity.TITLE);
                item.text = data.getExtras().getString(EditActivity.TEXT);
                list.set(position, item);
                adapter.notifyDataSetChanged();
            }
            SharedPreferences pref = getSharedPreferences(EditActivity.PREF, MODE_PRIVATE);
            SharedPreferences.Editor edit = pref.edit();
            edit.putString(EditActivity.IMAGE + position, item.image.toString());
            edit.putString(EditActivity.TITLE + position, item.title);
            edit.putString(EditActivity.TEXT + position, item.text);
            edit.putInt(EditActivity.SIZE, list.size());
            edit.commit();
        }
    }

    private void setData(){
        list = new ArrayList<>();
        SharedPreferences pref = getSharedPreferences(EditActivity.PREF, MODE_PRIVATE);
        int size = pref.getInt(EditActivity.SIZE, 0);
        for (int i = 0; i < size; i++){
            Uri image = Uri.parse(pref.getString(EditActivity.IMAGE + i, ""));
            String title = pref.getString(EditActivity.TITLE + i, "");
            String text = pref.getString(EditActivity.TEXT + i, "");
            list.add(new ListItem(image, title, text));
        }
    }

    public void onAdd(View view){
        Intent addIntent = new Intent(this, EditActivity.class);
        startActivityForResult (addIntent, EditActivity.ADD);
    }

    public static Uri getUriToDrawable(@NonNull Context context,
                                       @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
        return imageUri;
    }
}
