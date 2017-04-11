package cf.parks.a1note.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import cf.parks.a1note.R;
import cf.parks.a1note.dbdb.NotesDAO;
import cf.parks.a1note.entities.NoteD;

public class ViewNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        if(hasIntention()) {
            fillTheView(getNote());
        }
    }

    private void fillTheView(NoteD note) {
        TextView title = (TextView) findViewById(R.id.view_title);
        TextView tnote = (TextView) findViewById(R.id.view_note);
        title.setText(note.getTitle());
        tnote.setText(note.getData());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(hasIntention())
            getMenuInflater().inflate(R.menu.view_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(hasIntention()) {
            if (item.getItemId() == R.id.view_edit) {
                Intent intention = new Intent(ViewNoteActivity.this, NewNoteActivity.class);
                intention.putExtra("Note", getNote());
                startActivity(intention);
                finish();
            } else if (item.getItemId() == R.id.view_share) {
                Intent intention = new Intent(Intent.ACTION_SEND);
                intention.setType("text/plain");
                intention.putExtra(Intent.EXTRA_TEXT, getNote().getTitle() + ": \r\n\r\n" + getNote().getData());
                Intent chooser = Intent.createChooser(intention, "Share Note Via..");
                if (intention.resolveActivity(getPackageManager()) != null)
                    startActivity(chooser);

            } else if (item.getItemId() == R.id.view_delete) {
                NotesDAO dao = new NotesDAO(ViewNoteActivity.this);
                dao.remove(getNote());
                dao.close();
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private NoteD getNote() { return (NoteD) getIntent().getSerializableExtra("Note"); }

    private boolean hasIntention() { return getIntent().hasExtra("Note"); }
}
