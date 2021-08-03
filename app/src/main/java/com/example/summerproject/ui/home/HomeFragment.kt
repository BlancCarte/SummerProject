package com.example.summerproject.ui.home

import android.content.Intent
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
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

const val FLOWER_NAME = "flower name"
private var firebaseAuth: FirebaseAuth? = null



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
        firebaseAuth = FirebaseAuth.getInstance()

        val flowersAdapter = HomeAdapter { flower -> adapterOnClick(flower) }
        val concatAdapter = ConcatAdapter(flowersAdapter)

        val recyclerView: RecyclerView = mBinding!!.recyclerView
        recyclerView.adapter = concatAdapter

        flowersList.flowersLiveData.observe(viewLifecycleOwner, {
            it?.let {
                flowersAdapter.submitList(it as MutableList<Flower>)
            }
        })

        initFloatingButton(view)
    }

    private fun adapterOnClick(flower: Flower) {
        val intent = Intent(activity, DetailActivity()::class.java)
        intent.putExtra(FLOWER_NAME, flower.name)
        startActivity(intent)
    }

    private fun initFloatingButton(view: View) {
        // 플로팅 버튼;
        mBinding!!.addFloatingButton.setOnClickListener {
            context?.let {
                if (firebaseAuth!!.currentUser != null) {
                    val intent = Intent(it, AddArticleActivity::class.java)
                    startActivity(intent)
                } else {
                    Snackbar.make(view, "로그인 후 사용해주세요", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}
    data class Flower(
        val name: String,
        @DrawableRes
        val image: Int?,
        val description: String
    )

    fun flowerList(resources: Resources): List<Flower> {
        return listOf(
            Flower(
                name = resources.getString(R.string.flower1_name),
                image = R.drawable.rose,
                description = resources.getString(R.string.flower1_description)
            ),
            Flower(
                name = resources.getString(R.string.flower2_name),
                image = R.drawable.freesia,
                description = resources.getString(R.string.flower1_description)
            ),
            Flower(
                name = resources.getString(R.string.flower3_name),
                image = R.drawable.lily,
                description = resources.getString(R.string.flower1_description)
            ),
            Flower(
                name = resources.getString(R.string.flower4_name),
                image = R.drawable.sunflower,
                description = resources.getString(R.string.flower1_description)
            ),
            Flower(
                name = resources.getString(R.string.flower5_name),
                image = R.drawable.peony,
                description = resources.getString(R.string.flower1_description)

            ),
            Flower(
                name = resources.getString(R.string.flower6_name),
                image = R.drawable.daisy,
                description = resources.getString(R.string.flower1_description)
            ),
            Flower(
                name = resources.getString(R.string.flower7_name),
                image = R.drawable.lilac,
                description = resources.getString(R.string.flower1_description)
            ),
            Flower(
                name = resources.getString(R.string.flower8_name),
                image = R.drawable.marigold,
                description = resources.getString(R.string.flower1_description)
            ),
            Flower(
                name = resources.getString(R.string.flower9_name),
                image = R.drawable.poppy,
                description = resources.getString(R.string.flower1_description)
            ),
            Flower(
                name = resources.getString(R.string.flower10_name),
                image = R.drawable.daffodil,
                description = resources.getString(R.string.flower1_description)
            ),
            Flower(
                name = resources.getString(R.string.flower11_name),
                image = R.drawable.dahlia,
                description = resources.getString(R.string.flower1_description)
            )
        )
    }




