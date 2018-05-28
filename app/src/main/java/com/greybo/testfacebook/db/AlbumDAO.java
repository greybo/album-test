package com.greybo.testfacebook.db;

import com.greybo.testfacebook.Album;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class AlbumDAO extends BaseDaoImpl<Album, Integer> {

    AlbumDAO(ConnectionSource connectionSource, Class<Album> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Album> getAllAlbums() throws SQLException {
        return this.queryForAll();
    }

    public void saveAlbum(Album obj) {
        try {
            this.createIfNotExists(obj);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
