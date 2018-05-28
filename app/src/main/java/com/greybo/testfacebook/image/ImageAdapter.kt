package com.greybo.testfacebook.image

import android.app.Activity
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.greybo.testfacebook.R
import com.greybo.testfacebook.utils.AlbumConstants
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ImageAdapter(
        private val activity: Activity,
        private val listPath: List<String>
) : PagerAdapter() {
    val TAG = AlbumConstants.TAG

    override fun getCount(): Int {
        return listPath.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val path = listPath[position]
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_images, container, false)
        val imageView = view.findViewById<TouchImageView>(R.id.image_touch)
        val progress = view.findViewById<ProgressBar>(R.id.progress_image_loading)
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER)
        loadImage(imageView, progress, path)
        container.addView(view)
        return view
    }

    @Synchronized
    private fun loadImage(imageView: TouchImageView, progress: ProgressBar, path: String) {
        val callback = object : Callback {
            override fun onSuccess() {
                progress.visibility = View.GONE
            }

            override fun onError() {
                progress.visibility = View.GONE
            }
        }

        Picasso.with(activity)
                .load(path)
                .into(imageView, callback)

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }
}