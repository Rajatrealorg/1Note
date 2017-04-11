package cf.parks.a1note.entities;

import android.content.ContentValues;
import android.widget.EditText;

import java.io.Serializable;

import cf.parks.a1note.R;
import cf.parks.a1note.activities.NewNoteActivity;

public class NoteD implements Serializable {

    private final String title;
    private final String tnotes;
    private int id;

    public NoteD(String title, String tnotes) {
        this.title = title;
        this.tnotes = tnotes;
    }

    public NoteD(int id, String title, String tnotes) {
        this.id = id;
        this.title = title;
        this.tnotes = tnotes;
    }

    public String getTitle() {
        return title;
    }

    public String getData() {
        return tnotes;
    }

    @Override
    public String toString() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void fillInTheNewNote(NewNoteActivity activity) {
        EditText note_title = (EditText) activity.findViewById(R.id.add_note_title);
        EditText note_tNote = (EditText) activity.findViewById(R.id.add_note_tnote);
        note_title.setText(title);
        note_tNote.setText(tnotes);
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("data", tnotes);
        return cv;
    }
}
