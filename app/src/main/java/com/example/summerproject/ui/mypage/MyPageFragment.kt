package com.example.summerproject.ui.mypage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.summerproject.HomeActivity
import com.example.summerproject.databinding.FragmentMypageBinding


class MyPageFragment : Fragment() {
    private var homeActivity: HomeActivity? =null
    private var mBinding: FragmentMypageBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity=context as HomeActivity

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
            Toast.makeText(context, "프로필 수정", Toast.LENGTH_SHORT).show()
            homeActivity?.replaceFragment(ModifyFragment())
        }
    }


    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

}