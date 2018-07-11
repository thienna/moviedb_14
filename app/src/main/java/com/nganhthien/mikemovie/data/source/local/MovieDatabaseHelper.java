package com.nganhthien.mikemovie.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nganhthien.mikemovie.data.model.Movie;
import com.nganhthien.mikemovie.data.source.MovieDataSource;
import com.nganhthien.mikemovie.utils.AddFavoriteLocal;
import com.nganhthien.mikemovie.utils.Constants;
import com.nganhthien.mikemovie.utils.GetAllFavoriteFromLocal;
import com.nganhthien.mikemovie.utils.GetFavoriteIdsFromLocal;
import com.nganhthien.mikemovie.utils.RemoveFavoriteLocal;

public final class MovieDatabaseHelper extends SQLiteOpenHelper {
    private static MovieDatabaseHelper sInstance;

    private MovieDatabaseHelper(Context context) {
        super(context, Constants.DatabaseConfig.DATABASE_NAME,
                null, Constants.DatabaseConfig.DATABASE_VERSION);
    }

    public static synchronized MovieDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MovieDatabaseHelper(context);
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance = null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Movie.DatabaseConfig.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(Constants.DatabaseConfig.QUERY_DROP_TABLE_IF_EXISTS + Movie.DatabaseConfig.TABLE_NAME);
            onCreate(db);
        }
    }

    public void getAll(MovieDataSource.OnGetAllFavoriteListener listener) {
        new GetAllFavoriteFromLocal(listener, getReadableDatabase()).execute();
    }

    public void insertSingle(Movie movie, MovieDataSource.OnAddFavoriteListener listener) {
        new AddFavoriteLocal(movie, listener, getWritableDatabase()).execute();
    }

    public void deleteSingle(Movie movie, MovieDataSource.OnRemoveFavoriteListener listener) {
        new RemoveFavoriteLocal(movie, listener, getWritableDatabase()).execute();
    }

    public void getIds(MovieDataSource.OnGetFavoriteIdsListener listener) {
        new GetFavoriteIdsFromLocal(listener, getReadableDatabase()).execute();
    }
}
