package com.example.notes.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.notes.model.Notes;
import com.example.notes.util.NoteRoomDatabase;

import java.util.List;

public class NoteRepository {
    private NotesDao notesDao;
    private LiveData<List<Notes>> livenotes;

    public NoteRepository(Application application){
        NoteRoomDatabase ndb=NoteRoomDatabase.getDatabase(application);
        notesDao= ndb.notesDao();
        livenotes=notesDao.getAll();

    }

public LiveData<List<Notes>> getLivenotes(){
        return livenotes;
}
public void insert(Notes notes){
    NoteRoomDatabase.databaseWriterExecutor.execute(()->{
        notesDao.insert(notes);
    });
}
public void update(Notes notes){
        NoteRoomDatabase.databaseWriterExecutor.execute(()->{
            notesDao.update(notes);
        });
}

public void delete(Notes notes){
        NoteRoomDatabase.databaseWriterExecutor.execute(()->{
            notesDao.delete(notes);
        });
}
}
