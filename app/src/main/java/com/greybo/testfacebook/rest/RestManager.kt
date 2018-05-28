package com.greybo.testfacebook.rest

import android.util.Log
import com.facebook.AccessToken
import com.greybo.testfacebook.album.presenter.AlbumsMvp
import com.greybo.testfacebook.model.AlbumRasponse
import com.greybo.testfacebook.model.PhotoResponse
import com.greybo.testfacebook.photo.presenter.PhotoMvp
import com.greybo.testfacebook.utils.AlbumConstants
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RestManager {

    private val TAG = AlbumConstants.TAG
    private val api = GraphApi()

    fun getAlbums(accessToken: AccessToken, albumPresenter: AlbumsMvp.AlbumsMvpPresenter) {
        api.getApi().getAlbums(accessToken.userId, accessToken.token, "picture,name,created_time")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<AlbumRasponse>() {
                    override fun onCompleted() {
                        Log.i(TAG, "onCompleted: " + "completed!")
                    }

                    override fun onError(e: Throwable) {
                        Log.i(TAG, "onError: " + e.message)
                    }

                    override fun onNext(response: AlbumRasponse) {
                        albumPresenter.responseAlbums(response)
                    }
                })
    }

    fun getPhoto(accessToken: AccessToken, listener: PhotoMvp.PhotoMvpPresenter) {
        api.getApi().getPhotos(accessToken.userId, accessToken.token, "created_time,album,source", "uploaded")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<PhotoResponse>() {
                    override fun onCompleted() {
                        Log.i(TAG, "onCompleted getPhoto: " + "completed!")
                    }

                    override fun onError(e: Throwable) {
                        Log.i(TAG, "onError getPhoto: " + e.message)
                    }

                    override fun onNext(response: PhotoResponse) {
                        listener.responsePhoto(response)
                    }
                })
    }

}




