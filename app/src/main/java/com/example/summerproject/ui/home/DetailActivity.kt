package com.example.summerproject.ui.home

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.summerproject.R

class DetailActivity : AppCompatActivity() {

    private val flowerDetailViewModel by viewModels<FlowerDetail> {
        FlowerDetailFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_detail)

        var currentFlowerName: String? = null

        /* Connect variables to UI elements. */
        val flowerName: TextView = findViewById(R.id.flower_detail_name)
        val flowerImage: ImageView = findViewById(R.id.flower_detail_image)
        val flowerDescription: TextView = findViewById(R.id.flower_detail_description)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentFlowerName = bundle.getString(FLOWER_NAME)
        }

        /* If currentFlowerId is not null, get corresponding flower and set name, image and
        description */
        currentFlowerName?.let {
            val currentFlower = flowerDetailViewModel.getFlowerForName(it)
            flowerName.text = currentFlower?.name
            if (currentFlower?.image == null) {
                flowerImage.setImageResource(R.drawable.rose)
            } else {
                flowerImage.setImageResource(currentFlower.image)
            }
            flowerDescription.text = currentFlower?.description
        }

    }
}