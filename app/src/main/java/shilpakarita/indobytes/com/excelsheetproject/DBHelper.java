package shilpakarita.indobytes.com.excelsheetproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by indobytes21 on 8/29/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;

    private static final String DATABASE_NAME ="exceloffline";
    // Contacts table name
    private static final String TABLE_SHOPS = "dailytasks";
    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SH_TASK = "task";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     String CREATE_MAIN_TABLE="CREATE TABLE "
             + TABLE_SHOPS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
             + " TEXT," + KEY_SH_TASK + " INTEGER," +")";
        db.execSQL(CREATE_MAIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS" + TABLE_SHOPS);
// Creating tables again
        onCreate(db);

    }
}
