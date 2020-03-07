package com.example.docusign.controller

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.docusign.R
import com.example.docusign.model.ImageRequester
import com.example.docusign.model.ImageRequester.JsonTask
import com.example.docusign.model.Photo
import com.example.docusign.model.PhotoGenerator
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {

    private var photoList: ArrayList<Photo> = ArrayList()
    private lateinit var gridLayoutManager: GridLayoutManager

    private var buttonClicked: Boolean = false

    companion object{
        lateinit var adapter: RecyclerAdapter
    }

    private val lastVisibleImagePosition: Int
        get() = gridLayoutManager.findLastVisibleItemPosition()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.activity_main)

        gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager

        adapter =
            RecyclerAdapter(PhotoGenerator.photosList)
        recyclerView.adapter =
            adapter

        setRecyclerViewScrollListener()

        button.setOnClickListener {
            println(editText.text.toString())
            PhotoGenerator.clearPhotos()
            requestImage()
            buttonClicked = true
            textView.text = ""
        }
    }

    override fun onStart() {
        super.onStart()
        textView.text = "Search a picture!"
    }

    private fun requestImage() {
        try {
            val url = ImageRequester.getURL(this, editText.text.toString())
            JsonTask(this).execute(url)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun receivedNewPhoto(newPhoto: Photo) {
        runOnUiThread {
            photoList.add(newPhoto)
            adapter.notifyItemInserted(photoList.size-1)
        }
    }

    private fun setRecyclerViewScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                if (buttonClicked && totalItemCount == lastVisibleImagePosition + 1) {
                    requestImage()
                }
            }
        })
    }

}
