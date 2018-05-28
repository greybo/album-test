package com.greybo.testfacebook.photo.presenter

import com.greybo.testfacebook.model.Photo
import com.greybo.testfacebook.model.PhotoResponse

interface PhotoMvp {

    interface PhotoView {
        fun fillView(photos: ArrayList<String>)
        fun updateView(photo: Photo)
    }

    interface PhotoMvpPresenter {
        fun responsePhoto(response: PhotoResponse)
    }

}