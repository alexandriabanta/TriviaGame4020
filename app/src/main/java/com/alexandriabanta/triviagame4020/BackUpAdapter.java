package com.alexandriabanta.triviagame4020;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BackUpAdapter extends RecyclerView.Adapter<BackUpAdapter.ViewHolder> {
    private ArrayList<Score> scores;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView score;
        public TextView questions;
        public TextView cat;
        public TextView diff;
        public TextView typer;


        public ViewHolder(View itemView) {
            super(itemView);
            score = itemView.findViewById(R.id.score_textView);
            questions = itemView.findViewById(R.id.numQuestions_textview);
            cat = itemView.findViewById(R.id.cat_textview);
            diff = itemView.findViewById(R.id.diff_textview);
            typer = itemView.findViewById(R.id.ty_textview);
        }
    }

    public BackUpAdapter(ArrayList<Score> exampleList) {
        scores = exampleList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.backup_listview, parent, false);
        ViewHolder evh = new ViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Score currentItem = scores.get(position);

        holder.score.setText("Score: " + currentItem.getScore());
        //holder.score.setText(currentItem.getScore()); // problem
        holder.questions.setText("Number of Questions: " + currentItem.getNum_of_questions());
        //holder.questions.setText(currentItem.getNum_of_questions()); // problem
        holder.cat.setText("Category: " + currentItem.getCategory());
        holder.diff.setText("Difficulty: " + currentItem.getDifficulty());
        holder.typer.setText("Quiz Type: " + currentItem.getType());
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }
}