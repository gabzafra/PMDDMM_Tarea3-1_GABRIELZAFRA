package es.miteris.pmddmm31.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;

public class ComicDAO {
    private ComicDatabaseSQLiteOpenHelper helper;

    public ComicDAO(Context context) {
        helper = new ComicDatabaseSQLiteOpenHelper(context, "comic_db", null, 1);
    }

    public void createComic(Comic comic) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", comic.getId());
        values.put("title", comic.getTitle());
        values.put("date", comic.getDate());
        values.put("url", comic.getUrl());

        db.insert("comics", null, values);
        db.close();
    }

    public Comic getComic(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Comic comic = null;
        try(Cursor cursor = db.rawQuery("SELECT * FROM comics WHERE id =?", new String[]{String.valueOf(id)})){
            if (cursor.moveToFirst()) {
                comic = new Comic(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3));
            }
        }
        return comic;
    }

    public boolean updateComic(Comic comic) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", comic.getId());
        values.put("title", comic.getTitle());
        values.put("date", comic.getDate());
        values.put("url", comic.getUrl());

        int rows = db.update("comics", values, "id =?", new String[]{String.valueOf(comic.getId())});
        db.close();

        return rows == 1;
    }

    public boolean deleteComic(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();

        int rows = db.delete("comics", "id =?", new String[]{String.valueOf(id)});
        db.close();

        return rows == 1;
    }

    public HashMap<Integer,Comic> getAllComics() {
        SQLiteDatabase db = helper.getReadableDatabase();

        HashMap<Integer,Comic> comicList = new HashMap<>();

        try (Cursor cursor = db.rawQuery("SELECT * FROM comics",new String[]{})) {
            while (cursor.moveToNext()) {
            Comic comic = new Comic(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3));
            comicList.put(comic.getId(), comic);
            }
        }
        return comicList;
    }
}
