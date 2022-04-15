package com.example.note.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.note.PopupMenuItemClicker;
import com.example.note.R;
import com.example.note.data.InMemoryRepoImp;
import com.example.note.data.Note;
import com.example.note.data.Repo;
import com.example.note.recycler.NoteAdapter;

public class NotesListFragment extends Fragment implements NoteAdapter.onNoteClickListener, PopupMenuItemClicker {

    private RecyclerView list;
    private Repo repo = InMemoryRepoImp.getInstance();
    private NoteAdapter adapter;

    private FragmentManager manager;

    public static final int PENDING_REQUEST_ID = 416;

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
            Note note = (Note) args.getSerializable(Note.NOTE);
            if (note.getId() != null) {
                repo.update((Note) args.getSerializable(Note.NOTE));
            } else {
                repo.create(note);
                showNotification(note);
            }

        }

        adapter = new NoteAdapter();
        adapter.setOnNoteClickListener(this);
        adapter.setPopupMenuItemClicker(this);
        adapter.setNotes(repo.getAll());

        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(view.getContext()));

        manager = requireActivity().getSupportFragmentManager();

    }

    private void showNotification(Note note) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireActivity(), NotesActivity.CHANNEL_ID);

        Intent mainActivityIntent = new Intent(requireActivity(), NotesActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                requireActivity(),
                PENDING_REQUEST_ID,
                mainActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        builder
                .setContentTitle(note.getTitle())
                .setSmallIcon(android.R.drawable.star_on)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentText(note.getDescription());

        NotificationManagerCompat.from(requireActivity()).notify(666, builder.build());
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

    @Override
    public void delete(Note note, int position) {
        repo.delete(note.getId());
        adapter.delete(repo.getAll(), position);
        Toast.makeText(requireActivity(), note.getTitle() + " is deleted.", Toast.LENGTH_SHORT).show();
    }
}