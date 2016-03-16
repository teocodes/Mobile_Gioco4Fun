package com.example.matteo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.matteo.model.Category;
import com.example.matteo.utility.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matteo on 16/03/2016.
 */
public class DbCategory extends DbManager {

    public DbCategory(Context context) {
        super(context);
    }

    @Override
    public Category getSingleObject(int id) {
        openRead();
        Cursor cursor = db.query(Constants.TABLE_CATEGORY,
                new String[]{Constants.ID_CATEGORY,Constants.NAME_CATEGORY},
                Constants.ID_CATEGORY +"=?",
                new String[]{String.valueOf(id)},
                null,null,null);

        if(cursor != null)
            cursor.moveToFirst();

        close();

        return new Category(cursor.getString(1));
    }

    @Override
    public List<Category> getAllObjects() {
        List<Category> list = new ArrayList<>();
        openRead();

        Cursor cursor = db.query(Constants.TABLE_CATEGORY,
                null,null, null, null,null,null);

        if(cursor != null)
            cursor.moveToFirst();

        while (cursor.moveToNext()){
            Category category = new Category(cursor.getString(1));
            list.add(category);
        }

        close();
        return list;
    }

    @Override
    public boolean deleteObject(int id) {
        openWrite();
        int returnValue = db.delete(Constants.TABLE_CATEGORY, Constants.ID_CATEGORY + "=?",
                new String[]{String.valueOf(id)});
        close();

        return returnValue > 0;
    }

    @Override
    public boolean updateObject(int id, Object newObject) {
        Category newCategory = (Category) newObject;

        openWrite();

        ContentValues args = new ContentValues();
        args.put(Constants.NAME_CATEGORY, newCategory.getName() );

        // applico il metodo update
        int returnValue = db.update(Constants.TABLE_CATEGORY, args, Constants.ID_CATEGORY + "=?",
                new String[]{String.valueOf(id)});
        close();

        return returnValue > 0;
    }

    @Override
    public boolean insertObject(int id, Object object) {
        Category category = (Category) object;

        openWrite();

        ContentValues args = new ContentValues();
        args.put(Constants.NAME_CATEGORY, category.getName() );

        // applico il metodo update
        long returnValue = db.insert(Constants.TABLE_CATEGORY, null, args);
        close();

        return returnValue != -1;
    }
}
