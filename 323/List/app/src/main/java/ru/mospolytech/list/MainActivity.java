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


    private RecyclerView listView;
    private ListAdapter adapter;
    private List<ListItem> list;
    public static final int EDIT = 0;
    public static final int ADD = 1;
    public static final int EMPTY_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setData();
        adapter = new ListAdapter(this, list);
        listView = findViewById(R.id.listView);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            int position = -1;
            ListItem item = null;
            if (requestCode == EDIT){
                Uri image = (Uri) data.getExtras().get(EditActivity.IMAGE);
                String title = data.getExtras().getString(EditActivity.TITLE);
                String text = data.getExtras().getString(EditActivity.TEXT);
                position = data.getExtras().getInt(EditActivity.POSITION);
                item = list.get(position);
                item.title = title;
                item.text = text;
                item.image = image;
                adapter.notifyDataSetChanged();
            }
            if (requestCode == ADD){
                Uri image = getUriToDrawable(this, android.R.drawable.btn_star);
                String title = data.getExtras().getString(EditActivity.TITLE);
                String text = data.getExtras().getString(EditActivity.TEXT);
                item = new ListItem(image, title, text);
                position = list.size();
                list.add(item);
                adapter.notifyDataSetChanged();
            }
            if (item != null){
                SharedPreferences pref = getSharedPreferences(EditActivity.PREF, MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt(EditActivity.COUNT, list.size());
                editor.putString(EditActivity.IMAGE + String.valueOf(position), String.valueOf(item.image));
                editor.putString(EditActivity.TITLE + String.valueOf(position), item.title);
                editor.putString(EditActivity.TEXT + String.valueOf(position), item.text);
                editor.commit();
            }
        }
    }

    public static final Uri getUriToDrawable(@NonNull Context context,
                                             @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
        return imageUri;
    }

    private void setData(){
        list = new ArrayList<>();
        SharedPreferences pref = getSharedPreferences(EditActivity.PREF, MODE_PRIVATE);
        int listCount = pref.getInt(EditActivity.COUNT, 0);
        for (int i=0; i<listCount; i++){
            Uri image = Uri.parse(pref.getString(EditActivity.IMAGE + String.valueOf(i), ""));
            String title = pref.getString(EditActivity.TITLE + String.valueOf(i), "");
            String text = pref.getString(EditActivity.TEXT + String.valueOf(i), "");
            ListItem item = new ListItem(image, title, text);
            list.add(item);
       }
    }

    public void onClick(View view){
        Intent addIntent = new Intent(this, EditActivity.class);
        addIntent.putExtra(EditActivity.POSITION, EMPTY_POSITION);
        startActivityForResult(addIntent, ADD);
    }
}
