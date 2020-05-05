package com.alexandriabanta.triviagame4020;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqlLiteHelper extends SQLiteOpenHelper {
    private static final String database_name = "scores.sqlite";
    private static final int database_version = 1;

    public static final String SCORE_TABLE = "Scores";


    public enum ScoreColumns
    {
        score_id, score, num_of_questions, category, difficulty, type, date_created;

        public static String[] names()
        {
            ScoreColumns[] something = values();
            String[] names = new String[something.length];
            for(int i = 0; i < something.length; i++)
            {
                names[i] = something[i].toString();
            }

            return names;
        }
    }

    public MySqlLiteHelper(Context context)
    {
        super(context, database_name, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + SCORE_TABLE + " ("
                + ScoreColumns.score_id + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , " +
                ScoreColumns.score + " TEXT NOT NULL , " +
                ScoreColumns.num_of_questions + " TEXT NOT NULL , " +
                ScoreColumns.category + " TEXT NOT NULL , " +
                ScoreColumns.difficulty + " TEXT NOT NULL , " +
                ScoreColumns.type + " TEXT NOT NULL , " +
                ScoreColumns.date_created + " TEXT NOT NULL )";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(oldVersion == 1 && newVersion == 2)
        {
            String sql = "alter table " + SCORE_TABLE + " add column extra integer";
            sqLiteDatabase.execSQL(sql);

            sql = "update " + SCORE_TABLE + " set extra = 42";
            sqLiteDatabase.execSQL(sql);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //super.onDowngrade(db, oldVersion, newVersion);

        if (newVersion == 1 && oldVersion != newVersion) {

            // The reason we need all this code is because
            // SQLite does not support ALTER TABLE ... DROP COLUMN
            try {
                db.beginTransaction();

                // copy table that needs column dropped
                String sql = "alter table " + SCORE_TABLE + " rename to tmp";
                db.execSQL(sql);

                // recreate the table in the old schema
                sql = "CREATE TABLE " + SCORE_TABLE + " (" +
                        ScoreColumns.score_id + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , " +
                        ScoreColumns.score + " TEXT NOT NULL , " +
                        ScoreColumns.num_of_questions + " TEXT NOT NULL , " +
                        ScoreColumns.category + " TEXT NOT NULL , " +
                        ScoreColumns.difficulty + " TEXT NOT NULL , " +
                        ScoreColumns.type + " TEXT NOT NULL , " +
                        ScoreColumns.date_created + " TEXT NOT NULL )";
                db.execSQL(sql);

                // copy the data we want from the old table
                sql = "insert into " + SCORE_TABLE +
                        " select " +
                        ScoreColumns.score_id + ", " +
                        ScoreColumns.score + ", " +
                        ScoreColumns.num_of_questions + ", " +
                        ScoreColumns.category + ", " +
                        ScoreColumns.difficulty + ", " +
                        ScoreColumns.type + ", " +
                        ScoreColumns.date_created +
                        " from tmp";
                db.execSQL(sql);

                // get rid of the temprary table
                sql = "drop table tmp";
                db.execSQL(sql);

                db.setTransactionSuccessful();
            }  catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }


        }
    }
}
