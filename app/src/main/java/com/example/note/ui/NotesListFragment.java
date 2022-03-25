package com.example.note.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.note.R;
import com.example.note.data.InMemoryRepoImp;
import com.example.note.data.Note;
import com.example.note.data.Repo;
import com.example.note.recycler.NoteAdapter;

public class NotesListFragment extends Fragment implements NoteAdapter.onNoteClickListener {

    private RecyclerView list;
    private Repo repo = InMemoryRepoImp.getInstance();
    private NoteAdapter adapter;

    private FragmentManager manager;

    public static NotesListFragment getInstance(Note note) {

        NotesListFragment fragment = new NotesListFragment();
        Bundle args = new Bundle();
        args.putSerializable(Note.NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notes_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        list = view.findViewById(R.id.list);

        Bundle args = getArguments();
        if (args != null) {
            repo.update((Note) args.getSerializable(Note.NOTE));
        }

        adapter = new NoteAdapter();
        adapter.setOnNoteClickListener(this);
        adapter.setNotes(repo.getAll());

        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(view.getContext()));

        manager = requireActivity().getSupportFragmentManager();

    }

    @Override
    public void onNoteClick(Note note) {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            manager
                    .beginTransaction()
                    .replace(R.id.landscape_notes_edit_fragment_holder, EditNoteFragment.getInstance(note))
                    .addToBackStack(null)
                    .commit();
        } else {

            manager
                    .beginTransaction()
                    .replace(R.id.portrait_fragment_holder, EditNoteFragment.getInstance(note))
                    .addToBackStack(null)
                    .commit();

        }

    }

}