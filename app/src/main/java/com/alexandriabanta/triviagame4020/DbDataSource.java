package com.alexandriabanta.triviagame4020;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DbDataSource {

    private SQLiteDatabase database;
    private MySqlLiteHelper databaseHelper;

    public DbDataSource(Context context)
    {
        databaseHelper = new MySqlLiteHelper(context);
    }

    public void open()
    {
        database = databaseHelper.getWritableDatabase();
    }

    public void close()
    {
        database.close();
    }

    public Score createScore(String scoreString)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MySqlLiteHelper.ScoreColumns.score.toString(), scoreString);
        contentValues.put(MySqlLiteHelper.ScoreColumns.num_of_questions.toString(), scoreString);
        contentValues.put(MySqlLiteHelper.ScoreColumns.category.toString(), scoreString);
        contentValues.put(MySqlLiteHelper.ScoreColumns.difficulty.toString(), scoreString);
        contentValues.put(MySqlLiteHelper.ScoreColumns.type.toString(), scoreString);
        Date dateCreated = new Date();
        contentValues.put(MySqlLiteHelper.ScoreColumns.date_created.toString(), dateCreated.toString());

        /* insert into comment (comment, date_created) values ('hi', '12:00 AM') */
        long id = database.insert(MySqlLiteHelper.SCORE_TABLE,
                null, contentValues);

        String[] columnNames = MySqlLiteHelper.ScoreColumns.names();

        // select * from comment where comment_id = 2
        Cursor cursor = database.query(MySqlLiteHelper.SCORE_TABLE,
                columnNames,
                MySqlLiteHelper.ScoreColumns.score_id + " = " + id,
                null, null, null, null
        );

        cursor.moveToFirst();
        Score comment = cursorToComment(cursor);
        cursor.close();

        return comment;

    }

    public ArrayList<Score> getAllComments() {
        ArrayList<Score> comments = new ArrayList<>();

        String columns[] = MySqlLiteHelper.ScoreColumns.names();

        Cursor cursor = database.query(MySqlLiteHelper.SCORE_TABLE,
                columns,
                null, null, null, null, null);

        cursor.moveToNext();
        while (!cursor.isAfterLast()) {
            Score comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        cursor.close();

        return comments;
    }

    private Score cursorToComment(Cursor cursor) {
        Score score = new Score();

        int scoreId = cursor.getInt(MySqlLiteHelper.ScoreColumns.score_id.ordinal());
        score.setScore_id(scoreId);

        int scoreReal = cursor.getInt(MySqlLiteHelper.ScoreColumns.score.ordinal());
        score.setScore(scoreReal);

        int numQuestions = cursor.getInt(MySqlLiteHelper.ScoreColumns.num_of_questions.ordinal());
        score.setNum_of_questions(numQuestions);

        String cat = cursor.getString(MySqlLiteHelper.ScoreColumns.category.ordinal());
        score.setCategory(cat);

        String diff = cursor.getString(MySqlLiteHelper.ScoreColumns.difficulty.ordinal());
        score.setDifficulty(diff);

        String type = cursor.getString(MySqlLiteHelper.ScoreColumns.type.ordinal());
        score.setType(type);

        String dateStr = cursor.getString(MySqlLiteHelper.ScoreColumns.date_created.ordinal());

        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

        try {
            Date date = dateFormat.parse(dateStr);
            score.setDate_created(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return score;
    }
}

