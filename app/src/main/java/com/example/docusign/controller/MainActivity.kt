package com.example.docusign.controller

import android.os.Bundle
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

        if (photoList.size == 1) {
            requestImage()
        }

        gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager

        adapter =
            RecyclerAdapter(PhotoGenerator.photosList)
        recyclerView.adapter =
            adapter

        setRecyclerViewScrollListener()

        JsonTask(this).execute(ImageRequester.sURL)

    }

    override fun onStart() {
        super.onStart()
        if (photoList.size == 0) {
            requestImage()
        }
    }

    private fun requestImage() {
        try {
            JsonTask(this).execute(ImageRequester.sURL)
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
                if (!ImageRequester.isLoadingData && totalItemCount == lastVisibleImagePosition + 1) {
                    requestImage()
                }
            }
        })
    }

}
