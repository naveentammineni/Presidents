package naveent.com.presidents;

/**
 * Created by NaveenT on 9/10/14.
 */
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PresidentsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_PRESIDENT_NAME, MySQLiteHelper.COLUMN_PRESIDENT_PIC,
            };

    public PresidentsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public President createPresident(String name, byte[] presidentPic, String presidentBio) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_PRESIDENT_NAME, name);
        values.put(MySQLiteHelper.COLUMN_PRESIDENT_PIC, presidentPic);
        values.put(MySQLiteHelper.COLUMN_PRESIDENT_BIO, presidentBio);
        long insertId = database.insert(MySQLiteHelper.TABLE_PRESIDENTS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_PRESIDENTS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToPosition(0);
        President newPresident = cursorToPresident(cursor);
        cursor.close();
        return newPresident;
    }

    public void deleteComment(President president) {
        long id = president.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_PRESIDENTS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<President> getAllPresidents() {
        List<President> presidents = new ArrayList<President>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_PRESIDENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            President president = cursorToPresident(cursor);
            presidents.add(president);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return presidents;
    }

    private President cursorToPresident(Cursor cursor) {
        President president = new President();
        president.setId(cursor.getLong(0));
        president.setName(cursor.getString(1));
        ByteArrayInputStream imageStream = new ByteArrayInputStream(cursor.getBlob(2));
        Bitmap theImage= BitmapFactory.decodeStream(imageStream);
        //president.setImage(theImage);
        president.setBio(cursor.getString(3));
        return president;
    }
}