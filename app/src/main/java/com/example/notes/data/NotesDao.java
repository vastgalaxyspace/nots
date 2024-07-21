package com.example.notes.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notes.model.Notes;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Notes notes);

    @Update
    void update(Notes note);

    @Delete
    void delete(Notes note);

    @Query("SELECT * FROM Notes_table")
    LiveData<List<Notes>> getAll();

    @Query("SELECT * FROM Notes_table WHERE Noid IN (:noteIds)")
    List<Notes> loadAllByIds(int[] noteIds);

    @Query("SELECT * FROM Notes_table WHERE Noid = :noteId LIMIT 1")
    Notes getNoteById(int noteId);

    @Query("DELETE FROM Notes_table")
    void deleteAll();

    @Query("SELECT * FROM Notes_table WHERE title LIKE :title LIMIT 1")
    Notes findByName(String title);
}