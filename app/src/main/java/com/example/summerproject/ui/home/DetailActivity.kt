package com.example.summerproject.ui.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.summerproject.R
import com.example.summerproject.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("상세정보")
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var title = intent.getSerializableExtra("title")
        var imageurl = intent.getSerializableExtra("imageurl")
        var price = intent.getSerializableExtra("price")
        var content = intent.getSerializableExtra("content")
        var sellerEmail = intent.getSerializableExtra("sellerEmail")
        binding.titleTextView.text = title.toString()
        binding.sellerNicknameTextView.text = sellerEmail.toString()
        binding.priceTextView.text = price.toString()
        binding.contentTextView.text = content.toString()
        Glide.with(binding.titleimageView)
            .load(imageurl)
            .into(binding.titleimageView)
    }


}