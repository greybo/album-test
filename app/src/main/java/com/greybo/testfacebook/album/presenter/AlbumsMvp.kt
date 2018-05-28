package com.greybo.testfacebook.album.presenter

import com.greybo.testfacebook.Album
import com.greybo.testfacebook.model.AlbumRasponse

interface AlbumsMvp {

    interface AlbumsView {
        fun fillView(albums: ArrayList<Album>)
        fun updateView(album: Album)
    }

    interface AlbumsMvpPresenter {
        fun responseAlbums(response: AlbumRasponse?)
    }

}