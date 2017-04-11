package cf.parks.a1note.dbdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cf.parks.a1note.entities.NoteD;

import java.util.ArrayList;
import java.util.List;

public class NotesDAO extends SQLiteOpenHelper{
    private final String TABLE_NAME = "mynotes";
    public NotesDAO(Context context) {
        super(context, "DBNotesRKS", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "
                + TABLE_NAME
                + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "title TEXT NOT NULL, "
                + "data TEXT"
                + ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public List<NoteD> listAll() {
        List<NoteD> notes = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String tnote = cursor.getString(cursor.getColumnIndex("data"));
            NoteD n = new NoteD(id, title, tnote);
            notes.add(n);
        }
        cursor.close();
        database.close();
        return notes;
    }

    public void insert(NoteD newNote) {
        ContentValues data = newNote.toContentValues();
        SQLiteDatabase database = getWritableDatabase();
        database.insert(TABLE_NAME, null, data);
    }

    public void remove(NoteD rmNote) {
        SQLiteDatabase database = getWritableDatabase();
        String[] params = {"" + rmNote.getId()};
        database.delete(TABLE_NAME, "id = ?", params);
    }

    public void update(NoteD newNote, int originalId) {
        ContentValues data = newNote.toContentValues();
        SQLiteDatabase database = getWritableDatabase();
        String[] params = {"" + originalId};
        database.update(TABLE_NAME, data, "id = ?", params);
    }

}
