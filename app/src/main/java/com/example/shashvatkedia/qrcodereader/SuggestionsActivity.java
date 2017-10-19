package com.example.shashvatkedia.qrcodereader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SuggestionsActivity extends AppCompatActivity {
    private String email = "shashvat51@gmail.com";
    private String subject = "Suggestion for the app";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        TextView suggestionTextView = (TextView) findViewById(R.id.suggestionMessageActivity);
        EditText suggestionsEditText = (EditText) findViewById(R.id.suggestionsEditText);
        Button suggestionSubmitButton = (Button) findViewById(R.id.suggestionSubmitButton);
        suggestionSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_EMAIL,email);
                intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                if(intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
            }
        });
    }
}
