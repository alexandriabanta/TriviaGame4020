package com.alexandriabanta.triviagame4020;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BackUpStartQuiz extends AppCompatActivity implements View.OnClickListener {

    int questionNumber = 1;
    //int score = 0;
    private static int score = 0;
    int currentView = 0;

    private static boolean quizTaken = false;

    public static boolean getQuizStatus() {
        return quizTaken;
    }

    public static int getScore() {
        return score;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backup_startquiz);

        // this code adds my calculator icon on my action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        // never needed this. This means you have to click submit first to activate buttons
        //findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
           // @Override
            //public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Click a choice", Toast.LENGTH_SHORT).show();
                ((RadioGroup) findViewById(R.id.question_radioGroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(final RadioGroup radioGroup, final int checkedId) {

                        Log.i("LISTEN", "Before button setup");

                        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                RadioButton rb1 = findViewById(R.id.radioButton1);
                                RadioButton rb2 = findViewById(R.id.radioButton2);
                                RadioButton rb3 = findViewById(R.id.radioButton3);
                                RadioButton rb4 = findViewById(R.id.radioButton4);

                                TextView tv = findViewById(R.id.question_textView);

                                ImageView iv = findViewById(R.id.imageView);
                                if (questionNumber == 1) {
                                    if (checkedId == R.id.radioButton1 || checkedId == R.id.radioButton2 || checkedId == R.id.radioButton3 || checkedId == R.id.radioButton4) {

                                        if (rb4.isChecked()) {
                                            score++;
                                            Log.i("SCORE", Integer.toString(score));
                                        }
                                        tv.setText("2. What sign is this?");

                                        iv.setImageResource(R.drawable.deer_sign);

                                        rb1.setText("1. Deer Sign");
                                        rb2.setText("2. Chicken Sign");
                                        rb3.setText("3. Armidillo Sign");
                                        rb4.setText("4. Banche Sign");
                                        radioGroup.clearCheck();
                                        questionNumber++;
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Click a choice", Toast.LENGTH_SHORT).show();
                                    }
                                } else if (questionNumber == 2) {
                                    if (checkedId == R.id.radioButton1 || checkedId == R.id.radioButton2 || checkedId == R.id.radioButton3 || checkedId == R.id.radioButton4) {

                                        if (rb1.isChecked()) {

                                            score++;
                                            Log.i("SCORE", Integer.toString(score));
                                        }

                                        tv.setText("3. What sign is this?");

                                        iv.setImageResource(R.drawable.no_entry_sign);

                                        rb1.setText("1. No Entry Sign");
                                        rb2.setText("2. Dash Sign");
                                        rb3.setText("3. Red and White Sign");
                                        rb4.setText("4. Circle Sign");
                                        radioGroup.clearCheck();
                                        questionNumber++;
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Click a choice", Toast.LENGTH_SHORT).show();
                                    }

                                } else if (questionNumber == 3) {

                                    if (checkedId == R.id.radioButton1 || checkedId == R.id.radioButton2 || checkedId == R.id.radioButton3 || checkedId == R.id.radioButton4) {


                                        if (rb1.isChecked()) {

                                            score++;
                                            Log.i("SCORE", Integer.toString(score));
                                        }

                                        tv.setText("4. What sign is this?");
                                        iv.setImageResource(R.drawable.no_parking_sign);

                                        rb1.setText("1. Parking Sign");
                                        rb2.setText("2. Pepperment Sign");
                                        rb3.setText("3. No Parking Sign");
                                        rb4.setText("4. People Sign");
                                        radioGroup.clearCheck();
                                        questionNumber++;
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Click a choice", Toast.LENGTH_SHORT).show();
                                    }

                                } else if (questionNumber == 4) {

                                    if (checkedId == R.id.radioButton1 || checkedId == R.id.radioButton2 || checkedId == R.id.radioButton3 || checkedId == R.id.radioButton4) {
                                        if (rb3.isChecked()) {

                                            score++;
                                            Log.i("SCORE", Integer.toString(score));
                                        }

                                        tv.setText("5. What sign is this?");
                                        iv.setImageResource(R.drawable.route_sign);

                                        rb1.setText("1. Right Arrow Sign");
                                        rb2.setText("2. Blurry Sign Sign");
                                        rb3.setText("3. Route Sign");
                                        rb4.setText("4. Turn Sign");
                                        radioGroup.clearCheck();
                                        questionNumber++;
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Click a choice", Toast.LENGTH_SHORT).show();
                                    }

                                } else if (questionNumber == 5) {

                                    if (checkedId == R.id.radioButton1 || checkedId == R.id.radioButton2 || checkedId == R.id.radioButton3 || checkedId == R.id.radioButton4) {
                                        if (rb3.isChecked()) {

                                            score++;
                                            Log.i("SCORE", Integer.toString(score));
                                        }

                                        tv.setText("6. What sign is this?");
                                        iv.setImageResource(R.drawable.traffic_jam);

                                        rb1.setText("1. Jam Sign");
                                        rb2.setText("2. Traffic Jam Sign");
                                        rb3.setText("3. Cars Sign");
                                        rb4.setText("4. Primary Colors Sign");
                                        radioGroup.clearCheck();
                                        questionNumber++;
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Click a choice", Toast.LENGTH_SHORT).show();
                                    }

                                } else if (questionNumber == 6) {
                                    if (checkedId == R.id.radioButton1 || checkedId == R.id.radioButton2 || checkedId == R.id.radioButton3 || checkedId == R.id.radioButton4) {
                                        if (rb2.isChecked()) {

                                            score++;
                                            Log.i("SCORE", Integer.toString(score));
                                        }

                                        tv.setText("7. What sign is this?");
                                        iv.setImageResource(R.drawable.traffic_light_sign);

                                        rb1.setText("1. Traffic Light Sign");
                                        rb2.setText("2. Red, Yellow, & Green Sign");
                                        rb3.setText("3. No Left Turn Sign");
                                        rb4.setText("4. Caution Sign");
                                        radioGroup.clearCheck();
                                        questionNumber++;
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Click a choice", Toast.LENGTH_SHORT).show();
                                    }

                                } else if (questionNumber == 7) {
                                    if (checkedId == R.id.radioButton1 || checkedId == R.id.radioButton2 || checkedId == R.id.radioButton3 || checkedId == R.id.radioButton4) {
                                        if (rb1.isChecked()) {

                                            score++;
                                            Log.i("SCORE", Integer.toString(score));
                                        }

                                        tv.setText("8. What sign is this?");
                                        iv.setImageResource(R.drawable.left_turn);

                                        rb1.setText("1. Left Sign");
                                        rb2.setText("2. No Sign");
                                        rb3.setText("3. No Left Turn Sign");
                                        rb4.setText("4. Bent Sign");
                                        radioGroup.clearCheck();
                                        questionNumber++;
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Click a choice", Toast.LENGTH_SHORT).show();
                                    }

                                } else if (questionNumber == 8) {
                                    if (checkedId == R.id.radioButton1 || checkedId == R.id.radioButton2 || checkedId == R.id.radioButton3 || checkedId == R.id.radioButton4) {
                                        if (rb3.isChecked()) {

                                            score++;
                                            Log.i("SCORE", Integer.toString(score));
                                        }

                                        tv.setText("9. What sign is this?");
                                        iv.setImageResource(R.drawable.under_construction_sign);

                                        rb1.setText("1. Under Sign");
                                        rb2.setText("2. Under Construction Sign");
                                        rb3.setText("3. Dig Sign");
                                        rb4.setText("4. Diggity Dig Sign");
                                        radioGroup.clearCheck();
                                        questionNumber++;
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Click a choice", Toast.LENGTH_SHORT).show();
                                    }

                                } else if (questionNumber == 9) {
                                    if (checkedId == R.id.radioButton1 || checkedId == R.id.radioButton2 || checkedId == R.id.radioButton3 || checkedId == R.id.radioButton4) {
                                        if (rb2.isChecked()) {

                                            score++;
                                            Log.i("SCORE", Integer.toString(score));
                                        }

                                        tv.setText("10. What sign is this?");

                                        iv.setImageResource(R.drawable.yield_sign);

                                        rb1.setText("1. Triangle Sign");
                                        rb2.setText("2. Yield Sign");
                                        rb3.setText("3. Red and White Sign");
                                        rb4.setText("4. Stupid Sign");
                                        radioGroup.clearCheck();
                                        questionNumber++;
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Click a choice", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    if (rb2.isChecked()) {

                                        score++;
                                        Log.i("SCORE", Integer.toString(score));
                                    }
                                    quizTaken = true;
                                    results(view);
                                }
                            }
                        });
                    }


                });
            //}
        //});

    }

    @Override
    public void onClick(View view) {

    }

    private void quiz(View view) {

    }

    private void results(View view) {
        setContentView(R.layout.backup_scoreresults);
        TextView results = findViewById(R.id.score_textView);
        results.setText(Integer.toString(score) + "/10");

        findViewById(R.id.retake_test_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setContentView(R.layout.activity_main);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                questionNumber = 1;
                score = 0;
                //quiz(view);
            }
        });

        findViewById(R.id.see_scoreboard_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setContentView(R.layout.see_score_sharedpreferences);
                Intent intent = new Intent(getApplicationContext(), BackUpScoreSharedPreferences.class);
                startActivity(intent);

            }
        });

    }
}

