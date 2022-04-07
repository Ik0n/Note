package com.example.note.recycler;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note.PopupMenuItemClicker;
import com.example.note.R;
import com.example.note.data.Note;

import java.text.SimpleDateFormat;

public class NoteHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {

    private TextView tittle;
    private TextView description;
    private TextView date;
    private ImageView noteMenu;

    private PopupMenu popupMenu;

    private Note note;

    private PopupMenuItemClicker popupMenuItemClicker;

    public NoteHolder(@NonNull View itemView, NoteAdapter.onNoteClickListener listener, PopupMenuItemClicker popupMenuItemClicker) {
        super(itemView);
        tittle = itemView.findViewById(R.id.note_title);
        description = itemView.findViewById(R.id.note_description);
        date = itemView.findViewById(R.id.note_date);
        noteMenu = itemView.findViewById(R.id.more_edit);

        this.popupMenuItemClicker = popupMenuItemClicker;

        popupMenu = new PopupMenu(itemView.getContext(), noteMenu);

        popupMenu.inflate(R.menu.context);

        noteMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.show();
            }
        });

        popupMenu.setOnMenuItemClickListener(this);

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
        date.setText(new SimpleDateFormat("dd.MM.yy").format(note.getDate()));
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.context_delete:
                popupMenuItemClicker.delete(note, getAdapterPosition());
                return true;

        }
        return false;
    }
}
