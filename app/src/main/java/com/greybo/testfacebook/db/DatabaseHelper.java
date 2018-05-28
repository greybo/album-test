package com.greybo.testfacebook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.greybo.testfacebook.Album;
import com.greybo.testfacebook.model.Photo;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "facebookalbums.db";
    private static final int DATABASE_VERSION = 1;
    private AlbumDAO albumDAO = null;
    private PhotoDao photoDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Album.class);
            TableUtils.createTable(connectionSource, Photo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
                          int newVer) {
        try {
            TableUtils.dropTable(connectionSource, Album.class, true);
            TableUtils.dropTable(connectionSource, Photo.class, true);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        onCreate(db, connectionSource);
        Log.e(TAG, "error upgrading db " + DATABASE_NAME + "from ver " + oldVer);
    }

    public AlbumDAO getAlbumDAO() throws SQLException {
        if (albumDAO == null) {
            albumDAO = new AlbumDAO(getConnectionSource(), Album.class);
        }
        return albumDAO;
    }

    public PhotoDao getPhotoDAO() throws SQLException {
        if (photoDao == null) {
            photoDao = new PhotoDao(getConnectionSource(), Photo.class);
        }
        return photoDao;
    }

    @Override
    public void close() {
        super.close();
        albumDAO = null;
    }
}