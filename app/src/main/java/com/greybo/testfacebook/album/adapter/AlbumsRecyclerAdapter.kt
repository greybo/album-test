package com.greybo.testfacebook.album.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.greybo.testfacebook.Album
import com.greybo.testfacebook.R
import com.greybo.testfacebook.utils.OnItemClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_albums.view.*

class AlbumsRecyclerAdapter(
        private var albumsList: ArrayList<Album>,
        private var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<AlbumsRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_albums, parent, false)
        return ViewHolder(view, parent.context)
    }

    override fun getItemCount(): Int {
        return albumsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = albumsList.get(position)
        holder.bind(model)
        holder.itemView.image_album.setOnClickListener({
            itemClickListener.onItemClick(model.id)
        })
    }

    class ViewHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView) {
//        var imageAlbum = itemView.findViewById<ImageView>(R.id.image_album)

        fun bind(model: Album) {
            model.url?.let {
                Picasso.with(context)
                        .load(it)
                        .into(itemView.image_album)
            }
            itemView.text_album.text = model.name
            itemView.text_date_create.text = model.created_time
        }
    }

    fun addToAdapter(album: Album) {
        albumsList.add(album)
        notifyDataSetChanged()
    }
}