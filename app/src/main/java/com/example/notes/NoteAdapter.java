package com.example.notes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.model.Notes;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Notes> notesList;
    private List<Notes> originalList;

    public NoteAdapter(List<Notes> notesList) {
        this.notesList = notesList;
        this.originalList = new ArrayList<>(notesList);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.noteview, parent, false);
        return new NoteViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        final Notes note = notesList.get(position);
        holder.title.setText(note.getTitle());
        holder.content.setText(note.getMessage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MakeNote.class);
                intent.putExtra("title", note.getTitle());
                intent.putExtra("content", note.getMessage());
                intent.putExtra("id", note.getNoid());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void setNotes(List<Notes> notesList) {
        this.notesList = notesList;
        this.originalList = new ArrayList<>(notesList);
        notifyDataSetChanged();
    }

    public void filter(String text) {
        notesList.clear();
        if(text.isEmpty()){
            notesList.addAll(originalList);
        } else{
            text = text.toLowerCase();
            for(Notes item: originalList){
                if(item.getTitle().toLowerCase().contains(text)){
                    notesList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView title, content;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.noteTitleView);
            content = itemView.findViewById(R.id.noteBodyView);
        }
    }
}