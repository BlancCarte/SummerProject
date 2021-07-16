package com.example.summerproject.ui.mypage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.summerproject.HomeActivity
import com.example.summerproject.MainActivity
import com.example.summerproject.R

import com.example.summerproject.databinding.FragmentMypageBinding

class MypageFragment : Fragment() {

    private lateinit var mypageViewModel: MypageViewModel
    private var _binding: FragmentMypageBinding? = null
    lateinit var mainActivity: HomeActivity
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity=context as HomeActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_mypage,container, false)
        mypageViewModel =
            ViewModelProvider(this).get(MypageViewModel::class.java)

        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.profileModify.setOnClickListener(){
            Toast.makeText(context,"123",Toast.LENGTH_SHORT).show()
            mainActivity.replaceFragment()

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}