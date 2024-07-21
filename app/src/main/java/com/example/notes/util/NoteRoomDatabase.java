package com.example.notes.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.notes.data.NotesDao;
import com.example.notes.model.Notes;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kotlin.jvm.Synchronized;

@Database(entities = {Notes.class},version = 1,exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class NoteRoomDatabase extends RoomDatabase {

    public abstract NotesDao notesDao();
    public static volatile NoteRoomDatabase INSTANCE;
    public static final int NUMBER_OF_THREADS=4;
   public static final ExecutorService databaseWriterExecutor=Executors.newFixedThreadPool(NUMBER_OF_THREADS);

   public static NoteRoomDatabase getDatabase (final Context context){
        if(INSTANCE==null){
            synchronized (NoteRoomDatabase.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),NoteRoomDatabase.class,"Notes_database")
                            .addCallback(sroomdatabase)
                            .build();


                }
            }
        }
        return (NoteRoomDatabase) INSTANCE;
    }
private static final RoomDatabase.Callback sroomdatabase=
        new RoomDatabase.Callback(){
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                databaseWriterExecutor.execute(() -> {
                    NotesDao notesDao = INSTANCE.notesDao();
                    notesDao.deleteAll();
                });
            }
        };


}
