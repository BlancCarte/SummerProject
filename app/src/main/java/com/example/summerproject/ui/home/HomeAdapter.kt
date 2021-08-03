package com.example.summerproject.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.summerproject.R

class HomeAdapter(private val onClick: (Flower) -> Unit) : ListAdapter<Flower, HomeAdapter.FlowerViewHolder>(FlowerDiffCallback) {

    /* ViewHolder for Flower, takes in the inflated view and the onClick behavior. */
    class FlowerViewHolder(itemView: View, val onClick: (Flower) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val flowerTextView: TextView = itemView.findViewById(R.id.flower_text)
        private val flowerImageView: ImageView = itemView.findViewById(R.id.flower_image)
        private var currentFlower: Flower? = null

        init {
            itemView.setOnClickListener {
                currentFlower?.let {
                    onClick(it)
                }
            }
        }


        /* Bind flower name and image. */
        fun bind(flower: Flower) {
            currentFlower = flower

            flowerTextView.text = flower.name
            if (flower.image != null) {
                flowerImageView.setImageResource(flower.image)
            } else {
                flowerImageView.setImageResource(R.drawable.rose)
            }
        }
    }

    /* Creates and inflates view and return FlowerViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_home_item, parent, false)
        return FlowerViewHolder(view,onClick)
    }

    /* Gets current flower and uses it to bind view. */
    override fun onBindViewHolder(holder: FlowerViewHolder, position: Int) {
        val flower = getItem(position)
        holder.bind(flower)

    }
}

object FlowerDiffCallback : DiffUtil.ItemCallback<Flower>() {
    override fun areItemsTheSame(oldItem: Flower, newItem: Flower): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Flower, newItem: Flower): Boolean {
        return oldItem.name == newItem.name
    }
}

class FlowersList(val dataSource: DataSource) : ViewModel() {
    val flowersLiveData = dataSource.getFlowerList()
}

class FlowersListFactory(private val context: HomeFragment) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlowersList::class.java)) {
            return FlowersList(
                dataSource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class FlowerDetail(private val datasource: DataSource) : ViewModel() {
    fun getFlowerForName(name: String) : Flower? {
        return datasource.getFlowerForName(name)
    }
}
class FlowerDetailFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlowerDetail::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlowerDetail(
                datasource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}