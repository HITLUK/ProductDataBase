package ru.myitschool.lesson20230214.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "product.db";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "products";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_COUNT = "count";

    private static final long NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_TITLE = 1;
    private static final int NUM_COLUMN_DESCRIPTION = 2;
    private static final int NUM_COLUMN_COUNT = 3;

    SQLiteDatabase database;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        database = getWritableDatabase();
    }


    public void add(ProductData data) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, data.getName());
        cv.put(COLUMN_DESCRIPTION, data.getDescription());
        cv.put(COLUMN_COUNT, data.getCount());
        database.insert(TABLE_NAME, null, cv);
    }

    public ArrayList<ProductData> getAll() {
        ArrayList<ProductData> result = new ArrayList<>();
        Cursor cursor = database.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        if (cursor.isAfterLast()) return new ArrayList<>();
        cursor.moveToFirst();
        do {
            result.add(new ProductData(
                    cursor.getString(NUM_COLUMN_TITLE),
                    cursor.getString(NUM_COLUMN_DESCRIPTION)
            ));
        } while (cursor.moveToNext());
        cursor.close();
        return result;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_COUNT + " INTEGER); ";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void deleteProduct(ProductData data) {
        Log.d("DeleteSQLData", " " + data.getName() + " " + database.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(data.getId())}));

    }

    public void update(ProductData data) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, data.getName());
        cv.put(COLUMN_DESCRIPTION, data.getDescription());
        cv.put(COLUMN_COUNT, data.getCount());
        database.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(data.getId())});
    }

    @Override
    public synchronized void close() {
        super.close();
        database.close();
    }


}


