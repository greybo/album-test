package com.greybo.testfacebook.image

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.greybo.testfacebook.R
import com.greybo.testfacebook.utils.AlbumConstants


class ImagePreviewActivity : AppCompatActivity() {

    private var countImages: Int = 0

    companion object {
        @JvmStatic
        fun static(context: Context, position: Int, listPath: ArrayList<String>) {
            val intent = Intent(context, ImagePreviewActivity::class.java)
            intent.putExtra(AlbumConstants.IMAGE_PREVIEW_POSITION, position)
            intent.putExtra(AlbumConstants.IMAGE_PREVIEW_LIST_PAHT, listPath)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_preview)

        val listPath = intent.getStringArrayListExtra(AlbumConstants.IMAGE_PREVIEW_LIST_PAHT)
        if (listPath != null) {
            countImages = listPath.size
        }
        val positionSelected = intent.getIntExtra(AlbumConstants.IMAGE_PREVIEW_POSITION, 0)
        val viewPager = findViewById<ViewPager>(R.id.pagePreview)
        val adapter = ImageAdapter(this, listPath)
        viewPager.setAdapter(adapter)
        viewPager.setBackgroundColor(ContextCompat.getColor(this, android.R.color.black))
        viewPager.setCurrentItem(positionSelected)
    }

}
