package naveent.com.presidents;

/**
 * Created by NaveenT on 9/10/14.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_PRESIDENTS = "presidents";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRESIDENT_NAME = "name";
    public static final String COLUMN_PRESIDENT_PIC = "president_pic";
    public static final String COLUMN_PRESIDENT_BIO = "president_bio";

    private static final String DATABASE_NAME = "presidents.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_PRESIDENTS + "(" + COLUMN_ID
            + " integer primary key autoincrement , " + COLUMN_PRESIDENT_NAME
            + " text not null , "+COLUMN_PRESIDENT_PIC + " BLOB , "+COLUMN_PRESIDENT_BIO +" text );";
    Context dbContext;
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        dbContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        //insertRecords();
    }

    public void insertRecords() {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", "George Washington");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = ((BitmapDrawable)dbContext.getResources().getDrawable(R.drawable.georgewashington)).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] photo = baos.toByteArray();
        values.put("president_pic",photo);
        values.put("president_bio","George Washington (February 22, 1732 [O.S. February 11, 1731][Note 1][Note 2] – December 14, 1799) was the first President of the United States (1789–1797), the Commander-in-Chief of the Continental Army during the American Revolutionary War, and one of the Founding Fathers of the United States.[3] He presided over the convention that drafted the United States Constitution, which replaced the Articles of Confederation and remains the supreme law of the land.");
        database.insert("student", null, values);
        database.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRESIDENTS);
        onCreate(db);
    }

}
