package com.microblink.blinkid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.microblink.util.Log;

/**
 * Created by sodajune on 6/9/16.
 */
public class show extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_text);


    }

    public void scanNext(View view){
        Button btn_next = (Button)findViewById(R.id.button_scan);
        Intent intent = new Intent(show.this,FullScreenOCR.class);

        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TextView sex = (TextView) findViewById(R.id.editSex);
        TextView year = (TextView) findViewById(R.id.editYear);

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String result=data.getStringExtra("Sex");
                String result2=data.getStringExtra("Year");

                sex.setText(result);
                year.setText(result2);
            }
            }

        }
    }//onActivityResult


