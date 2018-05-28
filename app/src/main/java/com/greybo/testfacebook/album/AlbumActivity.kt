package com.greybo.testfacebook.album

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import com.greybo.testfacebook.Album
import com.greybo.testfacebook.R
import com.greybo.testfacebook.album.adapter.AlbumsRecyclerAdapter
import com.greybo.testfacebook.album.presenter.AlbumsMvp
import com.greybo.testfacebook.album.presenter.AlbumsPresenter
import com.greybo.testfacebook.photo.PhotosActivity
import com.greybo.testfacebook.utils.OnItemClickListener
import android.R.menu
import android.view.MenuInflater




class AlbumActivity : AppCompatActivity(), AlbumsMvp.AlbumsView, OnItemClickListener {

    private lateinit var mPresenter: AlbumsPresenter
    private lateinit var mAlbumsRecycler: RecyclerView
    private var albumAdapter: AlbumsRecyclerAdapter? = null
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    companion object {
        @JvmStatic
        fun start(context: Activity) {
            val intent = Intent(context, AlbumActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout_sights)
        mSwipeRefreshLayout.setOnRefreshListener {
            mPresenter.requestAlbums()
            Handler().postDelayed({
                mSwipeRefreshLayout.isRefreshing = false
            }, 1000)
        }
        supportActionBar?.title="Album"
        mAlbumsRecycler = findViewById(R.id.albums_recycler_view)
        mPresenter = AlbumsPresenter.INSTANCE
        mPresenter.attach(this)
        mPresenter.getAllAlbums()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add("Setting")
        return true
    }

    private fun initAdapter(albums: ArrayList<Album>?) {
        albums?.let {
            albumAdapter = null
            albumAdapter = AlbumsRecyclerAdapter(it, this)
            mAlbumsRecycler.layoutManager = LinearLayoutManager(this)
            mAlbumsRecycler.adapter = albumAdapter
        }
    }

    override fun fillView(albums: ArrayList<Album>) {
        initAdapter(albums)
    }

    override fun updateView(album: Album) {
        album.let { albumAdapter?.addToAdapter(it) }
    }

    override fun onItemClick(any: Any?) {
        PhotosActivity.start(this, any as String)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detach()
        albumAdapter = null
    }
}

