package cf.parks.a1note.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import cf.parks.a1note.R;
import cf.parks.a1note.dbdb.LogPassDAO;
import cf.parks.a1note.dbdb.NotesDAO;
import cf.parks.a1note.entities.NoteD;

public class MainActivity extends AppCompatActivity {

    LinearLayout empty_view;
    String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        empty_view = (LinearLayout) findViewById(R.id.main_empty_view);
        empty_view.setVisibility(View.INVISIBLE);

        registerForContextMenu(getNoteList());

        getNoteList().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> noteList, View item, int position, long id) {
                NoteD note = (NoteD) noteList.getItemAtPosition(position);
                Intent intention = new Intent(MainActivity.this, ViewNoteActivity.class);
                intention.putExtra("Note", note);
                startActivity(intention);
            }
        });

        Button addBtn = (Button) findViewById(R.id.main_add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewNoteActivity.class));
            }
        });

        getUserDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listAllNotes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intention;
        if(item.getItemId() == R.id.main_change_pass) {
            if(password == null)
                Toast.makeText(MainActivity.this, "No Password Set Yet !!", Toast.LENGTH_SHORT).show();
            else {
                intention = new Intent(MainActivity.this, SetPasswordActivity.class);
                startActivity(intention);
                finish();
            }
        }
        if(item.getItemId() == R.id.main_about) {
            startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void getUserDetails() {
        LogPassDAO lpd = new LogPassDAO(MainActivity.this);
        password = lpd.getAppPassword();
        lpd.close();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        NoteD note = (NoteD) getNoteList().getItemAtPosition(info.position);

        showContextMenuForNotes(menu, note);
    }

    private void showContextMenuForNotes(ContextMenu menu, final NoteD note) {
        MenuItem edit = menu.add("Edit");
        MenuItem remove = menu.add("Remove");

        edit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intention = new Intent(MainActivity.this, NewNoteActivity.class);
                intention.putExtra("Note", note);
                startActivity(intention);
                return true;
            }
        });

        remove.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                NotesDAO dao = new NotesDAO(MainActivity.this);
                dao.remove(note);
                dao.close();
                listAllNotes();
                return true;
            }
        });
    }

    private void listAllNotes() {
        NotesDAO dao = new NotesDAO(this);
        List<NoteD> savedNotes = dao.listAll();
        dao.close();
        if(savedNotes.size() == 0) {
            getNoteList().setVisibility(View.INVISIBLE);
            empty_view.setVisibility(View.VISIBLE);
        }
        else {
            getNoteList().setVisibility(View.VISIBLE);
            empty_view.setVisibility(View.INVISIBLE);
        }

        // TODO: Change Text Color According to Background in the List
        ArrayAdapter<NoteD> adapter = new ArrayAdapter<>(this, R.layout.my_simple_list_1, savedNotes);
        getNoteList().setAdapter(adapter);
    }

    private ListView getNoteList() {
        return (ListView) findViewById(R.id.main_list);
    }
}
