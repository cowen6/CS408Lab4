package edu.jsu.mcis.cs408.memopad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final String TABLE_MEMOS = "memos";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MEMO = "memo";

    public static final String QUERY_CREATE_TABLE = "CREATE TABLE " + TABLE_MEMOS + " (" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_MEMO + " text)";
    public static final String QUERY_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_MEMOS;

    public static final String QUERY_GET_ALL = "SELECT * FROM " + TABLE_MEMOS;
    public static final String QUERY_GET = "SELECT * FROM " + TABLE_MEMOS + " WHERE " + COLUMN_ID + " = ?";

    public DatabaseHandler(Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(QUERY_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(QUERY_DELETE_TABLE);
        onCreate(db);

    }

    public void addMemo(Memo m) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_MEMO, m.getMemo());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_MEMOS, null, values);
        db.close();

    }

    public void deleteAllMemos() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_MEMOS, null, null);

    }

    public void deleteMemo(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_MEMOS, COLUMN_ID + "=?", new String[]{ String.valueOf(id) });

    }

    public Memo getMemo(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(QUERY_GET, new String[]{ String.valueOf(id) });
        Memo m = null;

        if (cursor.moveToFirst()) {
            int newId = cursor.getInt(0);
            String newMemo = cursor.getString(1);
            m = new Memo(newId, newMemo);
            cursor.close();
        }

        db.close();

        return m;

    }

    public ArrayList<Memo> getAllMemosAsList() {

        ArrayList<Memo> allMemos = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(QUERY_GET_ALL, null);

        if (cursor.moveToFirst()) {

            cursor.moveToFirst();

            do {
                int id = cursor.getInt(0);
                allMemos.add( getMemo(id) );
            }
            while ( cursor.moveToNext() );

        }

        cursor.close();
        db.close();

        return allMemos;

    }

}