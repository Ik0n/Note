package com.example.note.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.note.R;
import com.example.note.data.InMemoryRepoImp;
import com.example.note.data.Note;
import com.example.note.data.Repo;
import com.example.note.recycler.NoteAdapter;

public class NotesListActivity extends AppCompatActivity implements NoteAdapter.onNoteClickListener {

    private RecyclerView list;
    private Repo repo = InMemoryRepoImp.getInstance();
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        list = findViewById(R.id.list);

        adapter = new NoteAdapter();
        adapter.setOnNoteClickListener(this);
        adapter.setNotes(repo.getAll());

        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));

    }

    public static final int EDIT_NOTE_REQUEST = 66;


    @Override
    public void onNoteClick(Note note) {

        Intent editNoteIntent = new Intent(this, EditNoteActivity.class);
        editNoteIntent.putExtra(Note.NOTE, note);
        startActivityForResult(editNoteIntent, EDIT_NOTE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {

            Note note = (Note) data.getSerializableExtra(Note.NOTE);

            repo.update(note);

            adapter.setNotes(repo.getAll());

        }

    }
}