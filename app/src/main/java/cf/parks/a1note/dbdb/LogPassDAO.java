package cf.parks.a1note.dbdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LogPassDAO extends SQLiteOpenHelper{
    private final String TABLE_NAME = "main_user";

    public LogPassDAO(Context context) {
        super(context, "LogPass", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "
                + TABLE_NAME
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "password VARCHAR(20) "
                +");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    public String getAppPassword() {
        String password = null;
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id=1", null);
        if(c.moveToNext())
            password = c.getString(c.getColumnIndex("password"));
        c.close();
        return password;
    }

    public void setPassword(String password) {
        ContentValues cv = new ContentValues();
        cv.put("password", password);
        SQLiteDatabase database = getWritableDatabase();
        if(getAppPassword() == null)
            database.insert(TABLE_NAME, null, cv);
        else
            database.update(TABLE_NAME, cv, "id=1", null);
    }

}
