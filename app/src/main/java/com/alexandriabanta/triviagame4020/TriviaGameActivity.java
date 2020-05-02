package com.alexandriabanta.triviagame4020;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class TriviaGameActivity extends AppCompatActivity {

    private TextView questionTextView;
    private TextView ans1TextView;
    private TextView ans2TextView;
    private TextView ans3TextView;
    private TextView ans4TextView;

    private TriviaDeck triviaDeck;
    private loadQuestionsIntoDeckTask loadDeckTask;
    private int currentQuestionNum = 0;
    private int score = 0;
    private boolean highScoreAchieved = false;
    private URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trivia_game);

        initializeTextViews();

        //get the url from the user's selection im TriviaSetupActivity
        Intent i = getIntent();
        String urlString = i.getStringExtra("urlString");
        Uri.Builder builder = Uri.parse(urlString).buildUpon();

        url = null;
        try {
            url = new URL(builder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i("TriviaGame-- ","url: " + url.toString());

        //proceed to load the deck into the activity
        loadDeck();

    }

    private View.OnClickListener onAnswerClicked = new View.OnClickListener() {
        @Override
        public void onClick(View clickedAnswerTv) {
            //See if they clicked the right answer

            //if we haven't hit the end of the deck
            if ((currentQuestionNum) < (triviaDeck.numOfQuestions-1)) {
                //if they got the answer right
                if (clickedAnswerTv == findViewById(R.id.answer1TextView)) {
                    score++;
                    correctAnswerAlertDialog();
                }
                //else they got it wrong
                else {
                    incorrectAnswerAlertDialog();
                }

                //move on to the next question
                currentQuestionNum++;
            } else if ((currentQuestionNum) == (triviaDeck.numOfQuestions-1)){
                if (clickedAnswerTv == findViewById(R.id.answer1TextView)) {
                    score++;
                }
                currentQuestionNum++;
            } // else, take them back to the trivia setup screen
            else {
                // if they got a high score, go to the scoreboard
                if (highScoreAchieved) {

                }
                gameFinishedAlertDialog();
            }
        }
    };

    private void updateQuestionInterface() {
        questionTextView.setText(triviaDeck.questionsDeck[currentQuestionNum].questionTitle);
        ans1TextView.setText(triviaDeck.questionsDeck[currentQuestionNum].correctAnswer);
        ans2TextView.setText(triviaDeck.questionsDeck[currentQuestionNum].incorrectAnswer1);
        ans3TextView.setText(triviaDeck.questionsDeck[currentQuestionNum].incorrectAnswer2);
        ans4TextView.setText(triviaDeck.questionsDeck[currentQuestionNum].incorrectAnswer3);
    }

    private void correctAnswerAlertDialog() {
        //show alertdialog telling user to change their search
        AlertDialog.Builder builder = new AlertDialog.Builder(TriviaGameActivity.this);
        builder.setMessage(Html.fromHtml("<html>" +
                "<p><b>Awesome work, you got it right! </b> " +
                "<p>The correct answer was " +
                triviaDeck.questionsDeck[currentQuestionNum].correctAnswer + ".</p>"
        ));

        builder.setTitle("Correct!").setPositiveButton("Next Question",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int choice) {
                if ((currentQuestionNum) <= (triviaDeck.numOfQuestions-1)) {
                    updateQuestionInterface();
                } else if ((currentQuestionNum) > (triviaDeck.numOfQuestions-1)) {
                    gameFinishedAlertDialog();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(1000, 600);
        dialog.show();
    }

    private void incorrectAnswerAlertDialog() {
        //show alertdialog telling user to change their search
        AlertDialog.Builder builder = new AlertDialog.Builder(TriviaGameActivity.this);
        builder.setMessage(Html.fromHtml("<html>" +
                "<p><b>Better luck next time! </b> " +
                "<p>The correct answer was " +
                triviaDeck.questionsDeck[currentQuestionNum].correctAnswer + ".</p>"
        ));

        builder.setTitle("Incorrect").setPositiveButton("Next Question",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int choice) {
                if ((currentQuestionNum) <= (triviaDeck.numOfQuestions-1)) {
                    updateQuestionInterface();
                } else if ((currentQuestionNum) > (triviaDeck.numOfQuestions-1)) {
                    gameFinishedAlertDialog();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(1000, 600);
        dialog.show();
    }

    private void gameFinishedAlertDialog() {
        //show alertdialog telling user to change their search
        AlertDialog.Builder builder = new AlertDialog.Builder(TriviaGameActivity.this);
        builder.setMessage(Html.fromHtml("<html>" +
                "<p><b>Congrats, you finished the game! </b> " +
                "<p>Your score was " + score + " points.</p>"
        ));

        builder.setTitle("All done!").setPositiveButton("New Game",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int choice) {
                Intent intent = new Intent(getApplicationContext(), TriviaSetupActivity.class);
                startActivity(intent);

                //SAVE SCORE BEFORE RESETTING TO 0
                score = 0;
            }
        });

        builder.setNegativeButton("Go to Scoreboard",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int choice) {
                Intent intent = new Intent(getApplicationContext(), ScoreboardActivity.class);
                startActivity(intent);

                //SAVE SCORE BEFORE RESETTING TO 0
                score = 0;
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(1000, 600);
        dialog.show();
    }

    private void loadDeck() {
        if (loadDeckTask == null) {
            loadDeckTask = new loadQuestionsIntoDeckTask();
            loadDeckTask.execute();
        }
    }

    protected class loadQuestionsIntoDeckTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            //setup Jsonreader for getting info
            try {
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                StringBuilder jsonData = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    jsonData.append(line);
                }

                JSONObject reader = new JSONObject(jsonData.toString());

                //now get the information and store it into the deck
                JSONArray questionsArr = reader.getJSONArray("results");
                triviaDeck = new TriviaDeck(questionsArr.length());

                for(int i = 0; i < questionsArr.length(); i++)  {
                    //need to get title, correct answer, and incorrect answers
                    JSONObject question = questionsArr.getJSONObject(i);
                    JSONArray incorrectAnswersArr = question.getJSONArray("incorrect_answers");

                    //get strings from array and parse them
                    String questionTitle = parseString(question.getString("question"));
                    String correctAnswer = parseString(question.getString("correct_answer"));
                    String incorrectAnswer1 = parseString(incorrectAnswersArr.getString(0));
                    String incorrectAnswer2 = parseString(incorrectAnswersArr.getString(1));
                    String incorrectAnswer3 = parseString(incorrectAnswersArr.getString(2));

                    triviaDeck.questionsDeck[i].setQuestion(
                            questionTitle, correctAnswer,
                            incorrectAnswer1, incorrectAnswer2, incorrectAnswer3);

                    Log.i("","---------------------------");
                    Log.i("question title: ",""+ questionTitle);
                    Log.i("correct answer: ",""+ correctAnswer);
                    Log.i("incorrect answer1: ","" + incorrectAnswer1);
                    Log.i("incorrect answer2: ","" + incorrectAnswer2);
                    Log.i("incorrect answer3: ","" + incorrectAnswer3);
                    Log.i("","---------------------------");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            //reset the loadDeckTask
            loadDeckTask = null;
            //update the UI
            updateQuestionInterface();
        }
    }

    private void initializeTextViews() {
        questionTextView = findViewById(R.id.questionTitleTextView);
        ans1TextView = findViewById(R.id.answer1TextView);
        ans2TextView = findViewById(R.id.answer2TextView);
        ans3TextView = findViewById(R.id.answer3TextView);
        ans4TextView = findViewById(R.id.answer4TextView);

        ans1TextView.setOnClickListener(onAnswerClicked);
        ans2TextView.setOnClickListener(onAnswerClicked);
        ans3TextView.setOnClickListener(onAnswerClicked);
        ans4TextView.setOnClickListener(onAnswerClicked);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity(0); //not sure if this is right?
    }

    private String parseString(String inputString) {
        //use replace all to fix unicode escape characters

        inputString.replaceAll("&quot;","\"");     //quote
        inputString.replaceAll("&ldquo;","\"");
        inputString.replaceAll("&#039;","\'");      //apostrophe/single quote
        inputString.replaceAll("&rsquo;","\'");
        inputString.replaceAll("&amp;","&");        //ampersand
        inputString.replaceAll("&Uuml;","ü");       //umlaut
        inputString.replaceAll("&hellip;","...");
        inputString.replaceAll("&oacute;","ó");

        return inputString;
    }
}
