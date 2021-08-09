package com.example.summerproject.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.summerproject.databinding.ActivityHomeDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var title = intent.getSerializableExtra("title")
        var imageUrl = intent.getSerializableExtra("imageUrl")
        var price = intent.getSerializableExtra("price")

        binding.titleTextView.text = title.toString()
        binding.priceTextView.text = price.toString()

        Glide.with(binding.titleimageView)
            .load(imageUrl)
            .into(binding.titleimageView)
    }
}