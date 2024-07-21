package com.example.notes.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notes.data.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository repository;
    public final LiveData<List<Notes>> allnotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allnotes = repository.getLivenotes();

    }
    public LiveData<List<Notes>> getAllnotes(){
        return  allnotes;
    }
    public void insert(Notes notes) {
        repository.insert(notes);
    }

    public void update(Notes notes) {
        repository.update(notes);
    }

    public void delete(Notes notes) {
        repository.delete(notes);
    }

    public LiveData<List<Notes>> getAll() {
        return allnotes;
    }
}
