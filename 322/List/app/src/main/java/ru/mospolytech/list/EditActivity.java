package ru.mospolytech.list;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    private Intent resultIntent;
    private EditText titleEdit;
    private EditText textEdit;
    private ImageView imageEdit;
    private Uri imageUri;
    private int position;

    public static final int ADD_REQUEST = 1;
    public static final int EDIT_REQUEST = 2;
    public static final int SELECT_REQUEST = 3;
    public static final String PREF = "PREF";
    public static final String TITLE = "TITLE";
    public static final String TEXT = "TEXT";
    public static final String IMAGE = "IMAGE";
    public static final String POSITION = "POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        titleEdit = findViewById(R.id.titleEdit);
        textEdit = findViewById(R.id.textEdit);
        imageEdit = findViewById(R.id.imageEdit);

        Intent intent = getIntent();
        imageUri = Uri.parse(intent.getExtras().getString(IMAGE));
        imageEdit.setImageURI(imageUri);
        titleEdit.setText(intent.getExtras().getString(TITLE));
        textEdit.setText(intent.getExtras().getString(TEXT));
        position = intent.getExtras().getInt(POSITION);

        resultIntent = new Intent();
        setResult(RESULT_CANCELED, resultIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == SELECT_REQUEST) {
                imageUri = data.getData();
                imageEdit.setImageURI(imageUri);
            }
        }
    }

    public void onChangeImage(View view){
        Intent selectIntent = new Intent(Intent.ACTION_PICK);
        selectIntent.setType("image/*");
        startActivityForResult(selectIntent, SELECT_REQUEST);
    }

    public void onClick(View view){
        resultIntent.putExtra(POSITION, position);
        resultIntent.putExtra(IMAGE, imageUri.toString());
        resultIntent.putExtra(TITLE, titleEdit.getText().toString());
        resultIntent.putExtra(TEXT, textEdit.getText().toString());
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
