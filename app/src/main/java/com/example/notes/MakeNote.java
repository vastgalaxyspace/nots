package com.example.notes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.example.notes.model.Notes;
import com.example.notes.util.NoteRoomDatabase;
import com.google.android.material.snackbar.Snackbar;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

public class MakeNote extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText title, content;
    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_note);

        title = findViewById(R.id.title);
        content = findViewById(R.id.body);

        // Get the Intent that started this activity and extract the strings
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        if (id != -1) {
            // An existing note is being edited
            new Thread(() -> {
                Notes note = NoteRoomDatabase.getDatabase(getApplicationContext()).notesDao().getNoteById(id);
                runOnUiThread(() -> {
                    title.setText(note.getTitle());
                    content.setText(note.getMessage());
                });
            }).start();
        } else {
            String title1 = intent.getStringExtra("title");
            String content1 = intent.getStringExtra("content");

            // Use the data to populate your views
            title.setText(title1);
            content.setText(content1);
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> {
            Intent intent1 = new Intent(MakeNote.this, MainActivity.class);
            startActivity(intent1);
            finish();
        });

        SpeedDialView speedDialView = findViewById(R.id.speedDial);
        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_action1, R.drawable.baseline_add_24)
                        .setLabel("Add Image")
                        .create()
        );

        speedDialView.setOnActionSelectedListener(speedDialActionItem -> {
            if (speedDialActionItem.getId() == R.id.fab_action1) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (galleryIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(galleryIntent);
                }
                return true;
            } else {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutoolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save) {
            saveNote();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveNote() {
        String Title = title.getText().toString();
        String Content = content.getText().toString();

        Notes notes = new Notes();
        notes.setTitle(Title);
        notes.setMessage(Content);

        new Thread(() -> {
            NoteRoomDatabase db = NoteRoomDatabase.getDatabase(getApplicationContext()); // Add this line
            if (id == -1) {
                // A new note is being created
                db.notesDao().insert(notes);
            } else {
                // An existing note is being edited
                notes.setNoid(id);
                db.notesDao().update(notes);
            }
            runOnUiThread(() -> {
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Note saved successfully", Snackbar.LENGTH_SHORT);
                snackbar.show();
                snackbar.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        if (event == DISMISS_EVENT_TIMEOUT) {
                            Intent intent = new Intent(MakeNote.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            });
        }).start();
    }
}