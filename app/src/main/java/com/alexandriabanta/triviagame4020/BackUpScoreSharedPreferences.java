package com.alexandriabanta.triviagame4020;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class BackUpScoreSharedPreferences extends AppCompatActivity {

    private ArrayList<Score> scores;
    private RecyclerView mRecyclerView;
    private BackUpAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;


    int number_of_questions = BackUpMainActivity.getNumOfQuestions();
    String cat = BackUpMainActivity.geCategory();
    String diff = BackUpMainActivity.getDifficulty();
    String type = BackUpMainActivity.getQuiz_type();
    int scoreGetter = BackUpStartQuiz.getScore();

    //boolean quiz = StartQuiz.getQuizStatus();

    private TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backup_scoresharedpreferences);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        loadData();

        // building recycler view
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        adapter = new BackUpAdapter(scores);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);

        scores.add(new Score(scoreGetter, number_of_questions, cat, diff, type));
        adapter.notifyItemInserted(scores.size());

        saveData();

    }

    private void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(scores);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Score>>() {}.getType();
        scores = gson.fromJson(json, type);

        if(scores == null)
        {
            scores = new ArrayList<>();
        }

    }

}


