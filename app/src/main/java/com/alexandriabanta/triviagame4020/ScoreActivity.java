package com.alexandriabanta.triviagame4020;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity {

    private DbDataSource dataSource;
    ArrayList<Score> scores;
    CityAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreboard);
        Log.i("START", "Hola mundo");

        dataSource = new DbDataSource((getApplicationContext()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        dataSource.open();

        scores = dataSource.getAllComments();
        RecyclerView recyclerView = findViewById(R.id.scoreboardRecyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        cityAdapter = new CityAdapter();
        recyclerView.setAdapter(cityAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        dataSource.close();
    }


    class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView view;

        public CityViewHolder(TextView view) { // not itemView
            super(view);
            this.view = view;
            this.view.setOnClickListener(this);
        }

        public TextView getView()
        {
            return view;
        }

        @Override
        public void onClick(View view) {
            Score score = scores.get(getAdapterPosition());

            AlertDialog.Builder builder = new AlertDialog.Builder(ScoreActivity.this);
            builder.setMessage(Html.fromHtml("<html>" +
                    "<p><b>Score: </b> " + score.getScore() + "</p>" +
                    "<p><b>Number of Questions: </b> " + score.getNum_of_questions() + "</p>" +
                    "<p><b>Category: </b> " + score.getCategory() + "</p>" +
                    "<p><b>Difficulty: </b> " + score.getDifficulty() + "</p>" +
                    "<p><b>Type: </b> " + score.getType() + "</p>" +
                    "<p><b>Date Created: </b> " + score.getDate_created() + "</p>" +
                    "<html>"
            ));

            builder.setTitle("City Info").setPositiveButton("OK", null);
            builder.create().show();
        }
    }

    class CityAdapter extends RecyclerView.Adapter<CityViewHolder> {

        @NonNull
        @Override
        public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //return null;
            TextView textView = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.score_view, parent, false);

            CityViewHolder viewHolder = new CityViewHolder(textView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
            TextView view = holder.getView();
            view.setText(scores.get(position).getScore());
        }

        @Override
        public int getItemCount() {
            return scores.size();
        }
    }
}
