package com.example.peppapigsrentcalculator;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final double RENT = 390;

    EditText xfinityEditText,pgeEditText,smudEditText,addlGroceriesEditText;
    Button calculate, clear;
    ImageView maeby;

    double xfinityValue,pgeValue,smudValue,addlGroceriesValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSelfPermission(Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_DENIED) {

            Log.d("permission", "permission denied to SEND_SMS - requesting it");
            String[] permissions = {Manifest.permission.SEND_SMS};

            requestPermissions(permissions, 1);

        }

        xfinityEditText = findViewById(R.id.xfinityEditText);
        pgeEditText = findViewById(R.id.pgeEditText);
        smudEditText = findViewById(R.id.smudEditText);
        addlGroceriesEditText = findViewById(R.id.addlGroceriesEditText);

        maeby = findViewById(R.id.maeby);

        calculate = findViewById(R.id.calculate);
        calculate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Calcing BIG PEPPA PIG'z costs", Toast.LENGTH_SHORT).show();
                xfinityValue = extractDoubleFromEditText(xfinityEditText);
                pgeValue = extractDoubleFromEditText(pgeEditText);
                smudValue = extractDoubleFromEditText(smudEditText);
                addlGroceriesValue = extractDoubleFromEditText(addlGroceriesEditText);
                double utilPerPerson = (xfinityValue + pgeValue + smudValue) / 3.0;

                double taylorOwesMattValue = RENT + utilPerPerson - 2*addlGroceriesValue;
                double samOwesMattValue  = utilPerPerson + addlGroceriesValue;

                Intent calculationIntent = new Intent(MainActivity.this, CalculationScreen.class);
                calculationIntent.putExtra("taylorOwesMatt", taylorOwesMattValue);
                calculationIntent.putExtra("samOwesMatt", samOwesMattValue);
                startActivity(calculationIntent);
            }
        }) ;

        clear = findViewById(R.id.clear);
        clear.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xfinityEditText.getText().clear();
                pgeEditText.getText().clear();
                smudEditText.getText().clear();
                addlGroceriesEditText.getText().clear();
            }
        });
    }

    private double extractDoubleFromEditText(EditText editText) {
        return "".equals(editText.getText().toString()) ? 0 : Double.parseDouble(editText.getText().toString());
    }
}
