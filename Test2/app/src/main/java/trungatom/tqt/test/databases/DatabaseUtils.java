package trungatom.tqt.test.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import trungatom.tqt.test.models.TodoModel;

public class DatabaseUtils {

    private final String TABLE_TODO = "todo";
    int a = 0;
    private SQLiteDatabase sqLiteDatabase;
    private MyDatabase myDatabase;

    private static DatabaseUtils databaseUtils;

    private DatabaseUtils(Context context) {
        myDatabase = new MyDatabase(context);
    }

    public static DatabaseUtils getInstance(Context context) {
        if (databaseUtils == null) {
            databaseUtils = new DatabaseUtils(context);
        }
        return databaseUtils;
    }

    public List<TodoModel> getListTopic() {
        sqLiteDatabase = myDatabase.getReadableDatabase();
        List<TodoModel> todoModels = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_TODO, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            String tag = cursor.getString(3);
            String showTime = cursor.getString(4);

            TodoModel todoModel = new TodoModel(id, title, content, tag, showTime);
            todoModels.add(todoModel);

            cursor.moveToNext();
        }
        a++;
        if (a == 1) {
            Log.d("atom", "getListTopic: " + todoModels);
        }

        return todoModels;
    }

    public void addData(String title, String tag, String content, String date, int id) {
        sqLiteDatabase = myDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("title", title);
        contentValues.put("content", content);
        contentValues.put("tag", tag);
        contentValues.put("showtime", date);
        sqLiteDatabase.insert(TABLE_TODO, null, contentValues);
    }
    public void updateData(String title, String tag, String content, String date, int id){
        sqLiteDatabase = myDatabase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("title", title);
        contentValues.put("content", content);
        contentValues.put("tag", tag);
        contentValues.put("showtime", date);
        sqLiteDatabase.update(TABLE_TODO, contentValues, "id = " + id, null);
    }
    public void deleteData(int id) {
        sqLiteDatabase.delete(TABLE_TODO, "id = " + id, null);
    }
}
