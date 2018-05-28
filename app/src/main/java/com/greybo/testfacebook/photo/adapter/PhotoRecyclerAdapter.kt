package com.greybo.testfacebook.photo.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import com.greybo.testfacebook.R
import com.greybo.testfacebook.model.Photo
import com.greybo.testfacebook.utils.AlbumConstants
import com.greybo.testfacebook.utils.OnBlurListener
import com.greybo.testfacebook.utils.OnItemClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_photos.view.*
import kotlinx.android.synthetic.main.item_gallery.view.*


class PhotoRecyclerAdapter(
        private var photoList: ArrayList<String>,
        private var itemClickListener: OnItemClickListener,
        private var blurkListener: OnBlurListener,
        private val container: LinearLayout
) : RecyclerView.Adapter<PhotoRecyclerAdapter.ViewHolder>() {

    private lateinit var context: Context

    private var mCurrentAnimator: Animator? = null

    private var mShortAnimationDuration: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gallery, parent, false)
        context = parent.context

        mShortAnimationDuration = context.getResources().getInteger(
                android.R.integer.config_shortAnimTime)

        return ViewHolder(view, parent.context)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = photoList.get(position)
        holder.imagePhoto.setOnLongClickListener {
            zoomImageFromThumb(holder)
            true
        }
        holder.imagePhoto.layoutParams.height = sizeImage()
        holder.imagePhoto.layoutParams.width = sizeImage()
        holder.bind(model)
        holder.imagePhoto.setOnClickListener({
            itemClickListener.onItemClick(position)
        })
    }

    class ViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {
        var imagePhoto = itemView.findViewById<ImageView>(R.id.image_photo)

        fun bind(url: String) {
            Picasso.with(context)
                    .load(url)
                    .into(imagePhoto)
        }
    }

    fun addToAdapter(photo: Photo) {
        photo.url?.let {
            photoList.add(it)
            notifyDataSetChanged()
        }
    }

    fun getPhoto(): ArrayList<String> {
        return photoList
    }

    fun sizeImage(): Int {
        val displayMetrics = context.resources.displayMetrics
        val width = displayMetrics.widthPixels
        return (width / AlbumConstants.SPAN_COUNT)
    }

    fun zoomImageFromThumb(holder: ViewHolder) {

        mCurrentAnimator?.cancel()
        val thumbView = holder.itemView.image_photo
        val expandedImageView = container.image_expanded
        expandedImageView.setImageDrawable(holder.imagePhoto.drawable)

        val startBounds = Rect()
        val finalBounds = Rect()
        val globalOffset = Point()

        thumbView.getGlobalVisibleRect(startBounds)
        container.getGlobalVisibleRect(finalBounds, globalOffset)
        startBounds.offset(-globalOffset.x, -globalOffset.y)
        finalBounds.offset(-globalOffset.x, -globalOffset.y)

        var startScale: Float
        if (finalBounds.width() / finalBounds.height()
                > startBounds.width() / startBounds.height()) {
            startScale = (startBounds.height().toFloat() / finalBounds.height().toFloat())
            val startWidth = startScale * finalBounds.width()
            val deltaWidth = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {
            startScale = (startBounds.width().toFloat() / finalBounds.width().toFloat())
            val startHeight = startScale * finalBounds.height()
            val deltaHeight = (startHeight - startBounds.height()) / 2
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }

        thumbView.setAlpha(0f)
        expandedImageView.setVisibility(View.VISIBLE)

        expandedImageView.setPivotX(0f)
        expandedImageView.setPivotY(0f)

        val set = AnimatorSet()
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left.toFloat(), finalBounds.left.toFloat()))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top.toFloat(), finalBounds.top.toFloat()))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f))
        set.setDuration(mShortAnimationDuration.toLong())
        set.setInterpolator(DecelerateInterpolator())
        set.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator) {
                mCurrentAnimator = null

            }

            override fun onAnimationCancel(animation: Animator) {
                mCurrentAnimator = null
            }
        })
        set.start()
        mCurrentAnimator = set
        blurkListener.onLongClick(true)
        var startScaleFinal = startScale
        expandedImageView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                mCurrentAnimator?.cancel()
                val anim = AnimatorSet()
                anim
                        .play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left.toFloat()))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top.toFloat()))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScaleFinal))
                anim.setDuration(mShortAnimationDuration.toLong())
                anim.setInterpolator(DecelerateInterpolator())
                anim.addListener(
                        object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                thumbView.setAlpha(1f)
                                expandedImageView.setVisibility(View.GONE)
                                mCurrentAnimator = null
                                blurkListener.onLongClick(false)
                            }

                            override fun onAnimationCancel(animation: Animator) {
                                thumbView.setAlpha(1f)
                                expandedImageView.setVisibility(View.GONE)
                                mCurrentAnimator = null
                            }
                        })
                anim.start()
                mCurrentAnimator = anim
            }
        })
    }
}