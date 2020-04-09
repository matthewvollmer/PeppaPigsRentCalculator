package com.example.peppapigsrentcalculator;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CalculationScreen extends AppCompatActivity {

    private static final String TAYLOR_SMS_NUMBER = "17146732732";
    private static final String SAM_SMS_NUMBER = "19168789788";
    private static final String MATT_SMS_NUMBER = "19162204504";
    private static final String PAY_UP_DEFAULT_TEXT = "OOPS ya dingus, now u gotta pay me for utilities";

    TextView tayOwesMatt, samOwesMatt;
    EditText messageText;

    Button startOverButton, sendMessagesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculation_screen);

        tayOwesMatt = findViewById(R.id.taylorOwesMattValue);
        samOwesMatt = findViewById(R.id.samOwesMattValue);

        messageText = findViewById(R.id.messageText);

        startOverButton = findViewById(R.id.startOverButton);
        sendMessagesButton = findViewById(R.id.sendMessagesButton);

        Intent incomingIntent = getIntent();
        final Double incomingTaylorOwesMattValue = incomingIntent.getDoubleExtra("taylorOwesMatt",0);
        final Double incomingSamOwesMattValue = incomingIntent.getDoubleExtra("samOwesMatt",0);
        tayOwesMatt.setText(String.format(java.util.Locale.US,"$%.2f", incomingTaylorOwesMattValue));
        samOwesMatt.setText(String.format(java.util.Locale.US,"$%.2f", incomingSamOwesMattValue));

        startOverButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent(CalculationScreen.this, MainActivity.class);
                startActivity(returnIntent);
            }
        });

        sendMessagesButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputMessageValue = extractMessageFromEditText(messageText);
                String baseMessage = inputMessageValue.equals("") ? PAY_UP_DEFAULT_TEXT : inputMessageValue;
                sendSms(TAYLOR_SMS_NUMBER, baseMessage +
                        String.format(java.util.Locale.US,": $%.2f", incomingTaylorOwesMattValue));
                sendSms(SAM_SMS_NUMBER, baseMessage +
                        String.format(java.util.Locale.US,"$%.2f", incomingSamOwesMattValue));
            }
        });

    }

    private void sendSms(String phoneNo, String message) {
        PendingIntent pi = PendingIntent.getActivity(this, 0 ,new Intent(this, CalculationScreen.class), 0);
        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNo, null, message, null, null);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS messages failed to send, please try again", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getApplicationContext(), "SMS messages sent successfully", Toast.LENGTH_SHORT).show();
    }

    private String extractMessageFromEditText(EditText editText) {
        return "".equals(editText.getText().toString()) ? "" : editText.getText().toString();
    }
}
