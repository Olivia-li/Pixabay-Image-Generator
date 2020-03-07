package com.example.docusign.controller

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.example.docusign.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_detail)
        val photoURL: String = intent.getStringExtra("imageURL")!!
        val user: String = intent.getStringExtra("user")!!
        Picasso.get().load(photoURL).into(imageView2)
        textView2.text = "By: " + user


    }
}
