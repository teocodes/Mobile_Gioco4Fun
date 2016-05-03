package com.example.teams_cup.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.teams_cup.model.Question;
import com.example.teams_cup.utility.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teams_cup on 16/03/2016.
 */
public class DbQuestion extends DbManager {

    public DbQuestion(Context context) {
        super(context);
    }

    @Override
    public Question getSingleObject(int id) {
        openRead();
        Cursor cursor = db.query(Constants.TABLE_QUESTION,
                new String[]{Constants.ID_QUESTION,Constants.CONTENT_QUESTION,Constants.ID_CATEGORY_QUESTION},
                Constants.ID_QUESTION +"=?",
                new String[]{String.valueOf(id)},
                null,null,null);

        if(cursor != null)
            cursor.moveToFirst();

        close();

        return new Question(cursor.getString(1), cursor.getInt(2));
    }

    @Override
    public List<Question> getAllObjects() {
        List<Question> list = new ArrayList<>();
        openRead();

        Cursor cursor = db.query(Constants.TABLE_QUESTION,
                null,null, null, null,null,null);

        if(cursor != null)
            cursor.moveToFirst();

        while (cursor.moveToNext()){
            Question question = new Question(cursor.getString(1), cursor.getInt(2));
            list.add(question);
        }

        close();
        return list;
    }

    @Override
    public boolean deleteObject(int id) {
        openWrite();
        int returnValue = db.delete(Constants.TABLE_QUESTION, Constants.ID_QUESTION + "=?",
                new String[]{String.valueOf(id)});
        close();

        return returnValue > 0;
    }

    @Override
    public boolean updateObject(int id, Object newObject) {
        Question newQuestion = (Question) newObject;

        openWrite();

        ContentValues args = new ContentValues();
        args.put(Constants.CONTENT_QUESTION, newQuestion.getContentQuestion() );
        args.put(Constants.ID_CATEGORY_QUESTION, newQuestion.getCategory());

        // applico il metodo update
        int returnValue = db.update(Constants.TABLE_QUESTION, args, Constants.ID_QUESTION + "=?",
                new String[]{String.valueOf(id)});
        close();

        return returnValue > 0;
    }

    @Override
    public boolean insertObject(int id, Object object) {
        Question question = (Question) object;

        openWrite();

        ContentValues args = new ContentValues();
        args.put(Constants.CONTENT_QUESTION, question.getContentQuestion() );
        args.put(Constants.ID_CATEGORY_QUESTION, question.getCategory());

        // applico il metodo update
        long returnValue = db.insert(Constants.TABLE_QUESTION, null, args);
        close();

        return returnValue != -1;
    }
}
