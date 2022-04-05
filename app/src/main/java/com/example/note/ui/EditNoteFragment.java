package com.example.note.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.note.R;
import com.example.note.data.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditNoteFragment extends Fragment {

    private Note note;

    private EditText title;
    private EditText description;
    private EditText date;

    private Button save;

    private FragmentManager manager;

    private Date formatDate;

    private AlertDialog.Builder dialog = null;

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
        date = view.findViewById(R.id.edit_note_date);
        save = view.findViewById(R.id.edit_button_save);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_datepicker, (ViewGroup) view, false);
        DatePicker datePicker = dialogView.findViewById(R.id.date_picker);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog == null) {
                    dialog = new AlertDialog.Builder(view.getContext())
                            .setTitle("Chose date")
                            .setView(dialogView)
                            .setCancelable(true)
                            .setPositiveButton("Chose", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    formatDate = new Date(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                                    date.setText(new SimpleDateFormat("dd.MM.yy").format(formatDate));
                                }
                            })
                            .setNeutralButton("Cancel", null);
                }

                dialog.show();
            }
        });

        if (getArguments() != null) {
            note = (Note) getArguments().getSerializable(Note.NOTE);
        }

        manager = requireActivity().getSupportFragmentManager();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.popBackStack();
                if (getArguments() != null) {
                    note = (Note) getArguments().getSerializable(Note.NOTE);
                    note.setTitle(title.getText().toString());
                    note.setDescription(description.getText().toString());
                    note.setDate(formatDate);
                } else {
                    note = new Note(title.getText().toString(), description.getText().toString(), formatDate);
                }

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
            date.setText(new SimpleDateFormat("dd.MM.yy").format(note.getDate()));

        } else {

            if (note != null) {
                args.putSerializable(Note.NOTE, note);
                setArguments(args);
            }

        }

    }

}
