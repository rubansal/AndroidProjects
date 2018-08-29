package com.codingblocks.recorder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String TABLE_NAME = "recordings";

    public DBHelper(Context context) {
        super(context, "recordings.db", null, 1);
        this.context = context;
    }

    public static abstract class DBHelperItem implements BaseColumns {
        public static final String RECORDING_NAME = "recording_name";
        public static final String RECORDING_FILE_PATH = "file_path";
        public static final String RECORDING_LENGTH = "length";
        public static final String RECORDING_TIME = "time";
    }

    String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    DBHelperItem._ID + " INTEGER PRIMARY KEY" + "," +
                    DBHelperItem.RECORDING_NAME + " TEXT" + "," +
                    DBHelperItem.RECORDING_FILE_PATH + " TEXT" + "," +
                    DBHelperItem.RECORDING_LENGTH + " INTEGER " + "," +
                    DBHelperItem.RECORDING_TIME + " INTEGER " + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Record getItemAt(int position){
        SQLiteDatabase db=getReadableDatabase();
        String[] projection={
        DBHelperItem._ID,
        DBHelperItem.RECORDING_NAME,
        DBHelperItem.RECORDING_FILE_PATH,
        DBHelperItem.RECORDING_LENGTH,
        DBHelperItem.RECORDING_TIME
        };
        Cursor c=db.query(TABLE_NAME,
                projection,
                null,null,
                null,null,
                null,null);
        if(c.moveToPosition(position)){
            Record item=new Record();
            item.setId(c.getInt(c.getColumnIndex(DBHelperItem._ID)));
            item.setName(c.getString(c.getColumnIndex(DBHelperItem.RECORDING_NAME)));
            item.setPath(c.getString(c.getColumnIndex(DBHelperItem.RECORDING_FILE_PATH)));
            item.setLength(c.getInt(c.getColumnIndex(DBHelperItem.RECORDING_LENGTH)));
            item.setDate(c.getLong(c.getColumnIndex(DBHelperItem.RECORDING_TIME)));
            c.close();
            return item;
        }
        return null;
    }

    public long addRecording(String recordingName,String filePath,long length){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(DBHelperItem.RECORDING_NAME,recordingName);
        cv.put(DBHelperItem.RECORDING_FILE_PATH,filePath);
        cv.put(DBHelperItem.RECORDING_LENGTH,length);
        cv.put(DBHelperItem.RECORDING_TIME, System.currentTimeMillis());
        long row=db.insert(TABLE_NAME,null,cv);
        return row;
    }

    public int getCount(){
        SQLiteDatabase db=getReadableDatabase();
        String[] projection={DBHelperItem._ID};
        Cursor c=db.query(TABLE_NAME,
                projection,
                null,null,
                null,null,null
        );
        int count=c.getCount();
        c.close();
        return count;
    }
}
