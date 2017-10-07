package com.example.shashvatkedia.qrcodereader;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.StringTokenizer;

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
            if(scanContent.startsWith("http://") || scanContent.startsWith("https://")){
                Intent intnt = new Intent(Intent.ACTION_VIEW);
                intnt.setData(Uri.parse(scanContent));
                startActivity(intnt);
            }
            else if(scanContent.startsWith("MATMSG:")) {
                String email = "", subject = "", body = "";
                StringTokenizer tokenizer = new StringTokenizer(scanContent, ";");
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    if (token.startsWith("MATMSG:TO:")) {
                        if (token.length() > "MATMSG:TO:".length()) {
                            email = token.substring("MATMSG:TO:".length(), token.length());
                            Log.e("#",email);
                        }
                    } else if (token.startsWith("SUB:")) {
                        if (token.length() > "SUB:".length()) {
                            subject = token.substring("SUB:".length(), token.length());
                        }
                    } else if (token.startsWith("BODY:")) {
                        if (token.length() > "BODY:".length()) {
                            body = token.substring("BODY:".length(), token.length());
                        }
                    }
                }
                if (email.length() > 0) {
                    Intent intnt = new Intent(Intent.ACTION_SEND);
                    intnt.setType("*/*");
                    intnt.putExtra(Intent.EXTRA_EMAIL, email);
                    intnt.putExtra(Intent.EXTRA_SUBJECT, subject);
                    intnt.putExtra(Intent.EXTRA_TEXT, body);
                    startActivity(intnt);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Email id not specified",Toast.LENGTH_SHORT).show();
                }
            }
            else if(scanContent.startsWith("SMSTO:")){
                int count = 0;
                String number="",text="";
                StringTokenizer tokenizer = new StringTokenizer(scanContent,":");
                while(tokenizer.hasMoreTokens()){
                    String token = tokenizer.nextToken();
                    count++;
                    if(count == 2){
                        number = token;
                    }
                    if(count == 3){
                        text = token;
                    }
                }
                if(number.length()>0){
                    Uri uri = Uri.parse("smsto:"+number);
                    Intent intnt = new Intent(Intent.ACTION_SENDTO,uri);
                    intnt.putExtra("sms_body",text);
                    startActivity(intnt);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Number not specified",Toast.LENGTH_SHORT).show();
                }
            }
            else {
                result.setText(scanContent);
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"No Data",Toast.LENGTH_SHORT).show();
        }
    }
}
