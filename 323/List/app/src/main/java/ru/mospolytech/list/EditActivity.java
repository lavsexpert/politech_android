package ru.mospolytech.list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditActivity extends AppCompatActivity {

    public static final String PREF = "PREF";
    public static final String COUNT = "COUNT";
    public static final String IMAGE = "IMAGE";
    public static final String TITLE = "TITLE";
    public static final String TEXT = "TEXT";
    public static final String POSITION = "POSITION";
    public static final int SELECT_IMAGE = 1;

    public Uri imageUri;

    private ImageView imageEdit;
    private EditText titleEdit;
    private EditText textEdit;
    private Intent resultIntent;
    private int position;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        imageEdit = findViewById(R.id.imageEdit);
        titleEdit = findViewById(R.id.titleEdit);
        textEdit = findViewById(R.id.textEdit);
        resultIntent = new Intent();

        Intent intent = this.getIntent();
        position = intent.getExtras().getInt(POSITION);
        imageEdit.setImageURI((Uri) intent.getExtras().get(IMAGE));
        titleEdit.setText(intent.getExtras().getString(TITLE));
        textEdit.setText(intent.getExtras().getString(TEXT));
        setResult(RESULT_CANCELED, resultIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null){
            if (requestCode == SELECT_IMAGE){
                imageUri = data.getData();
                if (imageUri != null){
                    imageEdit.setImageURI(imageUri);
                }
            }
        }
    }

    public void onClick(View view){
        resultIntent.putExtra(IMAGE, imageUri);
        resultIntent.putExtra(TITLE, titleEdit.getText().toString());
        resultIntent.putExtra(TEXT, textEdit.getText().toString());
        resultIntent.putExtra(POSITION, position);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void onChangeImage(View view){
        Intent selectIntent = new Intent(Intent.ACTION_PICK);
        selectIntent.setType("image/*");
        startActivityForResult(selectIntent, SELECT_IMAGE);
    }
}
