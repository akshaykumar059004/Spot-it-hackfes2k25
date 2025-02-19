package com.developersunit.spot_it_hackfes2k25.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.developersunit.spot_it_hackfes2k25.Models.WordModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseLogic extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dictionary.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_WORDS = "words";
    private static final String COLUMN_ENGLISH = "EnglishWord";
    private static final String COLUMN_MEANING1 = "Meaning1";
    private static final String COLUMN_MEANING2 = "Meaning2";

    Context _context;

    public DatabaseLogic(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        _context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORDS_TABLE = "CREATE TABLE " + TABLE_WORDS + " ("
                + COLUMN_ENGLISH + " TEXT PRIMARY KEY, "
                + COLUMN_MEANING1 + " TEXT, "
                + COLUMN_MEANING2 + " TEXT);";
        db.execSQL(CREATE_WORDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
        onCreate(db);
    }

    public void addNewWord(WordModel wordModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ENGLISH, wordModel.getEnglishWord());
        values.put(COLUMN_MEANING1, wordModel.getMeaning1());
        values.put(COLUMN_MEANING2, wordModel.getMeaning2());

        db.insert(TABLE_WORDS, null, values);
        db.close();
    }

    public List<WordModel> getAllWords() {
        List<WordModel> wordModels = new ArrayList<>();
        String selectQuery = "SELECT " + COLUMN_ENGLISH + ", " + COLUMN_MEANING1 + ", " + COLUMN_MEANING2 + " FROM " + TABLE_WORDS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    WordModel wordModel = new WordModel();
                    wordModel.setEnglishWord(cursor.getString(0));
                    wordModel.setMeaning1(cursor.getString(1));
                    wordModel.setMeaning2(cursor.getString(2));

                    wordModels.add(wordModel);
                } while (cursor.moveToNext());
            }
            cursor.close(); // Close cursor after use
        }
        db.close(); // Close database after operation
        return wordModels;
    }

    public WordModel SearchSpecificWord(String word) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_WORDS,
                new String[]{COLUMN_ENGLISH, COLUMN_MEANING1, COLUMN_MEANING2},
                COLUMN_ENGLISH + "=?",
                new String[]{word}, null, null, null, null);

        WordModel wordModel = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                wordModel = new WordModel();
            }
            cursor.close();
        }
        db.close();
        return wordModel;
    }

    public List<WordModel> SearchSimilarWords(String word) {
        List<WordModel> wordModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT " + COLUMN_ENGLISH + ", " + COLUMN_MEANING1 + ", " + COLUMN_MEANING2 +
                " FROM " + TABLE_WORDS + " WHERE " + COLUMN_ENGLISH + " LIKE ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{"%" + word + "%"});

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    WordModel wordModel = new WordModel();
                    wordModel.setEnglishWord(cursor.getString(0));
                    wordModel.setMeaning1(cursor.getString(1));
                    wordModel.setMeaning2(cursor.getString(2));

                    wordModels.add(wordModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        return wordModels;
    }
}
