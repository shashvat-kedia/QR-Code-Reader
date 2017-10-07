package com.example.shashvatkedia.qrcodereader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private TextView result;
    private Button scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.barcodeTranslation);
        scan = (Button) findViewById(R.id.qrReadButton);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.initiateScan();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(intentResult!=null){
            String scanContent = intentResult.getContents();
             result.setText(scanContent);
        }
        else{
            Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_SHORT).show();
        }
    }
}
