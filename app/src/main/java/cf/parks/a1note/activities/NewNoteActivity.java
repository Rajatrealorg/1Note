package cf.parks.a1note.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import cf.parks.a1note.R;
import cf.parks.a1note.dbdb.NotesDAO;
import cf.parks.a1note.entities.NoteD;

public class NewNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        if(hasIntentionToUpdate()){
            NoteD noted = getOriginalNoteToUpdate();
            noted.fillInTheNewNote(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add_note_menu_check) {
            EditText title = (EditText) findViewById(R.id.add_note_title);
            EditText tnote = (EditText) findViewById(R.id.add_note_tnote);
            String t1 = title.getText().toString();
            String t2 = tnote.getText().toString();
            NoteD newNote = new NoteD(t1,t2);

            NotesDAO dao = new NotesDAO(this);
            if(hasIntentionToUpdate())
                dao.update(newNote, getOriginalNoteToUpdate().getId());
            else
                dao.insert(newNote);
            dao.close();

            Toast.makeText(NewNoteActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean hasIntentionToUpdate() {
        return getIntent().hasExtra("Note");
    }

    private NoteD getOriginalNoteToUpdate() { return (NoteD) getIntent().getSerializableExtra("Note"); }
}
