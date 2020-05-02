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
            if ((currentQuestionNum) <= (triviaDeck.numOfQuestions-1)) {
                //if they got the answer right
                if (clickedAnswerTv == findViewById(R.id.answer1TextView)) {
                    correctAnswerAlertDialog();
                }
                //else they got it wrong
                else {
                    incorrectAnswerAlertDialog();
                }

                //move on to the next question
                currentQuestionNum++;
            } else {
                // if they got a high score, go to the scoreboard
                if (highScoreAchieved) {

                }
                // else, take them back to the trivia setup screen
                else {
                    gameFinishedAlertDialog();
                }
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
                triviaDeck.questionsDeck[currentQuestionNum].correctAnswer +
                " </p>"
        ));

        builder.setTitle("Correct!").setPositiveButton("Next Question",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int choice) {
                if ((currentQuestionNum) <= (triviaDeck.numOfQuestions-1)) {
                    updateQuestionInterface();
                }
            }
        });

        builder.show();
    }

    private void incorrectAnswerAlertDialog() {
        //show alertdialog telling user to change their search
        AlertDialog.Builder builder = new AlertDialog.Builder(TriviaGameActivity.this);
        builder.setMessage(Html.fromHtml("<html>" +
                "<p><b>Better luck next time! </b> " +
                "<p>The correct answer was " +
                triviaDeck.questionsDeck[currentQuestionNum].correctAnswer +
                " </p>"
        ));

        builder.setTitle("Incorrect").setPositiveButton("Next Question",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int choice) {
                if ((currentQuestionNum) <= (triviaDeck.numOfQuestions-1)) {
                    updateQuestionInterface();
                }
            }
        });

        builder.show();
    }

    private void gameFinishedAlertDialog() {

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

                    String questionTitle = question.getString("question");
                    String correctAnswer = question.getString("correct_answer");
                    String incorrectAnswer1 = incorrectAnswersArr.getString(0);
                    String incorrectAnswer2 = incorrectAnswersArr.getString(1);
                    String incorrectAnswer3 = incorrectAnswersArr.getString(2);

                    triviaDeck.questionsDeck[i].setQuestion(
                            questionTitle, correctAnswer,
                            incorrectAnswer1, incorrectAnswer2, incorrectAnswer3);

                    Log.i("question title: ",""+ questionTitle);
                    Log.i("correct answer: ",""+ correctAnswer);
                    Log.i("","---------------------------");
                    Log.i("incorrect answer1: ","" + incorrectAnswer1);
                    Log.i("incorrect answer2: ","" + incorrectAnswer2);
                    Log.i("incorrect answer3: ","" + incorrectAnswer3);
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
}
