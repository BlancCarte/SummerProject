package com.example.summerproject.ui.home

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.summerproject.R
import com.example.summerproject.databinding.FragmentHomeBinding


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


data class Flower(
    val name: String,
    @DrawableRes
    val image: Int?
)

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




