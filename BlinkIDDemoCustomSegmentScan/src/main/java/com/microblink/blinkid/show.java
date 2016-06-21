package com.microblink.blinkid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sodajune on 6/9/16.
 */
public class show extends Activity{


    private DBHelper mDBHelper ;
    private String mValueList;

    private String str_age  ;
    private String str_sex;

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

    public void compute(View view){
        Button compute = (Button)findViewById(R.id.button_compute);
        TextView value = (TextView) findViewById(R.id.value);
        mDBHelper = new DBHelper(this);



        mValueList = mDBHelper.ComputeData("F","22");
//        Log.e("DB---",mDBHelper.ComputeData("F","22")+"");
//        value.setText("LOL");

        value.setText(mValueList);


    }

    private boolean copyDatabase(Context context) {
        try {

            InputStream inputStream = context.getAssets().open(DBHelper.DBNAME);
            String outFileName = DBHelper.DBLOCATION + DBHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[]buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("MainActivity","DB copied");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TextView sex = (TextView) findViewById(R.id.editSex);
        TextView year = (TextView) findViewById(R.id.editYear);
        Button compute = (Button)findViewById(R.id.button_compute);
        compute.setEnabled(true);

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String result=data.getStringExtra("Sex");
                String result2=data.getStringExtra("Year");
                Pattern men = Pattern.compile("(M\\s*r\\s+)");
                Pattern wmen = Pattern.compile("((M\\s*i\\s*ss)|(M\\s*r|M\\s*r\\s*s))\\s+");
                Matcher mm = men.matcher(result);
                Matcher mw = wmen.matcher(result);

                if(mm.matches()){
                    result="M";

                }else if(mw.matches()) {
                    result="F";
                }

                int thisYear = Calendar.getInstance().get(Calendar.YEAR);
                int age  ;
                age = thisYear-Integer.valueOf(result2);

                result2 = String.valueOf(age);
                str_sex = result;
                str_age = result2;
                sex.setText("F");
                year.setText("22");
            }
            }

        }
    }//onActivityResult


