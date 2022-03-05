package com.example.note.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.note.R;
import com.example.note.data.Note;

public class EditNoteActivity extends AppCompatActivity {

    private Note note;

    private EditText title;
    private EditText description;
    private Button save;

    public static final int LIST_NOTE_REQUEST = 66;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        note = (Note) getIntent().getSerializableExtra(Note.NOTE);

        setContentView(R.layout.activity_edit_note);

        title = findViewById(R.id.edit_note_title);
        description = findViewById(R.id.edit_note_description);

        title.setText(note.getTitle());
        description.setText(note.getDescription());

        save = findViewById(R.id.edit_button_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                note.setTitle(title.getText().toString());
                note.setDescription(description.getText().toString());
                saveNote();
            }
        });
    }

    void saveNote() {
        Intent result = new Intent(this, NotesListActivity.class);
        result.putExtra(Note.NOTE, note);
        setResult(RESULT_OK, result);
        finish();
    }

}
