package com.microblink.blinkid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sodajune on 6/20/16.
 */
public class DBHelper extends SQLiteOpenHelper {


    public static final String DBNAME = "db.sqlite";
    public static final String DBLOCATION = "/data/data/com.microblink.blinkid/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private Value value;
    private String data;

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

    public String ComputeData(String gender, String age) {


        openDatabase();
      Cursor cursor = mDatabase.rawQuery("SELECT pr.product_plan_code, pr.gender_insured, pr.age_insured,  pr.occupation_level_insured, pr.rate as premium_rate, dr.lower_sa,  dr.rate as discount_rate, pmr.rate as payment_mode_rate, cp.formula as calculation_pattern_formula FROM  premium_rate pr, payment_mode_rate pmr, calculation_pattern cp LEFT OUTER JOIN discount_rate dr on pr.product_plan_code = dr.product_code WHERE pr.product_plan_code = ? AND (pr.gender_insured = ? OR pr.gender_insured = '' OR pr.gender_insured IS NULL) AND (cast(pr.age_insured as real) = cast(? AS REAL) OR pr.age_insured = '' OR pr.age_insured IS NULL) AND (pr.occupation_level_insured = ? OR pr.occupation_level_insured = '' OR pr.occupation_level_insured IS NULL) AND pmr.code = ? AND pr.calculation_pattern_code = cp.code AND (dr.lower_sa <= cast(? AS REAL) OR dr.lower_sa IS NULL) ORDER BY dr.lower_sa DESC LIMIT 1", new String[]{"05PN99", gender, age, "1", "12", "500000"});
        cursor.moveToFirst();

//gst value
        if (cursor.getCount() == 1) {
            data = cursor.getString(8);
            Log.e("ValueT", data);

        }

        cursor.close();
        closeDatabase();

        return data;
    }


}
