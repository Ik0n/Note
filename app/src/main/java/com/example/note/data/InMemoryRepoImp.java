package com.example.note.data;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRepoImp implements Repo {

    private static InMemoryRepoImp repo;
    private ArrayList<Note> notes = new ArrayList<>();

    private InMemoryRepoImp() {
        init();
    }

    private void init() {
        create(new Note("title 1", "description 1"));
        create(new Note("title 2", "description 2"));
        create(new Note("title 3", "description 3"));
        create(new Note("title 4", "description 4"));
        create(new Note("title 5", "description 5"));
        create(new Note("title 6", "description 6"));
        create(new Note("title 7", "description 7"));
        create(new Note("title 8", "description 8"));
        create(new Note("title 9", "description 9"));
        create(new Note("title 10", "description 10"));
        create(new Note("title 11", "description 11"));
        create(new Note("title 12", "description 12"));
        create(new Note("title 13", "description 13"));
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
                break;
            }
        }
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId().equals(id)) {
                notes.remove(i);
                break;
            }
        }
    }

    @Override
    public List<Note> getAll() {
        return notes;
    }
}
