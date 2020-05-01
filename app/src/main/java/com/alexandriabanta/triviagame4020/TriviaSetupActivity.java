package com.alexandriabanta.triviagame4020;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class TriviaSetupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String chosenCategory = "General Knowledge"; //general knowledge by default
    private String chosenDifficulty = "Easy";
    private int numOfQuestions = 10;

    private URLTask UrlTask;
    private int ApiReturnCode = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trivia_setup);

        Spinner categorySpinner = (Spinner) findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View spinner, int pos, long id) {
                Log.i("spinner: ", "category spinner");
                chosenCategory = parent.getItemAtPosition(pos).toString();
                Log.i("chosen category: ", parent.getItemAtPosition(pos).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        categorySpinner.setAdapter(categoryAdapter);

        Spinner difficultySpinner = (Spinner) findViewById(R.id.difficultySpinner);
        ArrayAdapter<CharSequence> difficultyAdapter = ArrayAdapter.createFromResource(this,
                R.array.difficulty, android.R.layout.simple_spinner_item);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficultyAdapter);
        difficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View spinner, int pos, long id) {
                Log.i("spinner: ", "diffficulty spinner");
                chosenDifficulty = parent.getItemAtPosition(pos).toString();
                Log.i("chosen difficulty: ", parent.getItemAtPosition(pos).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });


        final NumberPicker numberOfQuestionsPicker = findViewById(R.id.numberPicker);

        numberOfQuestionsPicker.setMinValue(5);
        numberOfQuestionsPicker.setMaxValue(50);
        numberOfQuestionsPicker.setValue(10);

        numberOfQuestionsPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                numOfQuestions = i1;
            }
        });

        TextView playButton = findViewById(R.id.triviaSetupPlayButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                //see if this is a valid trivia set
                TryUrl(); loadingTriviaSetToast();

                //then this is a
                if (ApiReturnCode == 0) {
                    Intent intent = new Intent(getApplicationContext(), TriviaGameActivity.class);
                    startActivity(intent);
                } else {
                    showTryAgainAlertDialog();
                }
                Log.i("response code: ","response code: " + ApiReturnCode);
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View spinner, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        if (spinner == findViewById(R.id.categorySpinner)) {
            Log.i("spinner: ", "category spinner");
            Log.i("category: ", "" + chosenCategory);
            chosenCategory = parent.getItemAtPosition(pos).toString();
        } else if (spinner == findViewById(R.id.difficultySpinner)) {
            Log.i("spinner: ", "diffficulty spinner");
            chosenDifficulty = parent.getItemAtPosition(pos).toString();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) { }

    public URL buildApiUrl(String cat, String dif, int num) {
        Uri.Builder builder = Uri.parse("https://opentdb.com/api.php?").buildUpon();
        builder.appendQueryParameter("amount", "" + numOfQuestions);
        builder.appendQueryParameter("category", "" + getCategoryNumberFromCategory(cat));
        builder.appendQueryParameter("difficulty", "" + chosenDifficulty.toLowerCase());
        builder.appendQueryParameter("type", "multiple");

        URL url = null;
        try {
            url = new URL(builder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.i("category: ", "" + chosenCategory);
        Log.i("category num: ", "" + getCategoryNumberFromCategory(cat));
        Log.i("difficulty: ", "" + chosenDifficulty);
        Log.i("API URL", url.toString());
        return url;
    }

    private void TryUrl() {
        if (UrlTask == null) {
            UrlTask = new URLTask();
            UrlTask.execute();
        }
    }

    private void showTryAgainAlertDialog() {
        //show alertdialog telling user to change their search
        AlertDialog.Builder builder = new AlertDialog.Builder(TriviaSetupActivity.this);
        builder.setMessage(Html.fromHtml("<html>" +
                        "<p><b>Oh no !</b> " + "Looks like there are not enough questions" + "</p>" +
                        "<p>in this category with this difficulty. Adjust your selection and try again. </p>"
        ));

        builder.setTitle("Try again").setPositiveButton("Get Nutrition Info",null);
    }

    private class URLTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            //UrlTask
            URL url = buildApiUrl(chosenCategory,chosenDifficulty,numOfQuestions);


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
                ApiReturnCode = reader.getInt("response_code");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        //@Override
        //protected void onPostExecute() {
            /*TextView tv = findViewById(R.id.textView);
            tv.setText(resultData.titleStr);*/

            //TextView tv = findViewById(R.id.tag_textView);
            //TextView textView = findViewById(R.id.textView);
            //textView.setText(resultData.tagStr);
            //foodAdapter.notifyDataSetChanged();

            //dataDownload = null;
        //}
    }

    public void loadingTriviaSetToast() {
        Context context = getApplicationContext();
        CharSequence text = "Loading trivia set, please wait...";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    //i know there's a better way to do this but idk what it is
    public int getCategoryNumberFromCategory(String categoryString) {
        int categoryNum = 9;

        if (categoryString.equals("Entertainment: Books")) {
            categoryNum = 10;
        } else if (categoryString.equals("Entertainment: Film")) {
            categoryNum = 11;
        } else if (categoryString.equals("Entertainment: Music")) {
            categoryNum = 12;
        } else if (categoryString.equals("Entertainment: Theatre")) {
            categoryNum = 13;
        } else if (categoryString.equals("Entertainment: Television")) {
            categoryNum = 14;
        } else if (categoryString.equals("Entertainment: Video Games")) {
            categoryNum = 15;
        } else if (categoryString.equals("Entertainment: Board Games")) {
            categoryNum = 16;
        } else if (categoryString.equals("Science &amp; Nature")) {
            categoryNum = 17;
        } else if (categoryString.equals("Science: Computers")) {
            categoryNum = 18;
        } else if (categoryString.equals("Science: Mathematics")) {
            categoryNum = 19;
        } else if (categoryString.equals("Mythology")) {
            categoryNum = 20;
        } else if (categoryString.equals("Sports")) {
            categoryNum = 21;
        } else if (categoryString.equals("Geography")) {
            categoryNum = 22;
        } else if (categoryString.equals("History")) {
            categoryNum = 23;
        } else if (categoryString.equals("Politics")) {
            categoryNum = 24;
        } else if (categoryString.equals("Art")) {
            categoryNum = 25;
        } else if (categoryString.equals("Celebrities")) {
            categoryNum = 26;
        } else if (categoryString.equals("Animals")) {
            categoryNum = 27;
        } else if (categoryString.equals("Vehicles")) {
            categoryNum = 28;
        } else if (categoryString.equals("Entertainment: Comics")) {
            categoryNum = 29;
        } else if (categoryString.equals("Science: Gadgets")) {
            categoryNum = 30;
        } else if (categoryString.equals("Entertainment: Anime &amp; Manga")) {
            categoryNum = 31;
        } else if (categoryString.equals("Entertainment: Cartoon &amp; Animation")) {
            categoryNum = 32;
        }

        return categoryNum;
    }
}
