package com.example.note.data;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.note.NotesApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InMemoryRepoImp implements Repo {

    private static InMemoryRepoImp repo;
    private ArrayList<Note> notes = new ArrayList<>();

    private SharedPreferences preferences;
    private static final String NOTES_KEY = "NOTES_KEY";
    private Gson gson = new Gson();

    private InMemoryRepoImp() {
        preferences = PreferenceManager.getDefaultSharedPreferences(NotesApplication.getInstance());
        //init();
    }

    private void init() {
        create(new Note("title 1", "description 1", new Date(System.currentTimeMillis())));
        create(new Note("title 2", "description 2", new Date(System.currentTimeMillis())));
        create(new Note("title 3", "description 3", new Date(System.currentTimeMillis())));
        create(new Note("title 4", "description 4", new Date(System.currentTimeMillis())));
        create(new Note("title 5", "description 5", new Date(System.currentTimeMillis())));
        create(new Note("title 6", "description 6", new Date(System.currentTimeMillis())));
        create(new Note("title 7", "description 7", new Date(System.currentTimeMillis())));
        create(new Note("title 8", "description 8", new Date(System.currentTimeMillis())));
        create(new Note("title 9", "description 9", new Date(System.currentTimeMillis())));
        create(new Note("title 10", "description 10", new Date(System.currentTimeMillis())));
        create(new Note("title 11", "description 11", new Date(System.currentTimeMillis())));
        create(new Note("title 12", "description 12", new Date(System.currentTimeMillis())));
        create(new Note("title 13", "description 13", new Date(System.currentTimeMillis())));
    }

    public static InMemoryRepoImp getInstance() {
        if (repo == null) {
            repo = new InMemoryRepoImp();
        }

        return repo;
    }

    private int counter = 0;

    @Override
    public int create(Note note) {
        int id = counter++;
        note.setId(id);
        notes.add(note);

        preferences
                .edit()
                .putString(NOTES_KEY, gson.toJson(notes))
                .apply();

        return id;
    }

    @Override
    public Note read(int id) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId() == id) {
                return notes.get(i);
            }
        }

        return null;
    }

    @Override
    public void update(Note note) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId().equals(note.getId())) {
                notes.set(i, note);

                preferences
                        .edit()
                        .putString(NOTES_KEY, gson.toJson(notes))
                        .apply();

                break;
            }
        }
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId().equals(id)) {
                notes.remove(i);
                
                preferences
                        .edit()
                        .putString(NOTES_KEY, gson.toJson(notes))
                        .apply();

                break;
            }
        }
    }

    @Override
    public List<Note> getAll() {

        String data = preferences.getString(NOTES_KEY, "{}");

        try {

            notes = gson.fromJson(
                    data,
                    new TypeToken<List<Note>>(){}.getType()
            );

        } catch (Exception e) {

            Log.d("happy", e.getMessage());

        }

        if (notes == null)
            notes = new ArrayList<>();

        return notes;
    }
}
