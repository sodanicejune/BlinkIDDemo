package com.microblink.blinkid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;

/**
 * Created by sodajune on 6/20/16.
 */
public class DBHelper extends SQLiteOpenHelper {


    public static final String DBNAME = "db.sqlite";
    public static final String DBLOCATION = "/data/data/com.microblink.blinkid/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private double data;




    private String pr;
    private String dr;
    private String pmr;
    private String sur;
    private double sa;


    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    public double ComputeData(String gender, String age) {

        ArrayList<String> valueList = new ArrayList<>();
        openDatabase();
      Cursor cursor = mDatabase.rawQuery("SELECT pr.product_plan_code, pr.gender_insured, pr.age_insured,  pr.occupation_level_insured, pr.rate as premium_rate, dr.lower_sa,  dr.rate as discount_rate, pmr.rate as payment_mode_rate, cp.formula as calculation_pattern_formula FROM  premium_rate pr, payment_mode_rate pmr, calculation_pattern cp LEFT OUTER JOIN discount_rate dr on pr.product_plan_code = dr.product_code WHERE pr.product_plan_code = ? AND (pr.gender_insured = ? OR pr.gender_insured = '' OR pr.gender_insured IS NULL) AND (cast(pr.age_insured as real) = cast(? AS REAL) OR pr.age_insured = '' OR pr.age_insured IS NULL) AND (pr.occupation_level_insured = ? OR pr.occupation_level_insured = '' OR pr.occupation_level_insured IS NULL) AND pmr.code = ? AND pr.calculation_pattern_code = cp.code AND (dr.lower_sa <= cast(? AS REAL) OR dr.lower_sa IS NULL) ORDER BY dr.lower_sa DESC LIMIT 1", new String[]{"05PN99", gender, age, "1", "12", "500000"});
        cursor.moveToFirst();



        if (cursor.getCount() == 1) {
            valueList.add("Str");
            valueList.add(cursor.getString(4));
            valueList.add(cursor.getString(6));
            valueList.add(cursor.getString(7));
            valueList.add(cursor.getString(8));


        }

        pr=valueList.get(1);
        dr=valueList.get(2);
        pmr=valueList.get(3);
        sur = valueList.get(4);

        sa = 500000.00;

        Log.e("pr",pr);
        Log.e("dr",dr);
        Log.e("pmr",pmr);
        Log.e("sur",sur);


        if(sur.equalsIgnoreCase("data.sa * (data.premium_rate - data.discount_rate) / 100000 * data.payment_mode_rate")){


            data =  sa * (Double.parseDouble(pr) - Double.parseDouble(dr)) / 100000.00 * Double.parseDouble(pmr);

            //Integer.valueOf(dr)  Integer.valueOf(pmr)
            Log.e("wtf",data+"");
        }else if(sur.equalsIgnoreCase("data.premium_rate * data.payment_mode_rate")){
            data = Double.parseDouble(pr) * Double.parseDouble(pmr);
        }


        cursor.close();
        closeDatabase();

        return data;
    }


}
