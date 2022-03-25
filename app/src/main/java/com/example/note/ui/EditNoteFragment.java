package com.example.note.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.note.R;
import com.example.note.data.Note;

public class EditNoteFragment extends Fragment {

    private Note note;

    private EditText title;
    private EditText description;

    private Button save;

    private FragmentManager manager;

    public interface Controller {
        void saveButtonPressed(Note note);
    }

    public static EditNoteFragment getInstance(Note note) {

        EditNoteFragment fragment = new EditNoteFragment();
        Bundle args = new Bundle();
        args.putSerializable(Note.NOTE, note);
        fragment.setArguments(args);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_note_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        title = view.findViewById(R.id.edit_note_title);
        description = view.findViewById(R.id.edit_note_description);
        save = view.findViewById(R.id.edit_button_save);

        if (getArguments() != null) {
            note = (Note) getArguments().getSerializable(Note.NOTE);
        }

        manager = requireActivity().getSupportFragmentManager();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                note = (Note) getArguments().getSerializable(Note.NOTE);
                note.setTitle(title.getText().toString());
                note.setDescription(description.getText().toString());

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

                    manager
                            .beginTransaction()
                            .replace(R.id.landscape_notes_list_fragment_holder, NotesListFragment.getInstance(note))
                            .commit();

                    manager
                            .beginTransaction()
                            .replace(R.id.landscape_notes_edit_fragment_holder, new Fragment())
                            .commit();

                } else {

                    manager
                            .beginTransaction()
                            .replace(R.id.portrait_fragment_holder, NotesListFragment.getInstance(note))
                            .commit();
                }

            }
        });

        init(note);

    }

    public void passMessage(Note note){
        init(note);
    }

    private void init(Note note) {

        Bundle args = getArguments();

        if (args != null) {

            if (note != null) {

                note = (Note) args.getSerializable(Note.NOTE);

            } else {

                args.putSerializable(Note.NOTE, note);
                setArguments(args);
            }

            title.setText(note.getTitle());
            description.setText(note.getDescription());

        }

    }

}
