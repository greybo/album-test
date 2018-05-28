package com.greybo.testfacebook.album.presenter

import com.facebook.AccessToken
import com.greybo.testfacebook.Album
import com.greybo.testfacebook.db.HelperFactory
import com.greybo.testfacebook.model.AlbumRasponse
import com.greybo.testfacebook.rest.RestManager
import com.greybo.testfacebook.utils.AlbumConstants

class AlbumsPresenter : AlbumsMvp.AlbumsMvpPresenter {

    val TAG = AlbumConstants.TAG

    private val mRestManager = RestManager()
    private var mViewMvp: AlbumsMvp.AlbumsView? = null
    private val dao = HelperFactory.getHelper().albumDAO

    private object Holder {
        internal val INSTANCE = AlbumsPresenter()
    }

    companion object {
        @JvmStatic
        val INSTANCE: AlbumsPresenter by lazy { Holder.INSTANCE }
    }

    fun attach(mViewMvp: AlbumsMvp.AlbumsView) {
        this.mViewMvp = mViewMvp
    }

    fun detach() {
        mViewMvp = null
    }

    fun requestAlbums() {
        mRestManager.getAlbums(AccessToken.getCurrentAccessToken(), this)
    }

    fun getAllAlbums() {
        val albumsList = dao.allAlbums
        mViewMvp?.fillView(albumsList as ArrayList<Album>)
        requestAlbums()
    }

    override fun responseAlbums(response: AlbumRasponse?) {
        response?.data?.let {
            it.forEach {
                val album = Album().convertToAlbum(it)
                if (!isAlbumExist(album)) {
                    dao.saveAlbum(album)
                    mViewMvp?.updateView(album)
                }
            }
        }
    }

    fun isAlbumExist(album: Album): Boolean {
        val list = dao.allAlbums
        list.forEach {
            if (it.equals(album))
                return true
        }
        return false
    }
}