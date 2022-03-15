package com.example.note.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Adapter;

import com.example.note.R;
import com.example.note.data.Note;
import com.example.note.recycler.NoteAdapter;

public class NotesActivity extends AppCompatActivity implements NoteAdapter.onNoteClickListener {

    private FragmentManager manager;
    private Note note;

    @Override
    public void onNoteClick(Note note) {
        this.note = note;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        manager = getSupportFragmentManager();

        if (savedInstanceState == null) {

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

                manager
                        .beginTransaction()
                        .replace(R.id.landscape_notes_list_fragment_holder, new NotesListFragment())
                        .commit();

            } else {

                manager
                        .beginTransaction()
                        .replace(R.id.portrait_fragment_holder, new NotesListFragment())
                        .commit();

            }

        } else {

            this.note = (Note) savedInstanceState.getSerializable(Note.NOTE);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

                manager
                        .beginTransaction()
                        .replace(R.id.landscape_notes_list_fragment_holder, new NotesListFragment())
                        .commit();
            /*
                manager
                        .beginTransaction()
                        .replace(R.id.landscape_notes_edit_fragment_holder, EditNoteFragment.getInstance(this.note))
                        .commit();
            */
            } else {

                manager
                        .beginTransaction()
                        .replace(R.id.portrait_fragment_holder, new NotesListFragment())
                        .commit();
            }

        }


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Note.NOTE, this.note);
    }
}