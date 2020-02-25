package com.example.calculator322;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText edit1;
    EditText edit2;
    RadioGroup radio;
    TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit1 = findViewById(R.id.edit1);
        edit2 = findViewById(R.id.edit2);
        radio = findViewById(R.id.radio);
        textResult = findViewById(R.id.textResult);
    }

    public void onClick(View view){
        int number1 = Integer.parseInt(edit1.getText().toString());
        int number2 = Integer.parseInt(edit2.getText().toString());
        int result = 0;
        switch(radio.getCheckedRadioButtonId()){
            case R.id.radioPlus:
                result = number1 + number2;
                break;
            case R.id.radioMinus:
                result = number1 - number2;
                break;
            case R.id.radioMulty:
                result = number1 * number2;
                break;
            case R.id.radioDevide:
                result = number1 / number2;
                break;
        }
        textResult.setText(String.valueOf(result));
    }
}
