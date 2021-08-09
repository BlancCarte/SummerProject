package com.example.summerproject.ui.home

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.summerproject.R
import com.example.summerproject.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

private var firebaseAuth: FirebaseAuth? = null
private var firebaseFirestore: FirebaseFirestore? = null

data class Flower(
    val name: String,
    @DrawableRes
    val image: Int?,
)


class HomeFragment : Fragment() {
    private val flowersList by viewModels<FlowersList> {
        FlowersListFactory(this)
    }

    private var mBinding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        mBinding = binding
        return mBinding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val flowersAdapter = FlowersAdapter { FlowerDiffCallback }
        val concatAdapter = ConcatAdapter(flowersAdapter)

        val recyclerView: RecyclerView = mBinding!!.recyclerView
        recyclerView.adapter = concatAdapter

        flowersList.flowersLiveData.observe(viewLifecycleOwner, {
            it?.let {
                flowersAdapter.submitList(it as MutableList<Flower>)
            }
        })
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}

fun flowerList(resources: Resources): List<Flower> {
    return listOf(
        Flower(
            name = resources.getString(R.string.flower1_name),
            image = R.drawable.rose
        ),
        Flower(
            name = resources.getString(R.string.flower2_name),
            image = R.drawable.freesia
        ),
        Flower(
            name = resources.getString(R.string.flower3_name),
            image = R.drawable.lily
        ),
        Flower(
            name = resources.getString(R.string.flower4_name),
            image = R.drawable.sunflower
        ),
        Flower(
            name = resources.getString(R.string.flower5_name),
            image = R.drawable.peony

        ),
        Flower(
            name = resources.getString(R.string.flower6_name),
            image = R.drawable.daisy
        ),
        Flower(
            name = resources.getString(R.string.flower7_name),
            image = R.drawable.lilac
        ),
        Flower(
            name = resources.getString(R.string.flower8_name),
            image = R.drawable.marigold
        ),
        Flower(
            name = resources.getString(R.string.flower9_name),
            image = R.drawable.poppy
        ),
        Flower(
            name = resources.getString(R.string.flower10_name),
            image = R.drawable.daffodil
        ),
        Flower(
            name = resources.getString(R.string.flower11_name),
            image = R.drawable.dahlia
        )
    )
}

class DataSource(resources: Resources) {
    private val initialFlowerList = flowerList(resources)
    private val flowersLiveData = MutableLiveData(initialFlowerList)


    fun getFlowerList(): LiveData<List<Flower>> {
        return flowersLiveData
    }


    companion object {
        private var INSTANCE: DataSource? = null

        fun getDataSource(resources: Resources): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}

class FlowersAdapter(private val onClick: (Flower) -> Unit) :
    ListAdapter<Flower, FlowersAdapter.FlowerViewHolder>(FlowerDiffCallback) {

    /* ViewHolder for Flower, takes in the inflated view and the onClick behavior. */
    class FlowerViewHolder(itemView: View, val onClick: (Flower) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val flowerTextView: TextView = itemView.findViewById(R.id.flower_text)
        private val flowerImageView: ImageView = itemView.findViewById(R.id.flower_image)
        private var currentFlower: Flower? = null


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
        return FlowerViewHolder(view, onClick)
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
            @Suppress("UNCHECKED_CAST")
            return FlowersList(
                dataSource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
