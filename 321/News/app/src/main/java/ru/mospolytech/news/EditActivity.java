package ru.mospolytech.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class EditActivity extends AppCompatActivity {

    public static final int ADD = 1;
    public static final int EDIT = 2;
    public static final int SELECT_IMAGE = 3;
    public static final String PREF = "PREF";
    public static final String POSITION = "POSITION";
    public static final String SIZE = "SIZE";
    public static final String IMAGE = "IMAGE";
    public static final String TITLE = "TITLE";
    public static final String TEXT = "TEXT";

    private Uri imageUri;
    private int position;
    private Intent resultIntent;
    private ImageView imageEdit;
    private EditText titleEdit;
    private EditText textEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        imageEdit = findViewById(R.id.imageEdit);
        titleEdit = findViewById(R.id.titleEdit);
        textEdit = findViewById(R.id.textEdit);

        Intent intent = getIntent();
        if (intent.getExtras() != null){
            position = intent.getExtras().getInt(POSITION);
            imageUri = Uri.parse(intent.getExtras().getString(IMAGE));
            imageEdit.setImageURI(imageUri);
            titleEdit.setText(intent.getExtras().getString(TITLE));
            textEdit.setText(intent.getExtras().getString(TEXT));
        } else {
            position = -1;
            imageUri = MainActivity.getUriToDrawable(this, android.R.drawable.star_on);
            imageEdit.setImageURI(imageUri);
        }

        resultIntent = new Intent();
        setResult(RESULT_CANCELED, resultIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == SELECT_IMAGE) {
                imageUri = data.getData();
                imageEdit.setImageURI(imageUri);
            }
        }
    }

    public void onSave(View view){
        resultIntent.putExtra(POSITION, position);
        resultIntent.putExtra(IMAGE, imageUri.toString());
        resultIntent.putExtra(TITLE, titleEdit.getText().toString());
        resultIntent.putExtra(TEXT, textEdit.getText().toString());
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void onSelectImage(View view){
        Intent selectIntent = new Intent(Intent.ACTION_PICK);
        selectIntent.setType("image/*");
        startActivityForResult(selectIntent, SELECT_IMAGE);
    }
}
