package com.alexandriabanta.triviagame4020;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BackUpMainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //private EditText numOfQuestions;
    private static String number_of_questions;
    private static String category;
    private static String difficulty;
    private static String quiz_type;

    public static int getNumOfQuestions() {
        return Integer.parseInt(number_of_questions);
    }

    public static String geCategory() {
        return category;
    }

    public static String getDifficulty() {
        return difficulty;
    }

    public static String getQuiz_type() {
        return quiz_type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backup_activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Button playButton = findViewById(R.id.start_quiz_button);
        Button scoreboardButton = findViewById(R.id.scoreboardButton);
        Button aboutButton = findViewById(R.id.aboutButton);


        playButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BackUpStartQuiz.class);
                startActivity(intent);
            }
        });


        scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BackUpScoreSharedPreferences.class);
                startActivity(intent);
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BackUpAbout.class);
                startActivity(intent);
            }
        });

        Spinner questions_spinner = findViewById(R.id.num_questions_spinner);
        ArrayAdapter<CharSequence> questions_adapter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        questions_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questions_spinner.setAdapter(questions_adapter);
        questions_spinner.setOnItemSelectedListener(this);

        Spinner category_spinner = findViewById(R.id.categories_spinner);
        ArrayAdapter<CharSequence> category_adapter = ArrayAdapter.createFromResource(this, R.array.categories1, android.R.layout.simple_spinner_item);
        category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner.setAdapter(category_adapter);
        category_spinner.setOnItemSelectedListener(this);

        Spinner difficulty_spinner = findViewById(R.id.difficulty_spinner);
        ArrayAdapter<CharSequence> difficulty_adapter = ArrayAdapter.createFromResource(this, R.array.difficulty1, android.R.layout.simple_spinner_item);
        difficulty_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficulty_spinner.setAdapter(difficulty_adapter);
        difficulty_spinner.setOnItemSelectedListener(this);

        Spinner quiz_type_spinner = findViewById(R.id.quiz_type_spinner);
        ArrayAdapter<CharSequence> quiz_type_adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        quiz_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quiz_type_spinner.setAdapter(quiz_type_adapter);
        quiz_type_spinner.setOnItemSelectedListener(this);

        findViewById(R.id.start_quiz_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz();

            }
        });

    }

    private void startQuiz() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Awesome! Ready to take this quiz???");
        builder.setMessage("Number of Questions: " + number_of_questions + "\n" +
                "Category: " + category + "\n" +
                "Difficulty: " + difficulty + "\n" +
                "Quiz Type: " + quiz_type
        );

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int choice) {
                // Dismiss Dialog
                Intent i = new Intent(getApplicationContext(), BackUpStartQuiz.class);
                getApplicationContext().startActivity(i);
            }
        });

        builder.setNegativeButton("NO, GO BACK!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int choice) {
                // Dismiss Dialog
                //Intent i = new Intent(getApplicationContext(), MainActivity.class);
                //getApplicationContext().startActivity(i);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setLayout(1100, 600);

        //Intent intent = new Intent(getApplicationContext(), StartQuiz.class);
        //startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();

        if (adapterView.getId() == R.id.num_questions_spinner)
        {
            number_of_questions = adapterView.getItemAtPosition(i).toString();
        }

        if (adapterView.getId() == R.id.categories_spinner)
        {
            category = adapterView.getItemAtPosition(i).toString();
        }

        if (adapterView.getId() == R.id.difficulty_spinner)
        {
            difficulty = adapterView.getItemAtPosition(i).toString();
        }

        if (adapterView.getId() == R.id.quiz_type_spinner)
        {
            quiz_type = adapterView.getItemAtPosition(i).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }
}
