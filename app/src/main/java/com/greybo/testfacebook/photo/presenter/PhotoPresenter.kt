package com.greybo.testfacebook.photo.presenter

import com.facebook.AccessToken
import com.greybo.testfacebook.db.HelperFactory
import com.greybo.testfacebook.model.Photo
import com.greybo.testfacebook.model.PhotoResponse
import com.greybo.testfacebook.rest.RestManager
import com.greybo.testfacebook.utils.AlbumConstants

class PhotoPresenter() : PhotoMvp.PhotoMvpPresenter {

    val TAG = AlbumConstants.TAG

    private val mRestManager = RestManager()
    private var mViewMvp: PhotoMvp.PhotoView? = null
    private val dao = HelperFactory.getHelper().photoDAO
    private lateinit var albumId: String

    private object Holder {
        internal val INSTANCE = PhotoPresenter()
    }

    companion object {
        @JvmStatic
        val INSTANCE: PhotoPresenter by lazy { Holder.INSTANCE }
    }

    fun attach(mViewMvp: PhotoMvp.PhotoView) {
        this.mViewMvp = mViewMvp
    }

    fun detach() {
        mViewMvp = null
    }

    fun getAllPhotos(albumId: String) {
        this.albumId = albumId
        val listUrl = arrayListOf<String>()
        dao.findPhotoByAlbumName(albumId)?.forEach { photo ->
            photo.url?.let {
                listUrl.add(it)
            }
        }
        mViewMvp?.fillView(listUrl)
        requestPhotos()
    }

    fun requestPhotos() {
        mRestManager.getPhoto(AccessToken.getCurrentAccessToken(), this)
    }

    override fun responsePhoto(response: PhotoResponse) {
        val listData = response.data
        listData?.forEach({
            val photo = Photo().convertToAlbum(it)
            if (!isExistPhoto(photo)) {
                dao.savePhoto(photo)
                if (photo.albumId.equals(albumId)) {
                    mViewMvp?.updateView(photo)
                }
            }
        })
    }

    fun isExistPhoto(photo: Photo): Boolean {
        val listPhotos = dao.allPhotos
        listPhotos.forEach {
            if (it.equals(photo)) {
                return true
            }
        }
        return false
    }
}