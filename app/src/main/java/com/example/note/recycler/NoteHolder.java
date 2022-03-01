package com.example.note.recycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note.R;
import com.example.note.data.Note;

public class NoteHolder extends RecyclerView.ViewHolder {

    private TextView tittle;
    private TextView description;

    private Note note;

    public NoteHolder(@NonNull View itemView, NoteAdapter.onNoteClickListener listener) {
        super(itemView);
        tittle = itemView.findViewById(R.id.note_title);
        description = itemView.findViewById(R.id.note_description);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onNoteClick(note);
            }
        });

    }

    void bind(Note note) {
        this.note = note;
        tittle.setText(note.getTitle());
        description.setText(note.getDescription());
    }
}
