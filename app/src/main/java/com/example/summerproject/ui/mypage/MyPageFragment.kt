package com.example.summerproject.ui.mypage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.summerproject.HomeActivity
import com.example.summerproject.R
import com.example.summerproject.databinding.FragmentMypageBinding
import com.example.summerproject.ui.dashboard.DashBoardFragment


class MyPageFragment : Fragment() {
    private var mainActivity: HomeActivity? =null
    private var mBinding: FragmentMypageBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity=context as HomeActivity

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMypageBinding.inflate(inflater, container, false)

        mBinding = binding
        return mBinding?.root
    }

    override fun onStart() {
        super.onStart()
        mBinding?.profileModify?.setOnClickListener() {
            Toast.makeText(context, "123", Toast.LENGTH_SHORT).show()
            mainActivity?.replaceFragment(DashBoardFragment())
        }
    }


    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

}

