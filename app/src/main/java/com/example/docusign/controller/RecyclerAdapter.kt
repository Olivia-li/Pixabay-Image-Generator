package com.example.docusign.controller

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.docusign.R
import com.example.docusign.model.Photo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.picture_item.view.*


class RecyclerAdapter(private val photos: ArrayList<Photo>) : RecyclerView.Adapter<RecyclerAdapter.PhotoHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val inflatedView = parent.inflate(R.layout.picture_item, false)
        return PhotoHolder(
            inflatedView
        )
    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val itemPhoto = photos[position]
        holder.bindPhoto(itemPhoto)
    }

    class PhotoHolder(v: View): RecyclerView.ViewHolder(v), View.OnClickListener{
        private var view: View = v
        private var photo: Photo? = null

        init{
            v.setOnClickListener(this)
        }

        fun bindPhoto(photo: Photo) {
            this.photo = photo
            Picasso.get().load(photo.largeImageURL).into(view.itemImage)
            view.itemDate.text = photo.id
            view.itemDescription.text = photo.user
        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }
}
