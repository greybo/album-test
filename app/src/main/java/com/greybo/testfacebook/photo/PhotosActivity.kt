package com.greybo.testfacebook.photo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.greybo.testfacebook.R
import com.greybo.testfacebook.image.ImagePreviewActivity
import com.greybo.testfacebook.model.Photo
import com.greybo.testfacebook.photo.adapter.PhotoRecyclerAdapter
import com.greybo.testfacebook.photo.presenter.PhotoMvp
import com.greybo.testfacebook.photo.presenter.PhotoPresenter
import com.greybo.testfacebook.utils.AlbumConstants
import com.greybo.testfacebook.utils.OnBlurListener
import com.greybo.testfacebook.utils.OnItemClickListener
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur


class PhotosActivity : AppCompatActivity(), PhotoMvp.PhotoView, OnItemClickListener, OnBlurListener {

    val TAG = AlbumConstants.TAG

    private lateinit var mPresenter: PhotoPresenter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var blurView: BlurView
    private var photoAdapter: PhotoRecyclerAdapter? = null

    companion object {
        @JvmStatic
        fun start(context: Activity, albumId: String?) {
            val intent = Intent(context, PhotosActivity::class.java)
            intent.putExtra(AlbumConstants.ALBUM_NAME, albumId)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)
        mRecyclerView = findViewById(R.id.photos_recycler_view)
        blurView = findViewById(R.id.blur_view)
        supportActionBar?.title = "Gallery"
        mPresenter = PhotoPresenter.INSTANCE
        mPresenter.attach(this)
        val albumId = intent.getStringExtra(AlbumConstants.ALBUM_NAME)
        mPresenter.getAllPhotos(albumId)
        blur()
    }

    private fun initAdapter(photos: ArrayList<String>?) {
        photoAdapter = null
        val container = findViewById<LinearLayout>(R.id.layout_container)
        photos?.let {
            photoAdapter = PhotoRecyclerAdapter(it, this, this, container)
            mRecyclerView.layoutManager = GridLayoutManager(this, AlbumConstants.SPAN_COUNT, GridLayoutManager.VERTICAL, false)
            mRecyclerView.adapter = photoAdapter
        }
    }

    override fun fillView(photos: ArrayList<String>) {
        initAdapter(photos)
    }

    override fun onItemClick(any: Any?) {
        photoAdapter?.getPhoto()?.let {
            ImagePreviewActivity.static(this, any as Int, it)
        }
    }

    override fun updateView(photo: Photo) {
        photoAdapter?.addToAdapter(photo)
    }

    override fun onLongClick(isBlur: Boolean) {
        if (isBlur){
            blurView.visibility= View.VISIBLE
        }else{
            blurView.visibility= View.INVISIBLE
        }

    }

    private fun blur() {
        val radius = 10f

        val decorView = window.decorView
        //ViewGroup you want to start blur from. Choose root as close to BlurView in hierarchy as possible.
        val rootView = decorView.findViewById(android.R.id.content) as ViewGroup
        //set background, if your root layout doesn't have one
        val windowBackground = decorView.background

        blurView.setupWith(rootView)
                .windowBackground(windowBackground)
                .blurAlgorithm(RenderScriptBlur(this))
    //                .blurAlgorithm(SupportRenderScriptBlur(this))
                .blurRadius(radius)
                .setHasFixedTransformationMatrix(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detach()
    }
}

